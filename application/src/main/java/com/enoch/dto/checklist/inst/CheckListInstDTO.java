package com.enoch.dto.checklist.inst;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.inst.Association;
import com.enoch.model.checklist.inst.CheckListInst;
import com.enoch.model.checklist.inst.State;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListInstDTO implements Transform<CheckListInst>{

	// Identifiers
	private Long id;
	private UUID checkId;

	// Association
	private CompanyDTO company;
	private ShipDTO vessel;
	private Association assocType;
	private String assocTo;
	
	//CHK DETAILS
	private UUID mChk;
	private String type;
	private String name;
	private String shortDesc;
	private String longDesc;
	private String data;
	private String preProcessor;
	private String postProcessor;
	
	// State INFO
	private State state;
	private Date startDate = new Date();
	private Date endDate = new Date();
	private Date completedDate = new Date();

	private Integer ansQuesCount;
	private Integer quesCount;

	private AuditTrail auditTrail;

	
	@Override
	public CheckListInst transform() {
		CheckListInst checklist = new CheckListInst();
		BeanUtils.copyProperties(this, checklist);
		 if(company != null) checklist.setCompany(company.transform());
		 if(vessel != null) checklist.setVessel(vessel.transform());
		 return checklist;
	}
}
