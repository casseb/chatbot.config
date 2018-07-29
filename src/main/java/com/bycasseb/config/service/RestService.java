package com.bycasseb.config.service;

import javax.ws.rs.core.Response;

import com.bycasseb.config.ds.User;
import com.bycasseb.config.ds.Variable;

public interface RestService {

	Response put(Variable variable);

	Response delete(Variable variable);

	Response getList(Variable variable);

	Response get(Variable variable);

	Response getType(Variable variable);

	Response newUser(User user);

	

}
