package com.bycasseb.config.service;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bycasseb.config.ds.PersistedAliases;
import com.bycasseb.config.ds.PersistedGroup;
import com.bycasseb.config.ds.PersistedSchema;
import com.bycasseb.config.ds.PersistedVariable;
import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.Variable;
import com.bycasseb.config.repository.AliasesRepository;
import com.bycasseb.config.repository.GroupRepository;
import com.bycasseb.config.repository.SchemaRepository;
import com.bycasseb.config.repository.VariableRepository;

@Service("mainService")
public class MainServiceImpl implements MainService{

	@Autowired
	private AliasesRepository aliasesRepo;
	@Autowired
	private GroupRepository groupRepo;
	@Autowired
	private SchemaRepository schemaRepo;
	@Autowired
	private VariableRepository variableRepo;
	@Autowired
	private ValidatorService validatorService;

	@Override
	public void put(String aliases) {
		aliasesRepo.save(new PersistedAliases(aliases));
	}
	
	@Override
	public void delete(String aliases) {
		aliasesRepo.deleteById(aliases);
		for(String group : getList(aliases)) {
			delete(aliases, group);
		}
	}
	
	@Override
	public boolean exists(String aliases) {
		return aliasesRepo.existsById(aliases);
	}

	@Override
	public void put(String aliases, String group) {
		if(group == null) {
			put(aliases);
			return;
		}
		PersistedGroup persistedGroup = new PersistedGroup(aliases, group);
		groupRepo.save(persistedGroup);
		put(aliases);
	}

	@Override
	public boolean exists(String aliases, String group) {
		if(group == null) {
			return exists(aliases);
		}else {
			return groupRepo.existsById(aliases + " |&| " + group);
		}
	}

	@Override
	public void delete(String aliases, String group) {
		groupRepo.deleteById(aliases + " |&| " + group);
		for(String schema : getList(aliases, group)) {
			delete(aliases, group, schema);
		}
	}

	@Override
	public void put(String aliases, String group, String schema) {
		PersistedSchema persistedSchema = new PersistedSchema(aliases, group, schema, Type.STRING);
		schemaRepo.save(persistedSchema);
		put(aliases, group);
		put(aliases, group, schema, Type.STRING.getTypeValidator().getDefault());
	}

	@Override
	public boolean exists(String aliases, String group, String schema) {
		return schemaRepo.existsById(aliases + " |&| " + group + " |&| " + schema);
	}

	@Override
	public void delete(String aliases, String group, String schema) {
		schemaRepo.deleteById(aliases + " |&| " + group + " |&| " + schema);
		variableRepo.deleteById(aliases + " |&| " + group + " |&| " + schema);
	}

	@Override
	public void put(String aliases, String group, String schema, Type type) {
		PersistedSchema persistedSchema = new PersistedSchema(aliases, group, schema, type);
		schemaRepo.save(persistedSchema);
		put(aliases, group, schema, type.getTypeValidator().getDefault());
	}

	@Override
	public void put(String aliases, String group, String schema, String value) {
		 
		Variable variable = Variable.builder()
										.aliases(aliases)
										.group(group)
										.type(Type.STRING)
										.schema(schema)
										.value(value)
									.build();
		
		if(!exists(aliases,group,schema)) {
			put(aliases,group,schema);
		}
		
		try {
			validatorService.execute(variable);
			variableRepo.save(new PersistedVariable(variable));
		} catch (Exception e) {
			e.printStackTrace();
		}							
	}

	@Override
	public boolean exists(String aliases, String group, String schema, String value) {
		Optional<PersistedVariable> optionalPersistedVariable = variableRepo.findById(aliases + " |&| " + group + " |&| " + schema);
		if(!optionalPersistedVariable.isPresent()) {
			return false;
		}
		return optionalPersistedVariable.get().getValue().equals(value);
	}

	@Override
	public void delete(String aliases, String group, String schema, String value) {
		
		if(isAliasesVariable(aliases, group, schema, null, value)) {
			delete(aliases);
			return;
		}
		
		if(isGroupVariable(aliases, group, schema, null, value)) {
			delete(aliases, group);
			return;
		}
		
		if(isSchemaVariable(aliases, group, schema, null, value)) {
			delete(aliases, group, schema);
			return;
		}
		
		if(isSchemaWithTypeVariable(aliases, group, schema, null, value)) {
			delete(aliases, group, schema);
			return;
		}
		
		if(isInvalidSchema(aliases, group, schema, null, value)) {
			return;
		}
		
		variableRepo.deleteById(aliases + " |&| " + group + " |&| " + schema);
	}

	@Override
	public List<String> getList(String aliases, String group) {
		List<String> result = new LinkedList<>();
		for(PersistedSchema schema : schemaRepo.findByAliasesAndGroup(aliases, group)) {
			result.add(schema.getSchema());
		}
		return result;
	}

	@Override
	public List<String> getList(String aliases) {
		List<String> result = new LinkedList<>();
		for(PersistedGroup group : groupRepo.findByAliases(aliases)) {
			result.add(group.getName());
		}
		return result;
	}

	@Override
	public List<String> getList(String aliases, String group, String schema) {
		if(isAliasesVariable(aliases, group, schema, null, null)) {
			return getList(aliases);
		}
		
		if(isGroupVariable(aliases, group, schema, null, null)) {
			return getList(aliases, group);
		}
		
		Optional<PersistedVariable> optionalVariable = variableRepo.findById(aliases + " |&| " + group + " |&| " + schema);
		List<String> result = new LinkedList<>();
		if(optionalVariable.isPresent()) {
			result.add(optionalVariable.get().getValue());
			return result; 
		}
		Optional<PersistedSchema> optionalSchema = schemaRepo.findById(aliases + " |&| " + group + " |&| " + schema);
		if(!optionalSchema.isPresent()) {
			result.add("SCHEMA NOT FOUND");
			return result;
		}
		result.add(optionalSchema.get().getType().getTypeValidator().getDefault());
		return result;
	}

	@Override
	public void put(String aliases, String group, String schema, Type type, String value) {
		
		if(isAliasesVariable(aliases, group, schema, type, value)) {
			put(aliases);
			return;
		}
		
		if(isGroupVariable(aliases, group, schema, type, value)) {
			put(aliases, group);
			return;
		}
		
		if(isSchemaVariable(aliases, group, schema, type, value)) {
			put(aliases, group, schema);
			return;
		}
		
		if(isSchemaWithTypeVariable(aliases, group, schema, type, value)) {
			put(aliases, group, schema, type);
			return;
		}
		
		if(isValueVariableWithoutType(aliases, group, schema, type, value)) {
			put(aliases, group, schema, value);
			return;
		}
		
		if(isInvalidSchema(aliases, group, schema, type, value)) {
			return;
		}
		
		Variable variable = Variable.builder()
										.aliases(aliases)
										.group(group)
										.type(type)
										.schema(schema)
										.value(value)
									.build();

		if(!exists(aliases,group,schema)) {
			put(aliases,group,schema);
		}

		try {
			validatorService.execute(variable);
			variableRepo.save(new PersistedVariable(variable));
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}

	private boolean isAliasesVariable(String aliases, String group, String schema, Type type, String value) {
		return 
				aliases != null &&
				group == null &&
				schema == null &&
				type == null &&
				value == null;
	}
	
	private boolean isGroupVariable(String aliases, String group, String schema, Type type, String value) {
		return 
				aliases != null && 
				group != null && 
				schema == null && 
				type == null && 
				value == null;
	}
	
	private boolean isInvalidSchema(String aliases, String group, String schema, Type type, String value) {
		return 	aliases == null || group == null;
	}
	
	private boolean isSchemaVariable(String aliases, String group, String schema, Type type, String value) {
		return 
				aliases != null && 
				group != null && 
				schema != null && 
				type == null && 
				value == null;
	}
	
	private boolean isValueVariableWithoutType(String aliases, String group, String schema, Type type, String value) {
		return 
				aliases != null &&
				group != null &&
				schema != null &&
				type == null &&
				value != null;
	}
	
	private boolean isSchemaWithTypeVariable(String aliases, String group, String schema, Type type, String value) {
		return 
				aliases != null && 
				group != null && 
				schema != null && 
				type != null && 
				value == null;
	}

	@Override
	public Type getType(String aliases, String group, String schema) {
		Optional<PersistedSchema> optionalSchema = schemaRepo.findById(aliases + " |&| " + group + " |&| " + schema);
		if(!optionalSchema.isPresent()) {
			return null;
		}
		return optionalSchema.get().getType();
	}

}
