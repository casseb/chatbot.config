package com.bycasseb.config.rest;

import org.junit.Before;
import org.junit.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;

import static io.restassured.RestAssured.port;

import com.bycasseb.config.common.ClassTest;
import com.bycasseb.config.repository.UserRepository;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore
public class RestTest extends ClassTest{

	@LocalServerPort
    public int PORT;
	
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void before() {
		userRepo.deleteAll();
		port = PORT;
	}
	
}
