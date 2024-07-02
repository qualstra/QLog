package com.enoch.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RolePrivillegeDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ROLE_PRIV")
public class RolePrivillege implements Transform<RolePrivillegeDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "role_id")
	private Role role;
	@ManyToOne
	@JoinColumn(name = "priv_id")
	private Privillege privillege;
	boolean enabled;
	@Override
	public RolePrivillegeDTO transform() {
		RolePrivillegeDTO dto = new RolePrivillegeDTO();
		BeanUtils.copyProperties(this, dto);
		dto.setRole(role.transform());
		dto.setPrivillege(privillege.transform());
		return dto;
	}
}
