package com.enoch.dto.checklist.inst;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.inst.SectionInst;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionInstDTO implements Transform<SectionInst>{
	private Long id;
	private UUID UUID;
	private UUID masterUUID;
	private Integer slNo;
	private String name;
	private String data;
	private CompanyDTO company;
	private ShipDTO vessel;
	private Integer ansQuesCount;
	private Integer quesCount;

	private AuditTrail auditTrail;
	@Override
	public SectionInst transform() {
		SectionInst section = new SectionInst();
		BeanUtils.copyProperties(this, section);
		if(company != null)section.setCompany(company.transform());
		if(vessel != null)section.setVessel(vessel.transform());
		return section;
	}
}
