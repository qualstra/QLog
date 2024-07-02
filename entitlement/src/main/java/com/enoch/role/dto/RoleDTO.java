package com.enoch.role.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.role.model.Role;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleDTO implements Transform<Role>{

	private Long id;
	// Company Id
	private String qualifier;
	private String name;
	private String code;
	private String description;
	private Boolean status;
	private Boolean deflt;
	
	public RoleDTO(Long id){
		this.id = id;
	}
	@Override
	public Role transform() {
		Role role = new Role();
		BeanUtils.copyProperties(this, role);
		return role;
	}

}
