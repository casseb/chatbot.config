package com.bycasseb.config.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bycasseb.config.ds.PersistedGroup;

public interface GroupRepository extends MongoRepository<PersistedGroup, String>{

	public List<PersistedGroup> findByAliases(String aliases);
	
}
