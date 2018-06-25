package com.bycasseb.config.ds;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
@EqualsAndHashCode
public class ValueResponseBody {

	private String value;
	
}
