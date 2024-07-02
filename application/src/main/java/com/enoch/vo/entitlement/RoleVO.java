package com.enoch.vo.entitlement;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleVO {
	private Long id;
	private String qualifier;
	private String name;
	private String code;
	private String description;
	private Boolean status;
	private Boolean deflt;

}
