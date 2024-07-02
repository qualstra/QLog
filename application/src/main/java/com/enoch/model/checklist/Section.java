package com.enoch.model.checklist;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Helper;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SECTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Section implements Transform<SectionDTO> {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID UUID;
	@Column(name = "version", updatable = true, nullable = true)
	private Integer version;
	@Column(name = "sno")
	private Integer slNo;
	@Column(name = "NAME", updatable = true, nullable = false, length = 100)
	private String name;
	@Column(name = "DESCR", updatable = true, nullable = false, length = 250)
	private String shortDesc;
	@Column(name = "DETAIL", updatable = true, length = 500)
	private String longDescription;
	@Column(name = "DATA", updatable = true, length = 1000)
	private String data;
	@ManyToOne
	@JoinColumn(name = "COMPANYID" , nullable = false)
	private Company company;

	// Published or Draft
	@Column(name = "STATE", updatable = true, nullable = false, length = 10)
	private State state;
	@Column(name = "ACTIVE", updatable = true, nullable = false)
	private Boolean active;
	@Column(name = "pre_processor", updatable = true, nullable = true)
	private String preProcessor;
	@Column(name = "post_processor", updatable = true, nullable = true)
	private String postProcessor;
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public SectionDTO transform() {
		SectionDTO section = new SectionDTO();
		BeanUtils.copyProperties(this, section);
		if(company != null)section.setCompany(company.transform());
		return section;
	}

}
