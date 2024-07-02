package com.enoch.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.config.ApplicationProperties;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.utils.JSONUtils;

@Component
public class StringQuestionValidator extends AbstractQuestionValidator<QuestionDTO> {

	protected final Log logger = LogFactory.getLog(StringQuestionValidator.class);

	@Autowired
	ApplicationProperties prop;

	@Override
	public void validate(QuestionDTO question) {
		
		JSONObject data = JSONUtils.getJSObject(question.getData());
		JSONObject options = data.getJSONObject(QuestionConstants.OPTION);
		validateAttachments(data);
		Integer minLen = JSONUtils.getInteger(options, QuestionConstants.MIN_LEN, prop.getMin_len());
		Integer maxLen = JSONUtils.getInteger(options, QuestionConstants.MAX_LEN, prop.getMax_len());

		if (minLen < prop.getMin_len()) {
			String info = String.format("Min Length in the question(%s) is less than the configured limit(%s)", minLen,
					prop.getMin_len());
			logger.error(logger);
			throw new ServiceException(info);
		}
		if (maxLen > prop.getMax_len()) {
			String info = String.format("Max Length in the question(%s) is more than the configured limit(%s)", maxLen,
					prop.getMax_len());
			logger.error(logger);
			throw new ServiceException(info);
		}

		if (maxLen < minLen) {
			String info = String.format("Max Length in the question(%s) is lesser than the min length(%s)", maxLen ,minLen);
			logger.error(logger);
			throw new ServiceException(info);
		}
		
	}

}


