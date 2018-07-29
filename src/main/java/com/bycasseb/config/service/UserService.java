package com.bycasseb.config.service;

import com.bycasseb.config.ds.User;

public interface UserService {

	User query(String username);

	void newUser(String userName, String password);

	boolean exists(String userName);

	boolean login(String userName, String password);

}
