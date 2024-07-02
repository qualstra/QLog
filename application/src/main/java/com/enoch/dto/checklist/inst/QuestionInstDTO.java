package com.enoch.dto.checklist.inst;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.Attachable;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.inst.Association;
import com.enoch.model.checklist.inst.QuestionInst;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInstDTO implements Serializable,Cloneable,Attachable,Transform<QuestionInst>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8951840582549591379L;
	private Long id;
	private UUID UUID;
	private UUID parentUUID; 
	
	private UUID masterUUID;
	private UUID masterParentUUID;
	
	private CompanyDTO company;
	private ShipDTO vessel;

	private Integer serNo;
	private Integer order;

	private String questionText;
	private String questionHelp;

	private QuestionType type;
	private String data;

	private String preProcessor;
	private String postProcessor;

	private String answer;
	private Date answerDate;
	private String answerBy;
	private Association assocType;
	private String assocTo;
	// Holds the filter
	private String filter;

	private AuditTrail auditTrail;
	
	private List<QuestionInstDTO> childQuestions = new ArrayList<QuestionInstDTO>();
	
	@Override
	public QuestionInst transform() {
		QuestionInst que = new QuestionInst();
		BeanUtils.copyProperties(this, que);
		if(company != null)que.setCompany(company.transform());
		if(vessel != null)que.setVessel(vessel.transform());
 
		return que;
	}

	public void addChildQuestion(QuestionInstDTO childQue) {
		this.childQuestions.add(childQue);
	}
}
