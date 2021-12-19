package com.nicoardizzolidev.redditclonespring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nicoardizzolidev.redditclonespring.dto.AuthenticationResponseDTO;
import com.nicoardizzolidev.redditclonespring.dto.LoginRequestDTO;
import com.nicoardizzolidev.redditclonespring.dto.RegisterRequestDTO;
import com.nicoardizzolidev.redditclonespring.security.RefreshTokenRequest;
import com.nicoardizzolidev.redditclonespring.services.AuthService;
import com.nicoardizzolidev.redditclonespring.services.RefreshTokensService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {
	
	private final AuthService authService;
	private final RefreshTokensService refreshTokensService;
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> signup(@RequestBody RegisterRequestDTO registerRequestDTO) {
		authService.signUp(registerRequestDTO);
		return new ResponseEntity<>("User registration Successful", HttpStatus.OK);
	}
	
	@GetMapping("/accountVerification/{token}")
	public ResponseEntity<String> verifyAccount(@PathVariable String token){
		authService.verifyAccount(token);
		
		return new ResponseEntity<String>("Account activated Successfully", HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public AuthenticationResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {
		return authService.login(loginRequestDTO);
	}
	
	@PostMapping("/refresh/token")
	public AuthenticationResponseDTO refreshTokens(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}
	
	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Validated @RequestBody RefreshTokenRequest refreshTokenRequest){
		refreshTokensService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh token deleted succesfully");
	}
}
