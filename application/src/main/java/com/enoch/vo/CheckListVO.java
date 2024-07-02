package com.enoch.vo;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckListVO {
	private Long id;
	private UUID checkId;
	private Integer version;
	private ChecklistType type = ChecklistType.SIMPLE;
	private String name;
	private String shortDesc;
	private String longDesc;
	private String remarks;
	private State state;
	private Boolean active;
	private String preProcessor;
	private String postProcessor;
	private AuditTrail auditTrail;
	private String data;
	private List<CheckListSectionVO> masterCheckListSectionVOs = Collections.emptyList();
	private CompanyDTO company;
	
}
