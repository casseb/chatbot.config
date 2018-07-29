package com.bycasseb.config.common;

import com.bycasseb.config.ds.Type;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.data.repository.Repository;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import com.bycasseb.config.repository.AliasesRepository;
import com.bycasseb.config.repository.GroupRepository;
import com.bycasseb.config.repository.SchemaRepository;
import com.bycasseb.config.repository.UserRepository;
import com.bycasseb.config.repository.VariableRepository;

@Component
@DataMongoTest(includeFilters = @Filter(Repository.class))
@ActiveProfiles("test")
public class TestSupport extends MainConstants {

    protected static final String ID_TEST = "1";
    protected static final String ALIASES_TEST = "Aliases Test";
    protected static final String GROUP_TEST = "Group Test";
    protected static final String SCHEMA_TEST = "Schema Test";
    protected static final Type TYPE_TEST = Type.STRING;
    protected static final String VALUE_TEST = "Value Test";
    protected static final String USER_TEST = "User Test";

	@Autowired
	private AliasesRepository aliasesRepo;
	@Autowired
	private GroupRepository groupRepo;
	@Autowired
	private SchemaRepository schemaRepo;
	@Autowired
	private VariableRepository variableRepo;
	@Autowired
	private UserRepository userRepo;
	
	@Before
	public void before(){
	    aliasesRepo.deleteAll();
		groupRepo.deleteAll();
		schemaRepo.deleteAll();
		variableRepo.deleteAll();
		userRepo.deleteAll();
	}
	
}
