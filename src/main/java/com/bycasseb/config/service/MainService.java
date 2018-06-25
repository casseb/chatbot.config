package com.bycasseb.config.service;

import java.util.List;

import com.bycasseb.config.ds.Type;

public interface MainService {
	
	public void put(String aliases);
	public void put(String aliases, String group);
	public void put(String aliases, String group, String schema);
	public void put(String aliases, String group, String schema, Type type);
	public void put(String aliases, String group, String schema, String value);
	public void put(String aliases, String group, String schema, Type type, String value);
	
	public boolean exists(String group);
	public boolean exists(String aliases, String group);
	public boolean exists(String aliases, String group, String schema);
	public boolean exists(String aliases, String group, String schema, String value);
	
	public List<String> getList(String aliases);
	public List<String> getList(String aliases, String group);
	public List<String> getList(String aliases, String group, String schema);
	
	public void delete(String aliases);
	public void delete(String aliases, String group);
	public void delete(String aliases, String group, String schema);
	public void delete(String aliases, String group, String schema, String value);
	
	public Type getType(String aliases, String group, String schema);
	
}
