package com.enoch.model.checklist.inst;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Ship;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHKLST_INST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListInst implements Transform<CheckListInstDTO> {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "CHK_INST_ID", updatable = false, nullable = false)
	private UUID checkId;

	@Column(name = "mas_chk_uid", updatable = false, nullable = false)
	private UUID mChk;

	@Formula(value="(SELECT chk.name  FROM chklst chk where  chk.chk_id = mas_chk_uid)")
	@Basic(fetch=FetchType.EAGER)
	private String type;
	
	@Column(name = "NAME", updatable = false, nullable = true)
	private String name;
	
	@Column(name = "DESCR", updatable = true, length = 250)
	private String shortDesc;
	
	@Column(name = "DET_DESC", updatable = true, length = 500)
	private String longDesc;
	@Column(name = "data", updatable = true, nullable = false, length = 500)
	private String data;
	@Column(name = "pre_processor", updatable = true, nullable = true)
	private String preProcessor;
	@Column(name = "post_processor", updatable = true, nullable = true)
	private String postProcessor;

	@Column(name = "STATE", updatable = true, nullable = false, length = 10)
	private State state;
	@Column(name = "CHK_ST_DATE", nullable = false, updatable = false)
	private Date startDate = new Date();
	@Column(name = "CHK_END_DATE", nullable = false, updatable = false)
	private Date endDate = new Date();
	@Column(name = "ACT_END_DATE", nullable = false, updatable = false)
	private Date completedDate = new Date();

	@Column(name = "ASSOC_TYPE", updatable = true, nullable = false, length = 10)
	private Association assocType;
	@Column(name = "ASSOC_TO", updatable = true, nullable = true, length = 10)
	private String assocTo;
	@ManyToOne
	@JoinColumn(name = "COMPANYID")
	private Company company;
	@ManyToOne
	@JoinColumn(name = "VESSELID")
	private Ship vessel;

	@Formula(value="(SELECT count(*) FROM chklst_inst chk join chklst_sec_inst chksec on chk.id = chksec.chk_id join section_inst sec on sec.id = chksec.sec_id join sec_que_inst secQue on  secQue.sec_id  = chksec.sec_id join question_inst que on que.id = secQue.que_id  and que.ans_by is not null  where chk.chk_inst_id =chk_inst_id)")
	@Basic(fetch=FetchType.EAGER)
	private Integer ansQuesCount;
	@Formula(value="(SELECT count(*) FROM chklst_inst chk join chklst_sec_inst chksec on chk.id = chksec.chk_id join section_inst sec on sec.id = chksec.sec_id join sec_que_inst secQue on  secQue.sec_id  = chksec.sec_id join question_inst que on que.id = secQue.que_id where chk.chk_inst_id =chk_inst_id )")
	@Basic(fetch=FetchType.EAGER)
	private Integer quesCount;
		
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public CheckListInstDTO transform() {
		CheckListInstDTO checklist = new CheckListInstDTO();
		BeanUtils.copyProperties(this, checklist);
		if (company != null)
			checklist.setCompany(company.transform());
		if (vessel != null)
			checklist.setVessel(vessel.transform());
		return checklist;
	}
}
