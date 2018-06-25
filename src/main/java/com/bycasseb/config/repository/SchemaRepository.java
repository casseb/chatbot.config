package com.bycasseb.config.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bycasseb.config.ds.PersistedSchema;

public interface SchemaRepository extends MongoRepository<PersistedSchema, String>{

	List<PersistedSchema> findByAliasesAndGroup(String aliases, String group);
	
}
