package com.bycasseb.config.ds;

import com.bycasseb.config.common.MainConstants;
import org.springframework.data.annotation.Id;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class PersistedVariable extends Variable{
	
	@Id
	private String id;

	public PersistedVariable(Variable variable) {
		this.id = variable.getAliases()+" |&| "+variable.getGroup()+ MainConstants.SEPARATOR +variable.getSchema();
		this.setAliases(variable.getAliases());
		this.setGroup(variable.getGroup());
		this.setType(variable.getType());
		this.setSchema(variable.getSchema());
		this.setValue(variable.getValue());
	}
}
