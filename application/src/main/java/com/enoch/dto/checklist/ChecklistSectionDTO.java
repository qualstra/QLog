package com.enoch.dto.checklist;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.model.checklist.CheckList;
import com.enoch.model.checklist.CheckListSection;
import com.enoch.model.checklist.Section;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChecklistSectionDTO implements Transform<CheckListSection> {
	private Long id;
	private CheckListDTO checklist;
	private SectionDTO section;
	private boolean enabled;

	@Override
	public CheckListSection transform() {
		CheckListSection checklistSection = new CheckListSection();
		BeanUtils.copyProperties(this, checklistSection);
		checklistSection.setChecklist(checklist.transform());
		checklistSection.setSection(section.transform());
		return checklistSection;
	}

}
