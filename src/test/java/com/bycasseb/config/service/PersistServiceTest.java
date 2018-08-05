package com.bycasseb.config.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import com.bycasseb.config.common.ClassTest;
import com.bycasseb.config.ds.PersistedVariable;
import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.repository.VariableRepository;

public class PersistServiceTest extends ClassTest {

	@InjectMocks
	private PersistServiceImpl service;
	@Mock
	private VariableRepository variableRepoMock;
	
	@Test
	public void queryVariableTest() {
		PersistedVariable persistedvariable = getPersistedVariable();

		Optional<PersistedVariable> dummyVariable = Optional.of(persistedvariable);
		doReturn(dummyVariable).when(variableRepoMock).findById(SCHEMA_ID);

		Optional<PersistedVariable> result = service.query(SCHEMA_ID);
		assertTrue(result.isPresent());
		assertEquals(persistedvariable, result.get());
	}

	@Test
	public void saveVariableTest() {
		PersistedVariable persistedvariable = getPersistedVariable();

		service.save(persistedvariable);
		verify(variableRepoMock, times(1)).save(persistedvariable);
	}

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
