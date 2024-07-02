package com.enoch.validator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.model.checklist.QuestionType;

@Component
public class ValidatorFactory {
	
	@Autowired
	StringQuestionValidator stringQueValidator;
	@Autowired
	OptionQuestionValidator optQuestionValidator;
	@Autowired
	StringAnswerValidator stringAnswerValidator;
	@Autowired
	OptionAnswerValidator optionAnswerValidator;
	@Autowired
	DateAnswerValidator dateAnswerValidator;
	@Autowired
	DateQuestionValidator dateQuestionValidator;
	public <T> Validator<T> getQueValidator(QuestionType type) {
		Validator validator = null;
		switch (type) {
		case String:
			validator = stringQueValidator;
			break;
		case Option:
			validator = optQuestionValidator;
			break;
		case Date:
			validator = dateQuestionValidator;
			break;
		default:
			throw new ServiceException(String.format("Question validator for type[%s] is not implemented", type));
		}
		return validator;
	}

	public <T> Validator<T> getAnsValidator(QuestionType type) {
		Validator validator = null;
		switch (type) {
		case String:
			validator = stringAnswerValidator;
			break;
		case Option:
			validator = optionAnswerValidator ;
			break;
		case Date:
			validator = dateAnswerValidator;
			break;
		default:
			throw new ServiceException(String.format("Answer validator for type[%s] is not implemented", type));
		}
		return validator;
	}

	public Validator<List<QuestionDTO>> getCheckListQueValidator(CheckListDTO checklistDTO, SectionDTO section) {
		return new ChecklistQueValidator(checklistDTO, section);
	}

	public Validator<List<SectionDTO>> getCheckListSecValidator(CheckListDTO checklistDTO) {
		return new ChecklistSecValidator(checklistDTO);
	}

}
