package com.nicoardizzolidev.redditclonespring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RegisterRequestDTO {
	
	private String email;
	private String username;
	private String password; 
	
}
