package com.bycasseb.config.ds;

import com.bycasseb.config.ds.exception.InvalidValueException;

public abstract class TypeValidator {
	
	public abstract void validate(Variable variable) throws InvalidValueException;

	public abstract String getDefault();
	
}
