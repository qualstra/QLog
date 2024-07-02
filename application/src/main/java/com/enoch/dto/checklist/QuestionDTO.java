package com.enoch.dto.checklist;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Helper;
import com.enoch.model.checklist.Question;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionDTO implements Serializable,Cloneable,Transform<Question>{

	private Long id;
	private UUID uuid;
	private Integer queId;
	private Integer version = 1;
	private UUID parentUUId;
	private CompanyDTO company;
	private String questionText;
	private String questionHelp;
	private String responsibility;
	private QuestionType type;
	private Integer serNo;
	private Integer order;
	private String data;
	private State state = State.DRAFT ;
	private Boolean active = true;
	private String preProcessor;
	private String postProcessor;
	private AuditTrail auditTrail;
	
	//THis will be used during Upload for parent ID
	private Long parentSerNo;
	
	private List<QuestionDTO> childQuestions = new ArrayList<QuestionDTO>();
	
	@Override
	public Question transform() {
		Question que = new Question();
		BeanUtils.copyProperties(this, que);
		if(company != null)que.setCompany(company.transform());
		que.setFilter(this.responsibility);
		return que;
	}

	public void addChildQuestion(QuestionDTO childQue) {
		this.childQuestions.add(childQue);
	}
}
