package com.enoch.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.role.dto.RoleDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "ROLE")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "qualifier", "name" }))
public class Role implements Transform<RoleDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@Column(name = "qualifier")
	private String qualifier;
	@Column(name = "name", nullable = false,length = 50)
	private String name;
	@Column(name = "code", nullable = false,length = 10)
	private String code;
	@Column(name = "descr", length = 250)
	private String description;

	@Column(name = "status", nullable = false)
	private Boolean status;
	@Column(name = "deflt", nullable = false)
	private Boolean deflt;

	@Override
	public RoleDTO transform() {
		RoleDTO dto = new RoleDTO();
		BeanUtils.copyProperties(this, dto);
		return dto;
	}
}
