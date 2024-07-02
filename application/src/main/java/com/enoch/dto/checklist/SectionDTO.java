package com.enoch.dto.checklist;

import java.util.UUID;

import javax.persistence.Column;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.Section;
import com.enoch.model.checklist.State;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionDTO implements Transform<Section>{
	private Long id;
	private Integer version;
	private UUID UUID;
	private Integer slNo;
	private String name;
	private String shortDesc;
	private String longDescription;
	// THis tells more on how this section has to be processed
	// This is json formatted string
	private String data;
	private CompanyDTO company;
	private State state;
	private Boolean active;
	private String preProcessor;
	private String postProcessor;
	private AuditTrail auditTrail;
	@Override
	public Section transform() {
		Section section = new Section();
		BeanUtils.copyProperties(this, section);
		if(company != null)section.setCompany(company.transform());
		return section;
	}
}
