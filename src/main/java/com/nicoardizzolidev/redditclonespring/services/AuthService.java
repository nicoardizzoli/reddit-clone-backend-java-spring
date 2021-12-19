package com.nicoardizzolidev.redditclonespring.services;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.nicoardizzolidev.redditclonespring.dto.AuthenticationResponseDTO;
import com.nicoardizzolidev.redditclonespring.dto.LoginRequestDTO;
import com.nicoardizzolidev.redditclonespring.dto.RegisterRequestDTO;
import com.nicoardizzolidev.redditclonespring.exceptions.SpringRedditException;
import com.nicoardizzolidev.redditclonespring.model.NotificationEmail;
import com.nicoardizzolidev.redditclonespring.model.User;
import com.nicoardizzolidev.redditclonespring.model.VerificationToken;
import com.nicoardizzolidev.redditclonespring.repository.UserRepository;
import com.nicoardizzolidev.redditclonespring.repository.VerificationTokenRepository;
import com.nicoardizzolidev.redditclonespring.security.JwtProvider;
import com.nicoardizzolidev.redditclonespring.security.RefreshTokenRequest;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthService {

	// se podria usar el autowired, pero spring recomienda usar constructor
	// injection si es posible. asi que usamos
	// allargscontructor y ponemos como final lo que queremos injectar. tmb hay que
	// usar el @transactional en el metodo.

	private final PasswordEncoder passwordEncoder;

	private final UserRepository userRepository;

	private final VerificationTokenRepository verificationTokenRepository;

	private final MailService mailService;
	
	//en la clase SecurityConfig se dice que implementacion de esta interface usar. 
	private final AuthenticationManager authenticationManager;
	
	private final JwtProvider jwtProvider;

	private final RefreshTokensService refreshTokensService;
	
	
	@Transactional
	public void signUp(RegisterRequestDTO registerRequestDTO) {
		User user = new User();
		user.setUsername(registerRequestDTO.getUsername());
		user.setEmail(registerRequestDTO.getEmail());
		user.setPassword(passwordEncoder.encode(registerRequestDTO.getPassword()));
		user.setCreated(Instant.now());
		user.setEnabled(false);
		userRepository.save(user);

		String token = generateVerificationToken(user);
		mailService.sendMail(new NotificationEmail("Please activate your account", user.getEmail(),
				"Thank you for signing up to Spring Reddit Clone, please activate your account:"
						+ "http://localhost:8080/api/auth/accountVerification/" + token));
	}

	private String generateVerificationToken(User user) {
		String token = UUID.randomUUID().toString();
		VerificationToken verificationToken = new VerificationToken();
		verificationToken.setToken(token);
		verificationToken.setUser(user);
		verificationTokenRepository.save(verificationToken);

		return token;
	}

	public void verifyAccount(String token) {
		Optional<VerificationToken> verificationToken = verificationTokenRepository.findByToken(token);
		verificationToken.orElseThrow(() -> new SpringRedditException("invalid token"));
		fetchUserAndEnable(verificationToken.get());
	}

	@Transactional
	private void fetchUserAndEnable(VerificationToken verificationToken) {
		String username = verificationToken.getUser().getUsername();
		Optional<User> user = userRepository.findByUsername(username);
		user.orElseThrow(() -> new SpringRedditException("User not found"));
		User userToUpdate = user.get();
		userToUpdate.setEnabled(true);
		userRepository.save(userToUpdate);
		
	}

	public AuthenticationResponseDTO login(LoginRequestDTO loginRequestDTO) {
		Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequestDTO.getUsername(), loginRequestDTO.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authenticate);
		String generateToken = jwtProvider.generateToken(authenticate);
		return AuthenticationResponseDTO.builder()
				.authenticationToken(generateToken)
				.refreshToken(refreshTokensService.generateRefreshToken().getToken())
				.expiresAt(jwtProvider.getExpirationTime())
				.username(loginRequestDTO.getUsername())
				.build();
				
		
	}
	
    @Transactional(readOnly = true)
    public User getCurrentUser() {
        org.springframework.security.core.userdetails.User principal = (org.springframework.security.core.userdetails.User) SecurityContextHolder.
                getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getUsername()));
    }

	public AuthenticationResponseDTO refreshToken(RefreshTokenRequest refreshTokenRequest) {
		// TODO Auto-generated method stub
		 refreshTokensService.validateRefreshToken(refreshTokenRequest.getRefreshToken());;
		 String token = jwtProvider.generateTokenWithUsername(refreshTokenRequest.getUsername());
		 AuthenticationResponseDTO authenticationResponse = AuthenticationResponseDTO.builder()
		 	.authenticationToken(token)
		 	.refreshToken(token)
		 	.expiresAt(jwtProvider.getExpirationTime())
		 	.username(refreshTokenRequest.getUsername())
		 	.build();
		 
		 return authenticationResponse;
	}


}
