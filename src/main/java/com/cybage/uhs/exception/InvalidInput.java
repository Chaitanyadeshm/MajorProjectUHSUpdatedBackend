package com.cybage.uhs.exception;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

public class InvalidInput extends ConstraintViolationException {

	private static final long serialVersionUID = 1L;

	public InvalidInput(Set<? extends ConstraintViolation<?>> msg) {
		super(msg);
	}

}
