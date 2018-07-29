package com.bycasseb.config.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycasseb.config.ds.User;
import com.bycasseb.config.repository.UserRepository;

@Service("userService")
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public User query(String username) {
		if(exists(username)) return userRepo.findById(username).get();
		return null;
	}

	@Override
	public void newUser(String userName, String password) {
		userRepo.save(new User(userName, password));
	}

	@Override
	public boolean exists(String username) {
		return userRepo.findById(username).isPresent();
	}

	@Override
	public boolean login(String userName, String password) {
		if(!exists(userName)) {
			return false;
		}

		return query(userName).getPassword().equals(password);
	}

}
