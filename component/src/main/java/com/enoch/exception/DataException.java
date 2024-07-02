package com.enoch.exception;

public class DataException extends RuntimeException
{

	private static final long serialVersionUID = -2741918717149739290L;

	public DataException(String message) {
		super(message);
	}
	public DataException(String message,Exception e) {
		super(message,e);
	}

}
