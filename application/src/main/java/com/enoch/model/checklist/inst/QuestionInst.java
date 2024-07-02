package com.enoch.model.checklist.inst;

import java.io.Serializable;
import java.util.Date;
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

import com.enoch.Attachable;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Ship;
import com.enoch.model.checklist.QuestionType;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QUESTION_INST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInst implements Serializable, Cloneable,Attachable, Transform<QuestionInstDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID UUID;
	@Column(name = "parent_id", updatable = false, nullable = true)
	private UUID parentUUID; 

	
	@Column(name = "mast_uuid", updatable = false, nullable = false)
	private UUID masterUUID;
	@Column(name = "mast_prnt_uuid", updatable = false, nullable = true)
	private UUID masterParentUUID;
	
	@ManyToOne
	@JoinColumn(name = "COMPANYID",nullable = false)
	private Company company;
	@ManyToOne
	@JoinColumn(name = "VESSELID",nullable = false)
	private Ship vessel;

	@Column(name = "SNO", updatable = true, nullable = true)
	private Integer serNo;
	@Column(name = "ORDR", updatable = true, nullable = true)
	private Integer order;

	@Column(name = "TXT", updatable = true, nullable = false, length = 500)
	private String questionText;
	@Column(name = "HLP", updatable = true, length = 500)
	private String questionHelp;

	@Column(name = "TYP", updatable = true, nullable = false, length = 25)
	private QuestionType type;
	// Stores the state of Question
	@Column(name = "DATA", updatable = true, nullable = false, length = 5000)
	private String data;

	@Column(name = "pre_processor", updatable = true, nullable = true)
	private String preProcessor;
	@Column(name = "post_processor", updatable = true, nullable = true)
	private String postProcessor;
	@Column(name = "ANS", updatable = true, nullable = true, length = 5000)
	private String answer;
	@Column(name = "ANS_ON", updatable = true, nullable = true)
	private Date answerDate;
	@Column(name = "ANS_BY", updatable = true, nullable = true)
	private String answerBy;
	@Column(name = "ASSOC_TYPE", updatable = true, nullable = false, length = 50)
	private Association assocType;
	@Column(name = "ASSOC_TO", updatable = true, nullable = true, length = 50)
	private String assocTo;
	@Column(name = "FLTR", updatable = true, nullable = true, length = 500)
	private String filter;
	
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public QuestionInstDTO transform() {
		QuestionInstDTO que = new QuestionInstDTO();
		BeanUtils.copyProperties(this, que);
		if(company != null)que.setCompany(company.transform());
		if(vessel != null)que.setVessel(vessel.transform());

		return que;
	}

	
}
