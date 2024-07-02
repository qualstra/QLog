package com.enoch.dto.checklist.inst;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.model.checklist.CheckListSection;
import com.enoch.model.checklist.inst.CheckListInst;
import com.enoch.model.checklist.inst.CheckListSectionInst;
import com.enoch.model.checklist.inst.SectionInst;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistSectionInstDTO implements Transform<CheckListSectionInst> {
	private Long id;
	private CheckListInstDTO checklist;
	private SectionInstDTO section;
	private boolean enabled = true;
	@Override
	public CheckListSectionInst transform() {
		CheckListSectionInst checklistSection = new CheckListSectionInst();
		BeanUtils.copyProperties(this, checklistSection);
		checklistSection.setChecklist(checklist.transform());
		checklistSection.setSection(section.transform());
		return checklistSection;
	}

}
