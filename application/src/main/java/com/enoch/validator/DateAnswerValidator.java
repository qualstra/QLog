package com.enoch.validator;

import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.config.ApplicationProperties;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.service.impl.QuestionUtils;
import com.enoch.utils.JSONUtils;

@Component
public class DateAnswerValidator extends AbstractQuestionValidator<QuestionInstDTO> {

	@Autowired
	ApplicationProperties props;
	
	@Override
	public void validate(QuestionInstDTO question) {
		Tuple<Integer, Integer> attMinMax = QuestionUtils.getAttachmentTupple(question.getData());

		if (question.getAnswer() == null)
			throw new ServiceException(String.format("Question[%s] is not answered", question.getUUID()));
		JSONObject jsAnswer = JSONUtils.getJSObject(question.getAnswer());
		String date = jsAnswer.optString(QuestionConstants.ANSWER);
		
		try{
			props.getDateDormat().parse(date);
		} catch (Exception e) {
			throw new ServiceException(String.format("Error parsing date %s : required format is %s",date,props.getDateDormat().toString()));
		}
		List<AttachmentDTO> attach = attService.getAll(question);
		Integer attachSize = attach.size();
		if (attachSize < attMinMax.getValue1()) {
			throw new ServiceException(String.format("Need %s attachmentsfor the question found only %s attachents",
					attMinMax.getValue1(), attachSize));
		}
		if (attMinMax.getValue2() != -1 && attachSize > attMinMax.getValue2()) {
			throw new ServiceException(String.format("Exceeded %s attachments for the question found %s attachents",
					attMinMax.getValue2(), attachSize));
		}
	}

	@Autowired
	AttachmentService attService;

}
