package com.enoch.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;

@Component
public class QuestionValidatonHelper {

	@Autowired
	private  ValidatorFactory factory;
	public void validateAnswer(QuestionInstDTO question) {
		Validator<QuestionInstDTO> validator = factory.getAnsValidator(question.getType());
		validator.validate(question);
	}

	public void validateQuestion(QuestionDTO question) {
		Validator<QuestionDTO> validator = factory.getQueValidator(question.getType());
		if(question.getSerNo() == 149) {
			System.out.println("TODO");
		}
		validator.validate(question);
	}

}
