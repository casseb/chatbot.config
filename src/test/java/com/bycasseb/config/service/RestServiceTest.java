package com.bycasseb.config.service;

import static org.junit.Assert.*;

import javax.ws.rs.core.Response;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.bycasseb.config.common.TestSupport;
import com.bycasseb.config.ds.GetListResponseBody;
import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.TypeResponseBody;
import com.bycasseb.config.ds.User;
import com.bycasseb.config.ds.ValueResponseBody;
import com.bycasseb.config.ds.Variable;

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class RestServiceTest extends TestSupport{

	@Autowired
	private RestService restService;
	@Autowired
	private MainService mainService;
	@Autowired
	private UserService userService;
	
	@Test
	public void createAliasesRestServiceTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
			.build();
		
		Response response = restService.put(variable);
		
		assertEquals(201,response.getStatus());
		assertTrue(mainService.exists("Aliases Test"));
	}
	
	@Test
	public void createGroupRestServiceTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
			.build();
		
		Response response = restService.put(variable);
		
		assertEquals(201,response.getStatus());
		assertTrue(mainService.exists("Aliases Test", "Group Test"));
	}
	
	@Test
	public void createSchemaRestServiceTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
			.build();
		
		Response response = restService.put(variable);
		
		assertEquals(201,response.getStatus());
		assertTrue(mainService.exists("Aliases Test", "Group Test", "Schema Test"));
	}
	
	@Test
	public void createValueRestServiceTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
				.value("Value Test")
			.build();
		
		Response response = restService.put(variable);
		
		assertEquals(201,response.getStatus());
		assertTrue(mainService.exists("Aliases Test", "Group Test", "Schema Test", "Value Test"));
	}
	
	@Test
	public void createIntegerValueRestServiceTest() {
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
				.type(Type.INTEGER)
				.value("1")
			.build();
		
		Response response = restService.put(variable);
		
		assertEquals(201,response.getStatus());
		assertTrue(mainService.exists("Aliases Test", "Group Test", "Schema Test", "1"));
	}
	
	@Test
	public void deleteAliasesRestServiceTest() {
		mainService.put("Aliases Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
			.build();
		
		Response response = restService.delete(variable);
		
		assertEquals(204,response.getStatus());
		assertFalse(mainService.exists("Aliases Test"));
	}
	
	@Test
	public void deleteGroupRestServiceTest() {
		mainService.put("Aliases Test", "Group Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
			.build();
		
		Response response = restService.delete(variable);
		
		assertEquals(204,response.getStatus());
		assertFalse(mainService.exists("Aliases Test", "Group Test"));
	}
	
	@Test
	public void deleteSchemaRestServiceTest() {
		mainService.put("Aliases Test", "Group Test", "Schema Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
			.build();
		
		Response response = restService.delete(variable);
		
		assertEquals(204,response.getStatus());
		assertFalse(mainService.exists("Aliases Test", "Group Test", "Schema Test"));
	}
	
	@Test
	public void deleteValueRestServiceTest() {
		mainService.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
				.value("Value Test")
			.build();
		
		Response response = restService.delete(variable);
		
		assertEquals(204,response.getStatus());
		assertFalse(mainService.exists("Aliases Test", "Group Test", "Schema Test", "Value Test"));
		assertTrue(mainService.exists("Aliases Test", "Group Test", "Schema Test"));
	}
	
	@Test
	public void queryAliasesRestServiceTest() {
		mainService.put("Aliases Test", "Group Test 1");
		mainService.put("Aliases Test", "Group Test 2");
		mainService.put("Aliases Test", "Group Test 3");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
			.build();
		
		Response response = restService.getList(variable);
		
		GetListResponseBody body = GetListResponseBody.builder()
										.addResult("Group Test 1")
										.addResult("Group Test 2")
										.addResult("Group Test 3")
									.build();
		assertEquals(body, response.getEntity());
	}
	
	@Test
	public void queryGroupRestServiceTest() {
		mainService.put("Aliases Test", "Group Test", "Schema Test 1");
		mainService.put("Aliases Test", "Group Test", "Schema Test 2");
		mainService.put("Aliases Test", "Group Test", "Schema Test 3");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
			.build();
		
		Response response = restService.getList(variable);
		
		GetListResponseBody body = GetListResponseBody.builder()
										.addResult("Schema Test 1")
										.addResult("Schema Test 2")
										.addResult("Schema Test 3")
									.build();
		assertEquals(body, response.getEntity());
	}
	
	@Test
	public void querySchemaRestServiceTest() {
		mainService.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
			.build();
		
		Response response = restService.get(variable);
		
		ValueResponseBody body = ValueResponseBody.builder()
									.value("Value Test")
								.build();
		
		assertEquals(body, response.getEntity());
	}
	
	@Test
	public void queryTypeRestServiceTest() {
		mainService.put("Aliases Test", "Group Test", "Schema Test", "Value Test");
		
		Variable variable = Variable.builder()
				.aliases("Aliases Test")
				.group("Group Test")
				.schema("Schema Test")
			.build();
		
		Response response = restService.getType(variable);
		
		TypeResponseBody body = TypeResponseBody.builder()
									.type(Type.STRING)
								.build();
		
		assertEquals(body, response.getEntity());
	}
	
	@Test
	public void createNewUser() {
		User user = new User("User Test", "Password Test");
		Response response = restService.newUser(user);
		
		assertEquals(201,response.getStatus());
		assertTrue(userService.exists("User Test"));
	}
}
