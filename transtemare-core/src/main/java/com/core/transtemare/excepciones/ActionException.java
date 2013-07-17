package com.core.transtemare.excepciones;

public class ActionException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public ActionException() {
		super();		
	}

	public ActionException(String message) {
		super(message);
	}
}
