package com.enoch.events;

import java.util.List;

import org.springframework.context.ApplicationEvent;

import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.SectionQuestionInstDTO;

import lombok.Data;

@Data
public class CheckListEvent extends ApplicationEvent {

	private CheckListInstDTO checklist;
	private List<SectionQuestionInstDTO> secQuestions;
	
	public CheckListEvent(CheckListInstDTO source) {
		super(source);
		this.checklist = source;
	}
	
	public CheckListEvent(CheckListInstDTO source, List<SectionQuestionInstDTO> secQuestions) {
		super(source);
		this.checklist = source;
		this.secQuestions = secQuestions;
	}

}
