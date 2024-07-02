package com.enoch.validator;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.service.impl.QuestionUtils;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;

@Component
public class OptionAnswerValidator extends AbstractQuestionValidator<QuestionInstDTO> {

	@Override
	public void validate(QuestionInstDTO question) {
		JSONObject data = JSONUtils.getJSObject(question.getData());
		JSONArray options = QuestionUtils.getOptions(question);

		Tuple<Integer, Integer> attMinMax = QuestionUtils.getAttachmentTupple(question.getData());

		if (question.getAnswer() == null)
			throw new ServiceException(String.format("Question[%s] is not answered", question.getUUID()));

		
		JSONObject jsAnswer = JSONUtils.getJSObject(question.getAnswer());
		String selection  = jsAnswer.optString(QuestionConstants.ANSWER);
		if(StringUtils.isBlank(selection)) {
			throw new ServiceException("Please select any one option");
		}
		String remark = jsAnswer.optString(QuestionConstants.REMARK); 
		Float weightage = jsAnswer.optFloat(QuestionConstants.WEIGHTAGE);
		JSONObject selOption = QuestionUtils.getSelectedOption(options,selection);
		Tuple<Integer, Integer> attMinMaxOpt = QuestionUtils.getAttachmentTupple(selOption);
		Integer minAttach = QuestionUtils.getMinAttachment(attMinMax,attMinMaxOpt);
		Integer maxAttach = QuestionUtils.getMaxAttachment(attMinMax,attMinMaxOpt);
		List<AttachmentDTO> attach = attService.getAll(question);
		Integer attachSize = attach.size() ;
		if(attachSize < minAttach) {
			throw new ServiceException(String.format("Need %s attachmentsfor the question found only %s attachents",minAttach,attachSize));
		}
		if(maxAttach != -1 && attachSize > maxAttach) {
			throw new ServiceException(String.format("Exceeded %s attachments for the question found %s attachents",maxAttach,attachSize));
		}
	}
	@Autowired
	AttachmentService attService; 

}
