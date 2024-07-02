package com.enoch.model.checklist;

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

import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Helper;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHKLST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckList implements Transform<CheckListDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "chk_id", updatable = false, nullable = false)
	private UUID checkId;
	@Column(name = "version", updatable = false, nullable = false)
	private Integer version;
	@Column(name = "typ", updatable = true, nullable = true)
	private ChecklistType type = ChecklistType.SIMPLE;
	@Column(name = "NAME", updatable = true, length = 250)
	private String name;
	@Column(name = "DESCR", updatable = true, length = 250)
	private String shortDesc;
	@Column(name = "DET_DESC", updatable = true, length = 500)
	private String longDesc;
	@Column(name = "REMARK", updatable = true, nullable = false, length = 500)
	private String remarks;
	@Column(name = "data", updatable = true, nullable = false, length = 500)
	private String data;
	@ManyToOne
	@JoinColumn(name="COMPANYID")
	private Company company;
	
	// Published or Draft
	@Column(name = "STATE", updatable = true, nullable = false, length = 10)
	private State state;

	@Column(name = "ACTIVE", updatable = true, nullable = false)
	private Boolean active;

	
	@Column(name = "pre_processor", updatable = true, nullable = true)
	private String preProcessor;
	@Column(name = "post_processor", updatable = true, nullable = true)
	private String postProcessor;
	@Embedded
	private AuditTrail auditTrail;
	@Override
	public CheckListDTO transform() {
		CheckListDTO checklist = new CheckListDTO();
		BeanUtils.copyProperties(this, checklist);
		if(company != null)
			checklist.setCompany(company.transform());
		return checklist;
	}
}
