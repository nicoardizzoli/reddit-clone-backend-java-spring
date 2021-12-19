package com.nicoardizzolidev.redditclonespring.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthenticationResponseDTO {
	private String authenticationToken;
	private String username;
	private Date expiresAt;
	private String refreshToken;
	
}
