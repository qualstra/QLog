package com.enoch.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.role.dto.RolePrivillegeDTO;
import com.enoch.role.dto.UserPrivillegeDTO;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
@Entity(name = "usr_priv")
public class UserPrivillege implements Transform<UserPrivillegeDTO>{

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@Column(name="usr_id")
	String userId;
	@ManyToOne
	@JoinColumn(name = "priv_id")
	Privillege privillege;
	@Column(name="enabled")
	boolean enabled;
	@Override
	public UserPrivillegeDTO transform() {
		UserPrivillegeDTO dto = new UserPrivillegeDTO();
		BeanUtils.copyProperties(this, dto);
		dto.setPrivillege(privillege.transform());
		return dto;
	}
}
