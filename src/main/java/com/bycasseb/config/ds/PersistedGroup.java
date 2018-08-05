package com.bycasseb.config.ds;

import com.bycasseb.config.common.Constants;
import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersistedGroup {

	@Id
	private String id;
	@NonNull
	private String aliases;
	@NonNull
	private String name;
	
	
	public PersistedGroup(String aliases, String name) {
		super();
		this.id = aliases + Constants.SEPARATOR + name;
		this.aliases = aliases;
		this.name = name;
	}
	
}
