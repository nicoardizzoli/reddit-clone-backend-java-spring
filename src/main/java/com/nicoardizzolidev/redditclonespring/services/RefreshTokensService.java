package com.nicoardizzolidev.redditclonespring.services;

import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.nicoardizzolidev.redditclonespring.exceptions.SpringRedditException;
import com.nicoardizzolidev.redditclonespring.model.RefreshToken;
import com.nicoardizzolidev.redditclonespring.repository.RefreshTokenRepository;

@Service
@AllArgsConstructor
@Transactional
public class RefreshTokensService {
	
	private final RefreshTokenRepository refreshTokenRepository;
	
	public RefreshToken generateRefreshToken() {
	
		RefreshToken refreshToken = RefreshToken.builder()
		.token(UUID.randomUUID().toString())
		.createdDate(Instant.now())
		.build();
		
		return refreshTokenRepository.save(refreshToken);
	}
	
	public void validateRefreshToken(String token) {
		RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(() -> new SpringRedditException("invalid refresh token"));
	}
	
	public void deleteRefreshToken(String token) {
		refreshTokenRepository.deleteByToken(token);
	}
	
}
