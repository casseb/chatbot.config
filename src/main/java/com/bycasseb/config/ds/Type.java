package com.bycasseb.config.ds;

import com.bycasseb.config.ds.validator.IntegerTypeValidator;
import com.bycasseb.config.ds.validator.StringTypeValidator;

public enum Type {
	
	STRING, INTEGER;

	public TypeValidator getTypeValidator() {
		switch (this) {
		case STRING:
			return new StringTypeValidator();
		case INTEGER:
			return new IntegerTypeValidator();
		default:
			return null;
		}
			
	}

}
