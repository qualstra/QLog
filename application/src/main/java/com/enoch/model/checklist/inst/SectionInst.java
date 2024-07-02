package com.enoch.model.checklist.inst;

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

import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Ship;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SECTION_INST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionInst implements Transform<SectionInstDTO> {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID UUID;
	@Column(name = "mast_uuid", updatable = false, nullable = false)
	private UUID masterUUID;

	@Column(name = "sno")
	private Integer slNo;
	
	@Column(name = "NAME", updatable = true, nullable = false, length = 100)
	private String name;
	@Column(name = "data", updatable = true, nullable = false, length = 500)
	private String data;
	@ManyToOne
	@JoinColumn(name = "COMPANYID" , nullable = false)
	private Company company;
	@ManyToOne
	@JoinColumn(name = "VESSELID")
	private Ship vessel;

	@Formula(value="(SELECT count(*) FROM section_inst sec join sec_que_inst secQue on  secQue.sec_id  = sec.id join question_inst que on que.id = secQue.que_id  and que.ans_by is not null  where sec.uuid = sec.uuid)")
	@Basic(fetch=FetchType.EAGER)
	private Integer ansQuesCount;
	@Formula(value="(SELECT count(*) FROM section_inst sec join sec_que_inst secQue on secQue.sec_id = sec.id join question_inst que on que.id = secQue.que_id and que.ans_by is not null where sec.uuid = sec.uuid)")
	@Basic(fetch=FetchType.EAGER)
	private Integer quesCount;

	
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public SectionInstDTO transform() {
		SectionInstDTO section = new SectionInstDTO();
		BeanUtils.copyProperties(this, section);
		if(company != null)section.setCompany(company.transform());
		if(vessel != null)section.setVessel(vessel.transform());
		return section;
	}

}
