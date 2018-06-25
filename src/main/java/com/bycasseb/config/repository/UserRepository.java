package com.bycasseb.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bycasseb.config.ds.User;

public interface UserRepository extends MongoRepository<User, String>{

}
