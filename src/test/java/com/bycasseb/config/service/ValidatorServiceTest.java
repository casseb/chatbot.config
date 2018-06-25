package com.bycasseb.config.service;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.ds.exception.InvalidAliasesException;
import com.bycasseb.config.ds.exception.InvalidValueException;
import com.bycasseb.config.ds.exception.NullAliasesException;
import com.bycasseb.config.ds.exception.NullGroupException;
import com.bycasseb.config.ds.exception.NullKeyException;
import com.bycasseb.config.ds.exception.NullTypeException;
import com.bycasseb.config.ds.exception.NullValueException;

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class ValidatorServiceTest {

	@Autowired
	private ValidatorService service;
	
	@Test
	public void validateStringTypeTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.type(Type.STRING)
				.schema("Key Test")
				.value("Value Test")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = InvalidValueException.class)
	public void validateIntegerTypeWithStringTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.type(Type.INTEGER)
				.schema("Key Test")
				.value("Value Test")
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
				.group("Group Test")
				.type(Type.STRING)
				.schema("Key Test")
				.value("Value Test")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullGroupException.class)
	public void validateNullGroupTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.type(Type.STRING)
				.schema("Key Test")
				.value("Value Test")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullTypeException.class)
	public void validateNullTypeTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Key Test")
				.value("Value Test")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullKeyException.class)
	public void validateNullKeyTest() throws Exception {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.type(Type.STRING)
				.value("Value Test")
			.build();
		
		service.execute(variable);
	}
	
	@Test(expected = NullValueException.class)
	public void validateNullValueTest() throws Exception {	
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.type(Type.STRING)
				.schema("Key Test")
			.build();
		
		service.execute(variable);
	}
	
}
