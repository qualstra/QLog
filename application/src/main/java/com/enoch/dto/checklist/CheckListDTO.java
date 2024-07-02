package com.enoch.dto.checklist;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.CheckList;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListDTO implements Transform<CheckList>{
	private Long id;
	private UUID checkId;
	private Integer version;
	private ChecklistType type = ChecklistType.SIMPLE;
	private String name;
	private String shortDesc;
	private String longDesc;
	private String remarks;
	private String data;
	private CompanyDTO company;
	private State state;
	private Boolean active;
	private String preProcessor;
	private String postProcessor;
	private AuditTrail auditTrail ;
	@Override

	public CheckList transform() {
		CheckList checklist = new CheckList();
		BeanUtils.copyProperties(this, checklist);
		if(company != null)
			checklist.setCompany(company.transform());
		return checklist;
	}
}
