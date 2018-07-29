package com.bycasseb.config.service;

import java.util.List;

import com.bycasseb.config.ds.Type;

public interface MainService {
	
	void put(String aliases);
	void put(String aliases, String group);
	void put(String aliases, String group, String schema);
	void put(String aliases, String group, String schema, Type type);
	void put(String aliases, String group, String schema, String value);
	void put(String aliases, String group, String schema, Type type, String value);
	
	boolean exists(String group);
	boolean exists(String aliases, String group);
	boolean exists(String aliases, String group, String schema);
	boolean exists(String aliases, String group, String schema, String value);
	
	List<String> getList(String aliases);
	List<String> getList(String aliases, String group);
	List<String> getList(String aliases, String group, String schema);
	
	void delete(String aliases);
	void delete(String aliases, String group);
	void delete(String aliases, String group, String schema);
	void delete(String aliases, String group, String schema, String value);
	
	Type getType(String aliases, String group, String schema);
	
}
