package com.enoch.validator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.config.ApplicationProperties;
import com.enoch.utils.JSONUtils;

public abstract class  AbstractQuestionValidator<T> implements Validator<T>{
	protected final Log logger = LogFactory.getLog(AbstractQuestionValidator.class);
	@Autowired
	protected ApplicationProperties prop;

	public void validateAttachments(JSONObject options) {
		Integer minAttachments = JSONUtils.getInteger(options, QuestionConstants.MIN_ATTACH, 0);
		Integer maxAttachments = JSONUtils.getInteger(options, QuestionConstants.MAX_ATTACH, 0);

		if (minAttachments < prop.getMin_len()) {
			String info = String.format("Minimum attachments for the question(%s) is wrong", minAttachments);
			logger.error(logger);
			throw new ServiceException(info);
		}
		if (maxAttachments > prop.getMax_len()) {
			String info = String.format("Max attachments for the question(%s) is more than the configured limit(%s)", maxAttachments,
					prop.getMax_attachments());
			logger.error(logger);
			throw new ServiceException(info);
		}

		if (maxAttachments < minAttachments) {
			String info = String.format("Max attachments for the question(%s) is lesser than the min attachments(%s)", maxAttachments ,minAttachments);
			logger.error(logger);
			throw new ServiceException(info);
		}

	}
	
}
