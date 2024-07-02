package com.enoch.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;

public class ChecklistQueValidator implements Validator<List<QuestionDTO>> {
	private SectionDTO section;

	public ChecklistQueValidator(CheckListDTO checklistDTO, SectionDTO section) {
		this.section = section;
	}

	@Override
	public void validate(List<QuestionDTO> questions) {
		JSONObject chkData = JSONUtils.getJSObject(section.getData());
		Object weightageObj = chkData.opt(QuestionConstants.WEIGHTAGE);
		if(weightageObj == null) return;
		boolean isPercent = (weightageObj instanceof String) && ("percent".equalsIgnoreCase(weightageObj.toString()));
		Double secWt = StringUtils.getDoubleVal(weightageObj.toString(), isPercent ? 100 : 0);
		if(secWt <0) return;
		Double quesWt = questions.stream().map(questionDTO -> {
			JSONObject answer = JSONUtils.getJSObject(questionDTO.getData());
			Double ansWt = answer.optDouble(QuestionConstants.WEIGHTAGE, 0);
			return ansWt == -1 ? 0 : ansWt;
		}).collect(Collectors.summingDouble(Double::doubleValue));
		if( Math.abs(secWt - quesWt ) > 0.001)  {
			throw new ServiceException(String.format("Section[%s] weightage[% ,.2f] is not matchng the sum of question weightage[% ,.2f]", section.getName(),secWt,quesWt));
		}
	}

}
