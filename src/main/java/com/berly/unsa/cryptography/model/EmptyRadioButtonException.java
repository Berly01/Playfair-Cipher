package com.berly.unsa.cryptography.model;

public class EmptyRadioButtonException extends NullPointerException {

	private static final long serialVersionUID = 1755085627253466996L;

	public EmptyRadioButtonException() { super(); }
	
	public EmptyRadioButtonException(String s) { super(s); }
	
	public EmptyRadioButtonException(String message, Throwable throwable) { super(message); }
	
	public EmptyRadioButtonException(Throwable throwable) { super(); }
}
