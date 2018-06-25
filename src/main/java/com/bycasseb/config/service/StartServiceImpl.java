package com.bycasseb.config.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("startService")
public class StartServiceImpl implements StartService{
	
	@Override
	@PostConstruct
	public void execute() {

	}

}
