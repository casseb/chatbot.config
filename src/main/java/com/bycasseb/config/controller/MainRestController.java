package com.bycasseb.config.controller;

import org.glassfish.jersey.internal.util.Base64;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.User;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.service.RestService;

@Component
@Path("/")
@CrossOrigin(origins = "*")
public class MainRestController {
	
	@Autowired
	private RestService restService;
	
	@POST
	@PermitAll
	@Path("/newUser")
	public Response newUser(User user) {
		return restService.newUser(user);
	}
	
	@PUT
	@Path("{group}")
	public Response putGroup(@PathParam("group") String group, @HeaderParam("Authorization") String auth) {
		
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
			.build();
		return restService.put(variable);
	}
	
	@GET
	@Produces("application/json")
	public Response getGroups(@HeaderParam("Authorization") String auth) {
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
			.build();
		return restService.getList(variable);
	}
	
	@DELETE
	@Path("{group}")
	@Produces("application/json")
	public Response deleteGroups(@PathParam("group") String group, @HeaderParam("Authorization") String auth) {
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
			.build();
		return restService.delete(variable);
	}
	
	@PUT
	@Path("{group}/{schema}")
	public Response putSchema(@PathParam("group") String group, @PathParam("schema") String schema, @HeaderParam("Authorization") String auth) {
		
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
				.schema(schema)
			.build();
		return restService.put(variable);
	}
	
	@GET
	@Path("{group}")
	@Produces("application/json")
	public Response getGroups(@PathParam("group") String group, @HeaderParam("Authorization") String auth) {
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
			.build();
		return restService.getList(variable);
	}
	
	@DELETE
	@Path("{group}/{schema}")
	@Produces("application/json")
	public Response deleteSchema(@PathParam("group") String group, @PathParam("schema") String schema, @HeaderParam("Authorization") String auth) {
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
				.schema(schema)
			.build();
		return restService.delete(variable);
	}
	
	@PUT
	@Path("{group}/{schema}")
	@Consumes("application/json")
	public Response putSchemaWithType(@PathParam("group") String group, 
									  @PathParam("schema") String schema,
									  @QueryParam("type") Type type, 
									  @HeaderParam("Authorization") String auth
									  ) {
		
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
				.schema(schema)
				.type(type)
			.build();
		return restService.put(variable);
	}
	
	@GET
	@Path("{group}/{schema}")
	@Produces("application/json")
	public Response getTypeSchema(@PathParam("group") String group, 
								  @PathParam("schema") String schema,
								  @HeaderParam("Authorization") String auth,
								  @DefaultValue("Value") @QueryParam("mode") String mode
									) {
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
								.aliases(aliases)
								.group(group)
								.schema(schema)
							.build();
		
		if(mode.equals("Type")) {
			return restService.getType(variable);
		}
		return restService.get(variable);
	}
	
	@PUT
	@Path("{group}/{schema}/{value}")
	@Consumes("application/json")
	public Response putValue(@PathParam("group") String group, 
									  @PathParam("schema") String schema,
									  @PathParam("value") String value,
									  @DefaultValue("STRING") @QueryParam("type") Type type,
									  @HeaderParam("Authorization") String auth
									  ) {
		
		String aliases = getCurrentUser(auth);
		
		Variable variable = Variable.builder()
				.aliases(aliases)
				.group(group)
				.schema(schema)
				.type(type)
				.value(value)
			.build();
		return restService.put(variable);
	}
	
	@PUT
	@Consumes("application/json")
	public Response putWithVariable(Variable variable, @HeaderParam("Authorization") String auth) {
		variable.setAliases(getCurrentUser(auth));
		return restService.put(variable);
	}
	
	@DELETE
	@Consumes("application/json")
	public Response deleteWithVariable(Variable variable, @HeaderParam("Authorization") String auth) {
		variable.setAliases(getCurrentUser(auth));
		return restService.delete(variable);
	}
	
	private String getCurrentUser(String auth)
    {
        final String encodedUserPassword = auth.replaceFirst("Basic ", "");
        String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		return tokenizer.nextToken();
    }
	
	
}
