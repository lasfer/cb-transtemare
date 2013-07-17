package com.core.transtemare.excepciones;

public class ConexionException extends RuntimeException{

	private static final long serialVersionUID = 1249236590156333203L;

	public ConexionException() {
		super();		
	}

	public ConexionException(String message) {
		super(message);
	}

}
