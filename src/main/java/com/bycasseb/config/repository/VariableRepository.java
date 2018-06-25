package com.bycasseb.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bycasseb.config.ds.PersistedVariable;

public interface VariableRepository extends MongoRepository<PersistedVariable, String>{

}
