package com.enoch.exception;

import java.util.List;

import com.enoch.common.exception.ServiceException;

import lombok.Data;

@Data
public class ValidationException extends ServiceException {

	private List<String> errors;

	public ValidationException(String string, List<String> errors) {
		super(string);
		this.errors = errors;
	}

}
