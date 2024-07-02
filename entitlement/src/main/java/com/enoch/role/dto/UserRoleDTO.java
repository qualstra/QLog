package com.enoch.role.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.role.model.Role;
import com.enoch.role.model.UserRole;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDTO implements Transform<UserRole> {

	private Long id;
	String userId;
	RoleDTO role;
	boolean enabled;
	@Override
	public UserRole transform() {
		UserRole model = new UserRole();
		BeanUtils.copyProperties(this, model);
		model.setRole(role.transform());
		return model;
	}
	public UserRoleDTO(String userId2, RoleDTO role2) {
		this.userId = userId2;
		this.role = role2;
	}
}
