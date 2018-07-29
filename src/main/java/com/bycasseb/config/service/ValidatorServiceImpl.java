package com.bycasseb.config.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.bycasseb.config.ds.TypeValidator;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.ds.exception.NullAliasesException;
import com.bycasseb.config.ds.exception.NullGroupException;
import com.bycasseb.config.ds.exception.NullKeyException;
import com.bycasseb.config.ds.exception.NullTypeException;
import com.bycasseb.config.ds.exception.NullValueException;

import java.util.Objects;

@Service("validatorService")
public class ValidatorServiceImpl implements ValidatorService{
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void execute(Variable variable) throws Exception {
		invalidNull(variable);
		TypeValidator typeValidator = variable.getType().getTypeValidator();
		Objects.requireNonNull(typeValidator).validate(variable);
	}
	
	@SuppressWarnings("SameReturnValue")
	private void invalidNull(Variable variable) throws Exception {
		if(variable.getAliases() == null) {
			log.error("Variable {} without aliases",variable);
			throw new NullAliasesException();
		}
		
		if(variable.getGroup() == null) {
			log.error("Variable {} without group",variable);
			throw new NullGroupException();
		}
		
		if(variable.getType() == null) {
			log.error("Variable {} without type",variable);
			throw new NullTypeException();
		}
		
		if(variable.getSchema() == null) {
			log.error("Variable {} without key",variable);
			throw new NullKeyException();
		}
		
		if(variable.getValue() == null) {
			log.error("Variable {} without value",variable);
			throw new NullValueException();
		}

	}
	
	
	
}
