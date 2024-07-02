package com.enoch.dto;

import java.io.Serializable;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.Attachable;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor @NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CompanyDTO implements Transform<Company>, Serializable ,Attachable{

	private static final long serialVersionUID = -3254489568361789976L;
	@EqualsAndHashCode.Include
	private Long id;
	@EqualsAndHashCode.Include
	private String name;
	private UUID UUID;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private Boolean isActive;
	private String itAdminUser;
	private String nctype;
	private AuditTrail auditTrail;
	@Override
	public Company transform() {
		Company company = new Company();
		BeanUtils.copyProperties(this, company);
		return company;
	}
	public CompanyDTO(Long tenantid) {
		this.id = tenantid;
	}


}
