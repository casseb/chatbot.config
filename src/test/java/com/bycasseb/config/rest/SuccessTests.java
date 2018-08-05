package com.bycasseb.config.rest;

import static io.restassured.RestAssured.given;

import org.junit.Test;

public class SuccessTests extends RestTest {

    @Test
    public void createNewUser(){
        given()
                .body(INSTANCE_USER_TEST)
                .contentType("application/json")
        .when()
                .post(ROUTE_NEW_USER)
        .then()
                .statusCode(201);
    }

}
