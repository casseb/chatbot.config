package com.bycasseb.config.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycasseb.config.ds.PersistedVariable;
import com.bycasseb.config.repository.VariableRepository;

@Service("persistService")
public class PersistServiceImpl implements PersistService{

	@Autowired
	private VariableRepository variableRepo;
	
	@Override
	public void save(PersistedVariable variable) {
		variableRepo.save(variable);
	}

	@Override
	public Optional<PersistedVariable> query(String string) {
		return variableRepo.findById(string);
	}

}
