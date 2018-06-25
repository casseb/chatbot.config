package com.bycasseb.config.ds.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bycasseb.config.ds.TypeValidator;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.ds.exception.InvalidValueException;

public class IntegerTypeValidator extends TypeValidator {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void validate(Variable variable) throws InvalidValueException {
		try {
			Integer.parseInt(variable.getValue());
		} catch (Exception e) {
			log.error("Variable {} with invalid Integer");
			throw new InvalidValueException(); 
		}
	}

	@Override
	public String getDefault() {
		return "0";
	}

}
