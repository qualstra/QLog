package com.enoch.builder;

import static com.enoch.builder.QuestionConstants.*;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.model.Helper;
import com.enoch.model.checklist.QuestionType;

public class QuestionBuilder {
	private QuestionType type;
	private String queText;
	private Integer sNO;
	private UUID parentNo;
	private Long parentSerNo;
	JSONObject data = new JSONObject();
	JSONArray jsProcessors = new JSONArray();
	private String responsibility;
	private int order;

	public QuestionBuilder(QuestionType type) {
		this.type = type;
	}

	public QuestionBuilder(QuestionType type, String queText) {
		this.type = type;
		this.queText = queText;
	}

	public QuestionBuilder setType(QuestionType type) {
		this.type = type;
		return this;
	}

	public QuestionBuilder setAttrib(String attribName, Object values) {
		switch (attribName) {
		case QuestionHelper.HEADER_RESPONSIBILITY:
			this.responsibility = values.toString();
			break;
		case QuestionHelper.HEADER_ORDER:
			this.order = new Float(values.toString()).intValue();
			break;
		case QuestionHelper.HEADER_QUESTION_NC:
			jsProcessors.put("NC");
			data.put(NC, values);
			break;
		case QuestionHelper.HEADER_QUESTION_WT:
			jsProcessors.put("WT");
			data.put(WEIGHTAGE, values);
			break;
		case QuestionHelper.HEADER_SNO:
			this.sNO = new Float(values.toString()).intValue();
			break;
		case QuestionHelper.HEADER_QUESTION_PARENT:
			if (values != null) {
				try {
					this.parentNo = UUID.fromString(values.toString());
				} catch (Exception e) {
					this.parentSerNo = new Float(values.toString()).longValue();
				}
			}
			break;
		case QuestionHelper.HEADER_QUESTION_OPT:
			if(values instanceof String) {
				values = new JSONArray((String)values);
				((JSONArray)values).forEach(a -> validateOptions((JSONObject) a));
			} else {
				validateOptions((JSONObject) values);	
			}
				
			data.put(OPTION, values);
			
			break;
		case QuestionHelper.HEADER_QUESTION_ATT_MIN:
			data.put(MIN_ATTACH, values);
			break;
		case QuestionHelper.HEADER_QUESTION_ATT_MAX:
			data.put(MAX_ATTACH, values);
			break;
		case QuestionHelper.HEADER_QUESTION_REM:
			data.put(REMARK, values);
			break;
		case QuestionHelper.HEADER_QUESTION_REM_REQ:
			data.put(REMARK_MAN, values);
			break;
			
		default:
			throw new ServiceException(String.format("SNo : %s Unknown option : %s ",this.sNO,attribName));
		}
		return this;
	}

	private void validateOptions(JSONObject values) {
		values.keySet().forEach(a -> checkOptionHeader(a));
	}

	private void checkOptionHeader(String a) {
		switch (a) {
		case WEIGHTAGE:
		case NAME:
		case CODE:
		case ISNC:
		case DEFLT:		
		case MAX_ATTACH:
		case MIN_ATTACH:
		case REMARK:
		case REMARK_MAN:
		case MAX_LEN:
		case MIN_LEN:
		case OPT_VALUES:	
			break;
		default:
			throw new ServiceException(String.format("SNo : %s Unknown option : %s ",this.sNO,a));
		}
	}

	public void setQueText(String queText) {
		this.queText = queText;
	}

	public QuestionBuilder(String queText) {
		this(QuestionType.String, queText);
	}

	public QuestionDTO build() {
		QuestionDTO result = null;
		result = new QuestionDTO();
		result.setUuid(UUID.randomUUID());
		result.setType(type);
		result.setSerNo(this.sNO);
		result.setQuestionText(this.queText);
		result.setOrder(this.order);
		result.setData(this.data.toString());
		result.setResponsibility(this.responsibility);
		result.setAuditTrail(Helper.getAuditTrail());
		if (!jsProcessors.isEmpty()) {
			result.setPostProcessor(jsProcessors.toString());
		}
		result.setParentUUId(parentNo);
		result.setParentSerNo(parentSerNo);
		return result;
	}

	public static void main(String[] args) {
		QuestionDTO question = new QuestionBuilder("THis is a sample question").setType(QuestionType.String)
				.setAttrib("length", 10).build();
		System.out.println(question);
	}
}
