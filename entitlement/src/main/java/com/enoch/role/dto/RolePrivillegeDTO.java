package com.enoch.role.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.role.model.RolePrivillege;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RolePrivillegeDTO implements Transform<RolePrivillege>{
	private Long id;
	private RoleDTO role;
	private PrivillegeDTO privillege;
	boolean enabled;

	@Override
	public RolePrivillege transform() {
		RolePrivillege res = new RolePrivillege();
		BeanUtils.copyProperties(this, res);
		res.setRole(role.transform());
		res.setPrivillege(privillege.transform());
		return res;
	}
	
	public RolePrivillegeDTO(RoleDTO role2, PrivillegeDTO priv) {
		this.role = role2;
		this.privillege = priv;
	}
}
