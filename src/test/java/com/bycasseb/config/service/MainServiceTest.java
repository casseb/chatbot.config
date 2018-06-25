package com.bycasseb.config.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

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

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class MainServiceTest extends TestSupport{
	
	@Autowired
	private MainService service;
	
	@Test
	public void createAliasesTest() {
		service.put("Aliases Test");
		assertTrue(service.exists("Aliases Test"));
	}
	
	@Test
	public void deleteAliasesTest() {
		service.put("Aliases Test");
		service.delete("Aliases Test");
		assertFalse(service.exists("Aliases Test"));
	}
	
	@Test
	public void createGroupTest() {
		service.put("Aliases Test","Group Test");
		assertTrue(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void deleteGroupTest() {
		service.put("Aliases Test","Group Test");
		service.delete("Aliases Test","Group Test");
		assertFalse(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void createSchemaStringTest() {
		service.put("Aliases Test","Group Test", "Schema Test");
		assertTrue(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void createSchemaIntegerTest() {
		service.put("Aliases Test","Group Test", "Schema Test", Type.INTEGER);
		assertTrue(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void deleteSchemaTest() {
		service.put("Aliases Test","Group Test", "Schema Test");
		service.delete("Aliases Test","Group Test", "Schema Test");
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void createValueTest() {
		service.put("Aliases Test","Group Test", "Schema Test", "Value Test");
		assertTrue(service.exists("Aliases Test","Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void deleteValueTest() {
		service.put("Aliases Test","Group Test", "Schema Test", "Value Test");
		service.delete("Aliases Test","Group Test", "Schema Test", "Value Test");
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void deleteCascateSchemaTest() {
		service.put("Aliases Test","Group Test", "Schema Test");
		service.put("Aliases Test","Group Test", "Schema Test", "Value Test");
		service.delete("Aliases Test","Group Test", "Schema Test");
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void queryGroupTest() {
		service.put("Aliases Test","Group Test", "Schema Test 1");
		service.put("Aliases Test","Group Test", "Schema Test 2");
		service.put("Aliases Test","Group Test", "Schema Test 3");
		List<String> schemas = service.getList("Aliases Test","Group Test");
		assertEquals(3,schemas.size());
	}
	
	@Test
	public void deleteCascateGroupTest() {
		service.put("Aliases Test","Group Test");
		service.put("Aliases Test","Group Test", "Schema Test");
		service.put("Aliases Test","Group Test", "Schema Test", "Value Test");
		service.delete("Aliases Test","Group Test");
		assertFalse(service.exists("Aliases Test","Group Test"));
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void queryAliasesTest() {
		service.put("Aliases Test", "Group Test 1");
		service.put("Aliases Test", "Group Test 2");
		service.put("Aliases Test", "Group Test 3");
		
		List<String> groups = service.getList("Aliases Test");
		assertEquals(3,groups.size());
	}
	
	@Test
	public void deleteCascateAliasesTest() {
		service.put("Aliases Test");
		service.put("Aliases Test","Group Test");
		service.put("Aliases Test","Group Test", "Schema Test");
		service.put("Aliases Test","Group Test", "Schema Test", "Value Test");
		service.delete("Aliases Test");
		assertFalse(service.exists("Aliases Test"));
		assertFalse(service.exists("Aliases Test","Group Test"));
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void createCascateGroupTest() {
		service.put("Aliases Test","Group Test");
		assertTrue(service.exists("Aliases Test"));
	}
	
	@Test
	public void createCascateSchemaTest() {
		service.put("Aliases Test","Group Test","Schema Test");
		assertTrue(service.exists("Aliases Test"));
		assertTrue(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void createCascateValueTest() {
		service.put("Aliases Test","Group Test","Schema Test","Value Test");
		assertTrue(service.exists("Aliases Test"));
		assertTrue(service.exists("Aliases Test","Group Test"));
		assertTrue(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void queryValueTest() {
		service.put("Aliases Test","Group Test","Schema Test","Value Test");
		assertEquals("Value Test", service.getList("Aliases Test","Group Test","Schema Test").get(0));
	}
	
	@Test
	public void defaultValueStringTest() {
		service.put("Aliases Test","Group Test","Schema Test");
		assertEquals("NOT_SET",service.getList("Aliases Test","Group Test","Schema Test").get(0));
	}
	
	@Test
	public void defaultValueIntegerTest() {
		service.put("Aliases Test","Group Test","Schema Test", Type.INTEGER);
		assertEquals("0", service.getList("Aliases Test","Group Test","Schema Test").get(0));
	}
	
	@Test
	public void createAliasesWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases("Aliases Test")
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertTrue(service.exists("Aliases Test"));
	}
	
	@Test
	public void createGroupWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases("Aliases Test")
										.group("Group Test")
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertTrue(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void createGroupWithoutAliasesVariableTest() {
		Variable variable = Variable.builder()
										.group("Group Test")
									.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists(null,"Group Test"));
	}
	
	@Test
	public void createSchemaWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases("Aliases Test")
										.group("Group Test")
										.schema("Schema Test")
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertTrue(service.exists("Aliases Test","Group Test","Schema Test"));
	}
	
	@Test
	public void createSchemaWithTypeWithVariableTest() {
		
		Variable variable = Variable.builder()
										.aliases("Aliases Test")
										.group("Group Test")
										.schema("Schema Test")
										.type(Type.INTEGER)
									.build();
		
		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertEquals(Type.INTEGER, service.getType("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void createSchemaWithoutAliasesTest() {
		Variable variable = Variable.builder()
				.group("Group Test")
				.schema("Schema Test")
				.type(Type.INTEGER)
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void createSchemaWithoutGroupTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.schema("Schema Test")
				.type(Type.INTEGER)
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void createValueStringWithVariableTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
				.value("Value Test")
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertEquals("Value Test", service.getList("Aliases Test","Group Test","Schema Test").get(0));
	}
	
	@Test
	public void createValueIntegerWithVariableTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
				.type(Type.INTEGER)
				.value("1")
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertEquals("1", service.getList("Aliases Test","Group Test","Schema Test").get(0));
	}
	
	@Test
	public void createValueStringWithVariableWithoutAliasesTest() {
		Variable variable = Variable.builder()
				.group("Group Test")
				.schema("Schema Test")
				.value("Value Test")
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test","Schema Test"));
	}
	
	@Test
	public void createValueStringWithVariableWithoutGroupTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.schema("Schema Test")
				.value("Value Test")
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test","Schema Test"));
	}
	
	@Test
	public void createValueStringWithVariableWithoutKeyTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.value("Value Test")
			.build();

		service.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test","Schema Test"));
	}
	
	@Test
	public void deleteAliasesWithVariableTest(){
		service.put("Aliases Test");
		
		Variable variable = Variable.builder()
								.aliases("Aliases Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertFalse(service.exists("Aliases Test"));
	}
	
	@Test
	public void deleteGroupWithVariableTest(){
		service.put("Aliases Test","Group Test");
		
		Variable variable = Variable.builder()
								.aliases("Aliases Test")
								.group("Group Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void deleteGroupWithVariableWithoutAliasesTest(){
		service.put("Aliases Test","Group Test");
		
		Variable variable = Variable.builder()
								.group("Group Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test","Group Test"));
	}
	
	@Test
	public void deleteSchemaWithVariableTest(){
		service.put("Aliases Test", "Group Test", "Schema Test");
		
		Variable variable = Variable.builder()
								.aliases("Aliases Test")
								.group("Group Test")
								.schema("Schema Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertFalse(service.exists("Aliases Test","Group Test", "Schema Test"));
	}
	
	@Test
	public void deleteSchemaWithVariableWithoutAliasesTest(){
		service.put("Aliases Test", "Group Test", "Schema Test");
		
		Variable variable = Variable.builder()
									.group("Group Test")
									.schema("Schema Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test", "Group Test", "Schema Test"));
	}
	
	@Test
	public void deleteSchemaWithVariableWithoutGroupTest(){
		service.put("Aliases Test", "Group Test", "Schema Test");
		
		Variable variable = Variable.builder()
									.aliases("Aliases Test")
									.schema("Schema Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test", "Group Test", "Schema Test"));
	}
	
	@Test
	public void deleteValueWithVariableTest(){
		service.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
								.aliases("Aliases Test")
								.group("Group Test")
								.schema("Schema Test")
								.value("Value Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertEquals("NOT_SET", service.getList("Aliases Test", "Group Test", "Schema Test").get(0));
	}
	
	@Test
	public void deleteValueWithVariableWithoutAliasesTest(){
		service.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
									.group("Group Test")
									.schema("Schema Test")
									.value("Value Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test", "Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void deleteValueWithVariableWithoutGroupTest(){
		service.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
									.aliases("Aliases Test")
									.schema("Schema Test")
									.value("Value Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test", "Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void deleteValueWithVariableWithoutSchemaTest(){
		service.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
									.aliases("Aliases Test")
									.group("Group Test")
									.value("Value Test")
							.build();
		service.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		assertTrue(service.exists("Aliases Test", "Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void queryAliasesWithVariableTest() {
		service.put("Aliases Test", "Group Test 1");
		service.put("Aliases Test", "Group Test 2");
		service.put("Aliases Test", "Group Test 3");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
			.build();
		
		assertEquals(3,service.getList(variable.getAliases(), variable.getGroup(), variable.getSchema()).size());
	}
	
	@Test
	public void queryGroupWithVariableTest() {
		service.put("Aliases Test", "Group Test", "Schema Test 1");
		service.put("Aliases Test", "Group Test", "Schema Test 2");
		service.put("Aliases Test", "Group Test", "Schema Test 3");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
			.build();
		
		assertEquals(3,service.getList(variable.getAliases(), variable.getGroup(), variable.getSchema()).size());
	}
	
	@Test
	public void queryGroupWithVariableWithoutAliasesTest() {
		service.put("Aliases Test", "Group Test", "Schema Test 1");
		service.put("Aliases Test", "Group Test", "Schema Test 2");
		service.put("Aliases Test", "Group Test", "Schema Test 3");
		
		Variable variable = Variable.builder()
				.group("Group Test")
			.build();
		
		assertEquals("SCHEMA NOT FOUND",service.getList(variable.getAliases(), variable.getGroup(), variable.getSchema()).get(0));
	}
	
}
