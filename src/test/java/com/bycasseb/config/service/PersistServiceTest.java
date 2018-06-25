package com.bycasseb.config.service;

import static org.junit.Assert.*;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bycasseb.config.common.TestSupport;
import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.ds.PersistedVariable;

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class PersistServiceTest extends TestSupport{
	
	@Autowired
	private PersistService service;
	
	@Test
	public void saveAndQueryVariableTest() {
		Variable variable = Variable.builder()
										.aliases("Aliases Test")
										.group("Group Test")
										.type(Type.STRING)
										.schema("Key Test")
										.value("Value Test")
									.build();
		
		PersistedVariable Persistedvariable = new PersistedVariable(variable);
		service.save(Persistedvariable);
		Optional<PersistedVariable> result = service.query("Aliases Test |&| Group Test |&| Key Test");
		assertTrue(result.isPresent());
		assertEquals(Persistedvariable, result.get());
	}
	
}
