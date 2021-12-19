package com.nicoardizzolidev.redditclonespring.exceptions;

public class UsernameNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public UsernameNotFoundException(String exMessage) {
		super(exMessage);
	}
}
