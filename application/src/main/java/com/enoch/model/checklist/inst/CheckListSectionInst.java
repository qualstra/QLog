package com.enoch.model.checklist.inst;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.checklist.inst.ChecklistSectionInstDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHKLST_SEC_INST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListSectionInst implements Transform<ChecklistSectionInstDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@ManyToOne
	@JoinColumn(name="CHK_ID")
	private CheckListInst checklist;
	@ManyToOne
	@JoinColumn(name="SEC_ID")
	private SectionInst section;
	@Column(name = "enabled")
	private boolean enabled = true;
	@Override
	public ChecklistSectionInstDTO transform() {
		ChecklistSectionInstDTO checklistSection = new ChecklistSectionInstDTO();
		BeanUtils.copyProperties(this, checklistSection);
		checklistSection.setChecklist(checklist.transform());
		checklistSection.setSection(section.transform());
		return checklistSection;
	}
}
