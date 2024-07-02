package com.enoch.service.impl;

import static com.enoch.builder.QuestionConstants.OPTION;
import static com.enoch.builder.QuestionConstants.REMARK;
import static com.enoch.builder.QuestionConstants.REMARK_MAN;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enoch.builder.QuestionConstants;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;

public class QuestionUtils {

	public static JSONArray getOptions(QuestionInstDTO question) {
		JSONObject o = new JSONObject(question.getData());
		Object orgvalue = o.get(OPTION);
		JSONArray optValues = null;
		if (orgvalue instanceof JSONArray) {
			optValues = (JSONArray) orgvalue;
		} else if (orgvalue instanceof JSONObject) {
			if (!((JSONObject) orgvalue).isNull("values")) {
				optValues = ((JSONObject) orgvalue).getJSONArray("values");
			}
		} else {
			optValues = new JSONArray();
		}
		return optValues;
	}

	public static JSONArray getOptions(QuestionDTO question) {
		JSONObject o = new JSONObject(question.getData());
		Object orgvalue = o.get(OPTION);
		JSONArray optValues = null;
		if (orgvalue instanceof JSONArray) {
			optValues = (JSONArray) orgvalue;
		} else if (orgvalue instanceof JSONObject) {
			if (!((JSONObject) orgvalue).isNull("values")) {
				optValues = ((JSONObject) orgvalue).getJSONArray("values");
			}
		} else {
			optValues = new JSONArray();
		}
		return optValues;
	}
	public static Tuple<Integer, Integer> getAttachmentTupple(String jData) {
		return getAttachmentTupple(new JSONObject(jData));
	}

	public static Tuple<Integer, Integer> getAttachmentTupple(JSONObject o) {
		Integer minAttachments = JSONUtils.getInteger(o, QuestionConstants.MIN_ATTACH, -1);
		Integer maxAttachments = JSONUtils.getInteger(o, QuestionConstants.MAX_ATTACH, -1);
		return new Tuple<Integer, Integer>(minAttachments, maxAttachments);
	}

	public static JSONObject getSelectedOption(JSONArray options, String selection) {
		JSONObject selectedOpt = null;
		for (int len = options.length() - 1; len >= 0; len--) {
			JSONObject object = (JSONObject) options.get(len);
			if (object.optString(QuestionConstants.CODE).equalsIgnoreCase(selection)) {
				selectedOpt = object;
				break;
			}
		}

		return selectedOpt;
	}

	public static Integer getMinAttachment(Tuple<Integer, Integer> attTupQue, 
			Tuple<Integer, Integer> attTupOpt) {
		if(attTupOpt.getValue1() < 0) {
			if(attTupQue.getValue1() < 0 ) {
				return 0;
			} else {
				return attTupQue.getValue1();
			}
		} else {
			return attTupOpt.getValue1();
		}
	}
	
	public static Integer getMaxAttachment(Tuple<Integer, Integer> attTupQue, 
			Tuple<Integer, Integer> attTupOpt) {
		if(attTupOpt.getValue2() < 0) {
			if(attTupQue.getValue2() < 0 ) {
				return 0;
			} else {
				return attTupQue.getValue2();
			}
		} else {
			return attTupOpt.getValue2();
		}
	}

	public static Boolean isHintEnabled(String data) {
		if(data == null) return false;
		JSONObject jData = new JSONObject(data);
		return StringUtils.getBoolVal(jData.optString(QuestionConstants.HINT_ANSWER,"N"), false);
	}

	public boolean checkForAutoSave(QuestionInstDTO pQue) {
		JSONObject data = new JSONObject(pQue.getData());
		Boolean remarkMan = StringUtils.getBoolVal(data.optString(REMARK_MAN,"N"), false);
		return !remarkMan;
	}
}