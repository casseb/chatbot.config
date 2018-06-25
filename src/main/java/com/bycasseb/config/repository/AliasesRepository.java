package com.bycasseb.config.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.bycasseb.config.ds.PersistedAliases;

public interface AliasesRepository extends MongoRepository<PersistedAliases, String>{

}
