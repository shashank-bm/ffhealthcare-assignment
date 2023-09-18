package com.ffhealthcare.crud.security.exception;

public class ResourceNotFoundException extends RuntimeException {
	private static final long serialVersionUID = -7030936733857154383L;

	public ResourceNotFoundException(String message) {
		super(message);
	}
}
