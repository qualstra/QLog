package com.enoch.vo;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.inst.Association;
import com.enoch.model.checklist.inst.State;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListInstVO {
	// Identifiers
	private Long id;
	private UUID checkId;

	// Association
	private CompanyVO company;
	private ShipVO vessel;
	private Association assocType;
	private String assocTo;
	
	//CHK DETAILS
	private UUID mChk;
	private String type;
	private String name;
	private String shortDesc;
	private String longDesc;
	private String remarks;
	private String preProcessor;
	private String postProcessor;
	
	private String data;
	
	// State INFO
	private State state;
	private Date startDate = new Date();
	private Date endDate = new Date();
	private Date completedDate = new Date();


	private AuditTrail auditTrail;

	private List<SectionInstVO> sections;
	private Integer ansQuesCount;
	private Integer quesCount;
	public void setSectionInstVOs(List<SectionInstVO> map) {
		this.sections  = map;
	}

}
