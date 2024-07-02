package com.enoch.exception;

public class EntityException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public EntityException(Class<?> class1, String string) {
		super(string);
	}

}
