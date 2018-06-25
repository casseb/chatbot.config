package com.bycasseb.config.service;

import com.bycasseb.config.ds.User;

public interface UserService {

	public User query(String username);

	public void newUser(String userName, String password);

	public boolean exists(String userName);

	public boolean login(String userName, String password);

}
