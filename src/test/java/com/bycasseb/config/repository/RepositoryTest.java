package com.bycasseb.config.repository;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Repository;

import com.bycasseb.config.common.ClassTest;
import com.bycasseb.config.ds.PersistedAliases;
import com.bycasseb.config.ds.PersistedGroup;
import com.bycasseb.config.ds.PersistedSchema;
import com.bycasseb.config.ds.PersistedVariable;
import com.bycasseb.config.ds.User;

@SpringBootTest
public class RepositoryTest extends ClassTest{

    @Autowired
    private VariableRepository variableRepo;
    @Autowired
    private AliasesRepository aliasesRepo;
    @Autowired
    private GroupRepository groupRepo;
    @Autowired
    private SchemaRepository schemaRepo;
    @Autowired
    private UserRepository userRepo;

    @Test
    public void saveAndFindVariableTest(){
        variableRepo.deleteById(ID_TEST);

        PersistedVariable value = new PersistedVariable();
        value.setId(ID_TEST);
        variableRepo.save(value);

        assertTrue(variableRepo.existsById(ID_TEST));
    }

    @Test
    public void saveAndFindAliasesTest(){
        aliasesRepo.deleteById(ID_TEST);

        PersistedAliases value = new PersistedAliases(ID_TEST);
        aliasesRepo.save(value);

        assertTrue(aliasesRepo.existsById(ID_TEST));
    }

    @Test
    public void saveAndFindGroupTest(){
        groupRepo.deleteById(ID_TEST);

        PersistedGroup value = new PersistedGroup(ALIASES_TEST, GROUP_TEST);
        groupRepo.save(value);

        assertTrue(groupRepo.existsById(ALIASES_TEST + SEPARATOR + GROUP_TEST));
    }

    @Test
    public void saveAndFindSchemaTest(){
        schemaRepo.deleteById(ID_TEST);

        PersistedSchema value = new PersistedSchema(ALIASES_TEST, GROUP_TEST, SCHEMA_TEST, TYPE_STRING_TEST);
        schemaRepo.save(value);

        assertTrue(schemaRepo.existsById(ALIASES_TEST + SEPARATOR + GROUP_TEST + SEPARATOR + SCHEMA_TEST));
    }

    @Test
    public void saveAndFindUserTest(){
        userRepo.deleteById(ID_TEST);

        User value = new User();
        value.setUserName(USER_TEST);
        userRepo.save(value);

        assertTrue(userRepo.existsById(USER_TEST));
    }
}