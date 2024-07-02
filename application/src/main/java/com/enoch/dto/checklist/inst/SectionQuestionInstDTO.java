package com.enoch.dto.checklist.inst;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.BeanUtils;

import com.enoch.model.checklist.inst.QuestionInst;
import com.enoch.model.checklist.inst.SectionInst;
import com.enoch.model.checklist.inst.SectionQuestionInst;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionQuestionInstDTO implements Transform<SectionQuestionInst>{

	private Long id;
	private SectionInstDTO section;
	private QuestionInstDTO question;
	private boolean enabled;

	@Override
	public SectionQuestionInst transform() {
		SectionQuestionInst target = new SectionQuestionInst();
		BeanUtils.copyProperties(this, target);
		if(section != null) target.setSection(section.transform());
		if(question != null) target.setQuestion(question.transform());
		return target;
	}
}

