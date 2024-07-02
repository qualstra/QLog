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

import com.enoch.dto.checklist.inst.SectionQuestionInstDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "SEC_QUE_INST")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectionQuestionInst implements Transform<SectionQuestionInstDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	private Long id;
	@ManyToOne
	@JoinColumn(name="SEC_ID")
	private SectionInst section;
	@ManyToOne
	@JoinColumn(name="QUE_ID")
	private QuestionInst question;
	
	@Column(name = "enabled")
	private boolean enabled;

	@Override
	public SectionQuestionInstDTO transform() {
		SectionQuestionInstDTO target = new SectionQuestionInstDTO();
		BeanUtils.copyProperties(this, target);
		if(section != null) target.setSection(section.transform());
		if(question != null) target.setQuestion(question.transform());
		return target;		
		
	}
	
}
