package com.berly.unsa.cryptography.model;

public class StringFormatException extends IllegalArgumentException {

	private static final long serialVersionUID = -751122182613241006L;

	public StringFormatException() { super(); }
	
	public StringFormatException(String s) { super(s); }

	public StringFormatException(Throwable cause) { super(cause); }

	public StringFormatException(String message, Throwable cause) { super(message, cause); }
}
