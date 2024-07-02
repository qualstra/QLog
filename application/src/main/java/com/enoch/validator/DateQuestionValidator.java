package com.enoch.validator;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.enoch.builder.QuestionConstants;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.utils.JSONUtils;
@Component
public class DateQuestionValidator extends AbstractQuestionValidator<QuestionDTO> {

	@Override
	public void validate(QuestionDTO question) {
		JSONObject data = JSONUtils.getJSObject(question.getData());
		validateAttachments(data);
		
		JSONArray options = JSONUtils.getArrayJS(data,QuestionConstants.OPTION);
		options.forEach(opt -> validateAttachments((JSONObject)opt));
	}

}
