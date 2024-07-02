package com.enoch.validator;


import static com.enoch.builder.QuestionConstants.MAX_ATTACH;
import static com.enoch.builder.QuestionConstants.MAX_LEN;
import static com.enoch.builder.QuestionConstants.MIN_ATTACH;
import static com.enoch.builder.QuestionConstants.MIN_LEN;
import static com.enoch.builder.QuestionConstants.OPTION;
import static com.enoch.builder.QuestionConstants.REMARK;
import static com.enoch.builder.QuestionConstants.REMARK_MAN;
import static com.enoch.utils.JSONUtils.*;

import org.hibernate.service.spi.ServiceException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.utils.StringUtils;
@Component
public class StringAnswerValidator implements Validator<QuestionInstDTO> {

	
	
	@Override
	public void validate(QuestionInstDTO question) {
		JSONObject data = new JSONObject(question.getData());
		Boolean remark = StringUtils.getBoolVal(data.optString(REMARK,"Y"), false);
		Boolean remarkMan = StringUtils.getBoolVal(data.optString(REMARK_MAN,"N"), false);
		JSONObject option = data.getJSONObject(OPTION);
		Integer minlen = getIntVal(option,MIN_LEN,null);
		Integer maxlen = getIntVal(option,MAX_LEN,null);
		
		JSONObject answer = new JSONObject(question.getAnswer());
		
		String remarkAns = (String) getStringVal(answer, REMARK);
		if (remark) {
			if (remarkMan) {
				if (remarkAns.length() == 0) {
					throw new ServiceException("Remark is mandatory");
				}
				if(minlen != null && minlen > 0) {
					if(remarkAns.trim().length() < minlen) {
						throw new ServiceException("Minimum %s charter required in remarks");
					}
				}
				if(maxlen != null && maxlen > 0) {
					if(remarkAns.trim().length() > maxlen) {
						throw new ServiceException(String.format("Exceeded %s charter for remarks(%s) ", maxlen,remarkAns));
					}
				}

			}
		} else {
			if (remarkAns != null && remarkAns.length() > 0)
				throw new ServiceException("Remark is not needed");
		}
			}

	private String getStringVal(JSONObject answer, String remark) {
		try {
			return answer.getString(remark).trim();
		} catch (Exception e) {
			return null;
		}
	}




}
