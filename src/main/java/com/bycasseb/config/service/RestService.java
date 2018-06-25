package com.bycasseb.config.service;

import javax.ws.rs.core.Response;

import org.springframework.http.ResponseEntity;

import com.bycasseb.config.ds.User;
import com.bycasseb.config.ds.Variable;

public interface RestService {

	public Response put(Variable variable);

	public Response delete(Variable variable);

	public Response getList(Variable variable);

	public Response get(Variable variable);

	public Response getType(Variable variable);

	public Response newUser(User user);

	

}
