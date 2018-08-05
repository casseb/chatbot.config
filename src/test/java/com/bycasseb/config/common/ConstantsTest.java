package com.bycasseb.config.common;

import com.bycasseb.config.ds.Type;
import com.bycasseb.config.ds.User;

public interface ConstantsTest extends Constants{

	static final String ID_TEST = "1";
    static final String ALIASES_TEST = "Aliases Test";
    static final String GROUP_TEST = "Group Test";
    static final String SCHEMA_TEST = "Schema Test";
    static final Type TYPE_STRING_TEST = Type.STRING;
    static final Type TYPE_INTEGER_TEST = Type.INTEGER;
    static final String VALUE_TEST = "Value Test";
    static final String USER_TEST = "User Test";
    static final String PASSWORD_TEST = "Password Test";
    static final String INVALID_TEST = "Invalid Test";
    
    static final long TIMEOUT = 2500;

    static final User INSTANCE_USER_TEST = new User(USER_TEST, PASSWORD_TEST);

	static final String ALIASES_ID = ALIASES_TEST;
	static final String GROUP_ID = ALIASES_TEST + SEPARATOR + GROUP_TEST;
	static final String SCHEMA_ID = GROUP_ID + SEPARATOR + SCHEMA_TEST;
	
}
