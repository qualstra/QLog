package com.enoch.model.checklist;

import java.io.Serializable;
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

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "QUESTION")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Question implements Serializable, Cloneable, Transform<QuestionDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "qid", updatable = false, nullable = false)
	private Integer queId;

	@Column(name = "version", updatable = false, nullable = false)
	private Integer version = 1;
	
	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID uuid;
	
	@Column(name = "parent_id", updatable = false, nullable = true)
	private UUID parentUUId; 
	@ManyToOne
	@JoinColumn(name = "COMPANYID",nullable = false)
	private Company company;

	@Column(name = "TXT", updatable = true, nullable = false, length = 500)
	private String questionText;
	@Column(name = "HLP", updatable = true, length = 500)
	private String questionHelp;
	@Column(name = "FILTR", updatable = true, length = 500)
	private String filter;
	@Column(name = "TYP", updatable = true, nullable = false, length = 25)
	private QuestionType type;
	@Column(name = "SNO", updatable = true, nullable = true)
	private Integer serNo;
	@Column(name = "ORDR", updatable = true, nullable = true)
	private Integer order;
	// Stores the state of Question
	@Column(name = "DATA", updatable = true, nullable = false, length = 5000)
	private String data;

	// Published or Draft
	@Column(name = "STATE", updatable = true, nullable = false, length = 10)
	private State state = State.DRAFT;

	@Column(name = "ACTIVE", updatable = true, nullable = false)
	private Boolean active = true;

	@Column(name = "pre_processor", updatable = true, nullable = true)
	private String preProcessor;
	@Column(name = "post_processor", updatable = true, nullable = true)
	private String postProcessor;
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public QuestionDTO transform() {
		QuestionDTO que = new QuestionDTO();
		BeanUtils.copyProperties(this, que);
		if(company != null) que.setCompany(company.transform());
		que.setResponsibility(this.filter);
		return que;
	}

}
