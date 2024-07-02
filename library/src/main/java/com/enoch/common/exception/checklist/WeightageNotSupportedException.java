package com.enoch.common.exception.checklist;

import com.enoch.common.exception.ServiceException;

public class WeightageNotSupportedException extends ServiceException {
	public WeightageNotSupportedException(String arg0) {
		super(arg0);
	}

	public WeightageNotSupportedException(Throwable arg0) {
		super(arg0);
	}

	public WeightageNotSupportedException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}
}
