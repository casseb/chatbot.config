package com.bycasseb.config.ds;

import com.bycasseb.config.common.MainConstants;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersistedSchema {
	
	@Id
	private String id;
	@NonNull
	private String aliases;
	@NonNull
	private String group;
	@NonNull
	private String schema;
	@NonNull
	private Type type;
	
	public PersistedSchema(String aliases, String group, String schema, Type type) {
		super();
		this.id = aliases + MainConstants.SEPARATOR + group + MainConstants.SEPARATOR + schema;
		this.aliases = aliases;
		this.group = group;
		this.schema = schema;
		this.type = type;
	}
	
}
