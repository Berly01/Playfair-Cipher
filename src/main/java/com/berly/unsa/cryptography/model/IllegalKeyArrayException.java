package com.berly.unsa.cryptography.model;

public class IllegalKeyArrayException extends IllegalArgumentException {

	private static final long serialVersionUID = 8669547099085146087L;

	public IllegalKeyArrayException() {}

	public IllegalKeyArrayException(String s) {
		super(s);
	}

	public IllegalKeyArrayException(Throwable cause) {
		super(cause);
	}

	public IllegalKeyArrayException(String message, Throwable cause) {
		super(message, cause);
	}
}
