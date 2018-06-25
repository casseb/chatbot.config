package com.bycasseb.config.service;

import java.util.Optional;

import com.bycasseb.config.ds.PersistedVariable;

public interface PersistService {

	public void save(PersistedVariable variable);

	public Optional<PersistedVariable> query(String string);

}
