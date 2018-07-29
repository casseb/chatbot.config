package com.bycasseb.config.service;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import com.bycasseb.config.repository.VariableRepository;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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

	@InjectMocks
	private PersistServiceImpl service;
	@Mock
	private VariableRepository variableRepoMock;
	
	@Test
	public void queryVariableTest() {
		PersistedVariable persistedvariable = getPersistedVariable();

		Optional<PersistedVariable> dummyVariable = Optional.of(persistedvariable);
		doReturn(dummyVariable).when(variableRepoMock).findById(ALIASES_TEST + SEPARATOR + GROUP_TEST + SEPARATOR + SCHEMA_TEST);

		Optional<PersistedVariable> result = service.query(ALIASES_TEST + SEPARATOR + GROUP_TEST + SEPARATOR + SCHEMA_TEST);
		assertTrue(result.isPresent());
		assertEquals(persistedvariable, result.get());
	}

	@Test
	public void saveVariableTest() {
		PersistedVariable persistedvariable = getPersistedVariable();

		service.save(persistedvariable);
		verify(variableRepoMock, times(1)).save(persistedvariable);
	}

	@NotNull
	private PersistedVariable getPersistedVariable() {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.type(Type.STRING)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
				.build();
		return new PersistedVariable(variable);
	}

}
