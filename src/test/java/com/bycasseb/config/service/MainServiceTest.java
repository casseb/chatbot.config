package com.bycasseb.config.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

import com.bycasseb.config.ds.*;
import com.bycasseb.config.repository.AliasesRepository;
import com.bycasseb.config.repository.GroupRepository;
import com.bycasseb.config.repository.SchemaRepository;
import com.bycasseb.config.repository.VariableRepository;
import lombok.SneakyThrows;
import org.junit.Before;
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

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class MainServiceTest extends TestSupport{
	
	@InjectMocks
	private MainServiceImpl service;
	@Mock
	private AliasesRepository aliasesRepoMock;
	@Mock
    private GroupRepository groupRepoMock;
	@Mock
    private SchemaRepository schemaRepoMock;
	@Mock
    private VariableRepository variableRepoMock;
	@Mock
    private ValidatorServiceImpl validatorServiceMock;

	@Before
    @SneakyThrows
    public void before(){
	    doNothing().when(validatorServiceMock).execute(any());

        List<PersistedGroup> dummyGroups = new LinkedList<>();
        dummyGroups.add(new PersistedGroup(ALIASES_TEST, GROUP_TEST));

        List<PersistedSchema> dummySchemas = new LinkedList<>();
        dummySchemas.add(new PersistedSchema(ALIASES_TEST, GROUP_TEST, SCHEMA_TEST, TYPE_STRING_TEST));

        Variable dummyVariable = new Variable(ALIASES_TEST, GROUP_TEST, TYPE_STRING_TEST, SCHEMA_TEST, VALUE_TEST);
        Optional<PersistedVariable> dummyPersistedVariable = Optional.of(new PersistedVariable(dummyVariable));

	    doReturn(true).when(aliasesRepoMock).existsById(ALIASES_TEST);
        doReturn(true).when(groupRepoMock).existsById(ALIASES_TEST + SEPARATOR + GROUP_TEST);
        doReturn(true).when(schemaRepoMock).existsById(ALIASES_TEST + SEPARATOR + GROUP_TEST + SEPARATOR + SCHEMA_TEST);
        doReturn(true).when(variableRepoMock).existsById(ALIASES_TEST + SEPARATOR + GROUP_TEST + SEPARATOR + SCHEMA_TEST + SEPARATOR + VALUE_TEST);

	    doReturn(dummyGroups).when(groupRepoMock).findByAliases(any());
	    doReturn(dummySchemas).when(schemaRepoMock).findByAliasesAndGroup(any(),any());
	    doReturn(dummyPersistedVariable).when(variableRepoMock).findById(any());
    }
	
	@Test
	public void createAliasesTest() {
		service.put(ALIASES_TEST);
		verify(aliasesRepoMock, times(1)).save(any());
	}

	@Test
    public void existAliasesTest() {
	    assertTrue(service.exists(ALIASES_TEST));
    }
	
	@Test
	public void deleteAliasesTest(){
		service.delete(ALIASES_TEST);
        verify(aliasesRepoMock, times(1)).deleteById(ALIASES_ID);
	}
	
	@Test
	public void createGroupTest() {
		service.put(ALIASES_TEST, GROUP_TEST);
        verify(groupRepoMock, times(1)).save(any());
	}

	@Test
    public void existGroupTest(){
	    assertTrue(service.exists(ALIASES_TEST, GROUP_TEST));
    }
	
	@Test
	public void deleteGroupTest() {
		service.delete(ALIASES_TEST,GROUP_TEST);
        verify(groupRepoMock, times(1)).deleteById(GROUP_ID);
	}
	
	@Test
	public void createSchemaStringTest() {
		service.put(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST);
        verify(schemaRepoMock, times(1)).save(any());
	}
	
	@Test
	public void createSchemaIntegerTest() {
		service.put(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST, TYPE_INTEGER_TEST);
        verify(schemaRepoMock, times(1)).save(any());
	}

	@Test
    public void existSchemaTest(){
        assertTrue(service.exists(ALIASES_TEST, GROUP_TEST, SCHEMA_TEST));
    }
	
	@Test
	public void deleteSchemaTest(){
		service.delete(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST);
        verify(schemaRepoMock, times(1)).deleteById(SCHEMA_ID);
	}
	
	@Test
	public void createValueTest() {
		service.put(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST, VALUE_TEST);
        verify(variableRepoMock, times(1)).save(any());
	}

	@Test
    public void existValueTest(){
        assertTrue(service.exists(ALIASES_TEST, GROUP_TEST, SCHEMA_TEST, VALUE_TEST));
    }
	
	@Test
	public void deleteValueTest(){
		service.delete(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST, VALUE_TEST);
        verify(variableRepoMock, times(1)).deleteById(SCHEMA_ID);
	}
	
	@Test
	public void deleteCascateSchemaTest(){
		service.delete(ALIASES_TEST,GROUP_TEST, SCHEMA_TEST);
		verify(variableRepoMock, times(1)).deleteById(SCHEMA_ID);
        verify(schemaRepoMock, times(1)).deleteById(SCHEMA_ID);
	}
	
	@Test
	public void queryGroupTest() {
		List<String> schemas = service.getList(ALIASES_TEST,GROUP_TEST);
		assertEquals(1,schemas.size());
	}
	
	@Test
	public void deleteCascateGroupTest() {
		service.delete(ALIASES_TEST,GROUP_TEST);
        verify(variableRepoMock, times(1)).deleteById(SCHEMA_ID);
        verify(schemaRepoMock, times(1)).deleteById(SCHEMA_ID);
        verify(groupRepoMock, times(1)).deleteById(GROUP_ID);
	}
	
	@Test
	public void queryAliasesTest() {
		List<String> groups = service.getList(ALIASES_TEST);
		assertEquals(1,groups.size());
	}
	
	@Test
	public void deleteCascateAliasesTest() {
		service.delete(ALIASES_TEST);
        verify(variableRepoMock, times(1)).deleteById(SCHEMA_ID);
        verify(schemaRepoMock, times(1)).deleteById(SCHEMA_ID);
        verify(groupRepoMock, times(1)).deleteById(GROUP_ID);
        verify(aliasesRepoMock, times(1)).deleteById(ALIASES_ID);
	}
	
	@Test
	public void createCascateGroupTest() {
		service.put(ALIASES_TEST,GROUP_TEST);
        verify(aliasesRepoMock, times(1)).save(any());
	}
	
	@Test
	public void createCascateSchemaTest() {
		service.put(ALIASES_TEST,GROUP_TEST,SCHEMA_TEST);
        verify(aliasesRepoMock, times(1)).save(any());
        verify(groupRepoMock, times(1)).save(any());
	}

	@Test
	public void queryValueTest() {
		service.put(ALIASES_TEST, GROUP_TEST, SCHEMA_TEST);
		assertEquals(VALUE_TEST, service.getList(ALIASES_TEST,GROUP_TEST,SCHEMA_TEST).get(0));
	}
	
	@Test
	public void createAliasesWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases(ALIASES_TEST)
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
        verify(aliasesRepoMock, times(1)).save(any());
	}
	
	@Test
	public void createGroupWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases(ALIASES_TEST)
										.group(GROUP_TEST)
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
        verify(groupRepoMock, times(1)).save(any());
	}
	
	@Test
	public void createSchemaWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases(ALIASES_TEST)
										.group(GROUP_TEST)
										.schema(SCHEMA_TEST)
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
        verify(schemaRepoMock, times(1)).save(any());
	}
	
	@Test
	public void createValueStringWithVariableTest() {
		Variable variable = Variable.builder()
				.aliases(ALIASES_TEST)
				.group(GROUP_TEST)
				.schema(SCHEMA_TEST)
				.value(VALUE_TEST)
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
        verify(variableRepoMock, times(1)).save(any());
	}
	
	@Test
	public void deleteAliasesWithVariableTest(){
		Variable variable = Variable.builder()
								.aliases(ALIASES_TEST)
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
        verify(aliasesRepoMock, times(1)).deleteById(ALIASES_ID);
	}
	
	@Test
	public void deleteGroupWithVariableTest(){
		Variable variable = Variable.builder()
								.aliases(ALIASES_TEST)
								.group(GROUP_TEST)
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
        verify(groupRepoMock, times(1)).deleteById(GROUP_ID);
	}
	
	@Test
	public void deleteSchemaWithVariableTest(){
        Variable variable = Variable.builder()
								.aliases(ALIASES_TEST)
								.group(GROUP_TEST)
								.schema(SCHEMA_TEST)
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
        verify(schemaRepoMock, times(1)).deleteById(SCHEMA_ID);
	}
	
	@Test
	public void deleteValueWithVariableTest(){
        Variable variable = Variable.builder()
								.aliases(ALIASES_TEST)
								.group(GROUP_TEST)
								.schema(SCHEMA_TEST)
								.value(VALUE_TEST)
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
        verify(variableRepoMock, times(1)).deleteById(SCHEMA_ID);
	}
	
}
