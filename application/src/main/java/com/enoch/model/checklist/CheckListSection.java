package com.enoch.model.checklist;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CHKLST_SEC")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckListSection implements Transform<ChecklistSectionDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@ManyToOne
	@JoinColumn(name="CHK_ID")
	private CheckList checklist;
	@ManyToOne
	@JoinColumn(name="SEC_ID")
	private Section section;
	@Column(name = "enabled")
	private boolean enabled;
	@Override
	public ChecklistSectionDTO transform() {
		ChecklistSectionDTO checklistSection = new ChecklistSectionDTO();
		BeanUtils.copyProperties(this, checklistSection);
		checklistSection.setChecklist(checklist.transform());
		checklistSection.setSection(section.transform());
		return checklistSection;
	}
}
