package com.enoch.role.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "PRIV")
@Table(uniqueConstraints = @UniqueConstraint(columnNames = { "key_code", "name" }))
public class Privillege  implements Transform<PrivillegeDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "key_code", nullable = false)
	private String key; 
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "status", nullable = false)
	private boolean status;
	@Column(name = "deflt",  nullable = false)
	private boolean deflt;
	@Override
	public PrivillegeDTO transform() {
		PrivillegeDTO priv = new PrivillegeDTO();
		BeanUtils.copyProperties(this, priv);
		return priv;
	}
	
}
