package com.bycasseb.config.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import com.bycasseb.config.common.ClassTest;
import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.ds.exception.InvalidValueException;
import com.bycasseb.config.ds.exception.NullAliasesException;
import com.bycasseb.config.ds.exception.NullGroupException;
import com.bycasseb.config.ds.exception.NullKeyException;
import com.bycasseb.config.ds.exception.NullTypeException;
import com.bycasseb.config.ds.exception.NullValueException;

@SpringBootTest
public class ValidatorServiceTest extends ClassTest {

	@Autowired
	private ValidatorService service;
	
	@Test
	public void validateStringTypeTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.type(TYPE_STRING_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = InvalidValueException.class)
	public void validateIntegerTypeWithStringTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.type(TYPE_INTEGER_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test
	public void validateIntegerTypeTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.type(Type.INTEGER)
				.schema("Key Test")
				.value("10")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullAliasesException.class)
	public void validateNullAliasesTest() throws Exception {
		Variable variable = Variable.builder()
				.group(GROUP_TEST)
				.type(TYPE_STRING_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullGroupException.class)
	public void validateNullGroupTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.type(TYPE_STRING_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullTypeException.class)
	public void validateNullTypeTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullKeyException.class)
	public void validateNullKeyTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.type(TYPE_STRING_TEST)
				.value(VALUE_TEST)
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullValueException.class)
	public void validateNullValueTest() throws Exception {	
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.type(TYPE_STRING_TEST)
				.schema(SCHEMA_TEST)
			.build();
		
		service.execute(variable);
	}
	
}
