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

@RunWith(SpringRunner.class)
@DataMongoTest(includeFilters = @Filter(Service.class))
@ActiveProfiles("test")
public class UserServiceTest {

	@Autowired
	private UserService userService;
	
	@Test
	public void createUser() {
		userService.newUser("User Test", "Password Test");
		assertTrue(userService.exists("User Test"));
	}
	
	@Test
	public void successLogin() {
		userService.newUser("User Test", "Password Test");
		assertTrue(userService.login("User Test", "Password Test"));
	}
	
	@Test
	public void incorrectUserName() {
		userService.newUser("User Test", "Password Test");
		assertFalse(userService.login("User Test Incorrect", "Password Test"));
	}
	
	@Test
	public void incorrectPassword() {
		userService.newUser("User Test", "Password Test");
		assertFalse(userService.login("User Test", "Password Test Incorrect"));
	}
	
}
