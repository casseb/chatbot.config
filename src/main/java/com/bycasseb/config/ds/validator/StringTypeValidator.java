package com.bycasseb.config.ds.validator;

import com.bycasseb.config.ds.TypeValidator;
import com.bycasseb.config.ds.Variable;

public class StringTypeValidator extends TypeValidator{

	@Override
	public void validate(Variable variable) {
		//no code
	}

	@Override
	public String getDefault() {
		return "NOT_SET";
	}

}
