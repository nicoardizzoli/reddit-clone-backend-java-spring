package com.nicoardizzolidev.redditclonespring.exceptions;

public class PostNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PostNotFoundException(String exMessage) {
		super(exMessage);
	}
}
