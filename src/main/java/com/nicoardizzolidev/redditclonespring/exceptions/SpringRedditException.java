package com.nicoardizzolidev.redditclonespring.exceptions;

public class SpringRedditException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public SpringRedditException(String exMessage) {
		super(exMessage);
	}
}
