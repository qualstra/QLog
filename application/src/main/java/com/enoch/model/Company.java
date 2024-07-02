package com.enoch.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "CMPNY")
@AllArgsConstructor @NoArgsConstructor
public class Company implements Transform<CompanyDTO>,Serializable {

	/**
	 * Serial version id
	 */
	private static final long serialVersionUID = -3254489568361789976L;

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "uuid", nullable = false, updatable = false)
	private UUID UUID;

	@Column(name = "ADDRESS1")
	private String address1;
	@Column(name = "ADDRESS2")
	private String address2;
	@Column(name = "CITY")
	private String city;
	@Column(name = "COUNTRY")
	private String country;
	@Column(name = "ISACTIVE")
	private Boolean isActive;
	@Column(name = "IT_EMAIL", nullable = false,updatable = false,unique = true)
	private String itAdminUser;
	@Column(name = "NCTYPES", updatable = true, length = 5000)
	private String nctype;
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public CompanyDTO transform() {
		CompanyDTO company = new CompanyDTO();
		BeanUtils.copyProperties(this, company);
		return company;
	}

}
