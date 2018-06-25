package com.bycasseb.config.service;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycasseb.config.ds.GetListResponseBody;
import com.bycasseb.config.ds.TypeResponseBody;
import com.bycasseb.config.ds.User;
import com.bycasseb.config.ds.ValueResponseBody;
import com.bycasseb.config.ds.Variable;

@Service("restService")
public class RestServiceImpl implements RestService{

	@Autowired
	private MainService mainService;
	@Autowired
	private UserService userService;

	@Override
	public Response put(Variable variable) {
		mainService.put(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getType(), variable.getValue());
		return Response.status(Status.CREATED).build();
	}

	@Override
	public Response delete(Variable variable) {
		mainService.delete(variable.getAliases(), variable.getGroup(), variable.getSchema(), variable.getValue());
		return Response.status(Status.NO_CONTENT).build();
	}

	@Override
	public Response getList(Variable variable) {
		return Response.status(Status.OK).entity(
					GetListResponseBody.builder().result(
						mainService.getList(variable.getAliases(), variable.getGroup(), variable.getSchema())
					).build()
				).build();
	}

	@Override
	public Response get(Variable variable) {
		return Response.status(Status.OK).entity(
				ValueResponseBody.builder()
				.value(
					mainService.getList(variable.getAliases(), variable.getGroup(), variable.getSchema()).get(0)
				).build()
			).build();
	}

	@Override
	public Response getType(Variable variable) {
		return Response.status(Status.OK).entity(
				TypeResponseBody.builder()
				.type(
					mainService.getType(variable.getAliases(), variable.getGroup(), variable.getSchema())
				).build()
			).build();
	}

	@Override
	public Response newUser(User user) {
		if(userService.exists(user.getUserName())) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		userService.newUser(user.getUserName(), user.getPassword());
		return Response.status(Status.CREATED).build();
	}

}
