package com.enoch.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.role.dto.UserRoleDTO;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
@Entity(name = "usr_role")
public class UserRole implements Transform<UserRoleDTO>{

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	
	@Column(name="usr_id")
	String userId;
	@ManyToOne
	@JoinColumn(name = "role_id")
	Role role;
	@Column(name="enabled")
	boolean enabled;
	@Override
	public UserRoleDTO transform() {
		UserRoleDTO dto = new UserRoleDTO();
		BeanUtils.copyProperties(this, dto);
		dto.setRole(role.transform());
		return dto;
	}
}
