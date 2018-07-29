package com.bycasseb.config.ds;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Variable {
	
	private String aliases;
	private String group;
	private Type type;
	private String schema;
	private String value;
}
