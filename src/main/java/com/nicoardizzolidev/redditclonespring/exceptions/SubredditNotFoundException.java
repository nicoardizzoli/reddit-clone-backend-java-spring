package com.nicoardizzolidev.redditclonespring.exceptions;

public class SubredditNotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SubredditNotFoundException(String exMessage) {
		super(exMessage);
	}
}
