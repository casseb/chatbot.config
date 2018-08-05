package com.bycasseb.config.rest;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.bycasseb.config.service.UserService;

public class SuccessTests extends RestTest {

	@Autowired
	private UserService userService;
    
	@Test
    public void createNewUser(){
		assertFalse(userService.exists(USER_TEST));
		
		given()
                .body(INSTANCE_USER_TEST)
                .contentType(HEADER_JSON)
        .when()
                .post(ROUTE_NEW_USER)
        .then()
                .statusCode(201)
                .time(lessThan(TIMEOUT));
				
        
        assertTrue(userService.exists(USER_TEST));
    }

}
