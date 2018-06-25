package com.bycasseb.config.ds;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NonNull;

@Data
public class PersistedAliases {

	@Id
	@NonNull
	private String id;
		
}
