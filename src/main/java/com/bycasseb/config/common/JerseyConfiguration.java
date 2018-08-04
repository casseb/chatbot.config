package com.bycasseb.config.common;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;
import org.springframework.context.annotation.Configuration;

import com.bycasseb.config.controller.MainRestController;

@Configuration
class JerseyConfiguration extends ResourceConfig{

	public JerseyConfiguration() {
		property(ServerProperties.RESPONSE_SET_STATUS_OVER_SEND_ERROR, true);
		register(JerseyAuthenticationFilter.class);
		
		register(MainRestController.class);
	}
	
}
