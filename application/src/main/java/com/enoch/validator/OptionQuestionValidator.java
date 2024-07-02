package com.enoch.validator;


import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.service.impl.QuestionUtils;
import com.enoch.utils.JSONUtils;
@Component
public class OptionQuestionValidator extends AbstractQuestionValidator<QuestionDTO> {

	@Override
	public void validate(QuestionDTO question) {
		JSONObject data = JSONUtils.getJSObject(question.getData());
		validateAttachments(data);
		
		JSONArray options = QuestionUtils.getOptions(question);//JSONUtils.getArrayJS(data,QuestionConstants.OPTION);
		if(options == null || options.isEmpty()) throw new ServiceException("Option Question with no options");
		options.forEach(opt -> validateAttachments((JSONObject)opt));
	}

}
