package com.enoch.role.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.role.model.UserPrivillege;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class UserPrivillegeDTO implements Transform<UserPrivillege>{

	public UserPrivillegeDTO(String userId2, PrivillegeDTO priv) {
		this.userId = userId2;
		this.privillege = priv;
		this.enabled = true;
	}
	private Long id;
	String userId;
	PrivillegeDTO privillege;
	boolean enabled;
	@Override
	public UserPrivillege transform() {
		UserPrivillege model = new UserPrivillege();
		BeanUtils.copyProperties(this, model);
		model.setPrivillege(privillege.transform());
		return model;

	}
}
