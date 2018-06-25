package com.bycasseb.config.common;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.PermitAll;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.glassfish.jersey.internal.util.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bycasseb.config.service.UserService;

@Provider
@Component
public class JerseyAuthenticationFilter implements ContainerRequestFilter
{

    @Autowired
    private UserService userService;

    @Context
    private ResourceInfo resourceInfo;

    private static final String AUTHORIZATION_PROPERTY = "Authorization";
    private static final String AUTHENTICATION_SCHEME = "Basic";
    private static final Response ACCESS_DENIED = Response.status(Response.Status.UNAUTHORIZED).entity("You cannot access this resource").build();

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException
    {
        Method method = resourceInfo.getResourceMethod();

        if(method.isAnnotationPresent(PermitAll.class)) {
            return;
        }

        final List<String> authorization = requestContext.getHeaders().get(AUTHORIZATION_PROPERTY);
        if(authorization == null || authorization.isEmpty())
        {
            requestContext.abortWith(ACCESS_DENIED);
            return;
        }
        final String encodedUserPassword = authorization.get(0).replaceFirst(AUTHENTICATION_SCHEME + " ", "");
        String usernameAndPassword = new String(Base64.decode(encodedUserPassword.getBytes()));;
        final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
        final String username = tokenizer.nextToken();
        final String password = tokenizer.nextToken();

       if(!userService.login(username, password)) {
    	   requestContext.abortWith(ACCESS_DENIED);
           return;
       }

        requestContext.setProperty("current username", username);
    }
}
