package com.enoch.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;
import com.enoch.xl.Parser;

public class QuestionHelper {
	public static final String HEADER_SNO = "SNO";
	public static final String HEADER_CHK_NAME = "Checklist";
	public static final String HEADER_CHK_OPT = "ChecklistOption";
	public static final String HEADER_SECTION_NAME = "Section";
	public static final String HEADER_SECTION_OPT = "SectionOption";
	public static final String HEADER_QUESTION = "Question";
	public static final String HEADER_RESPONSIBILITY = "Responsibility";
	public static final String HEADER_QUESTION_PARENT = "Parent";
	public static final String HEADER_QUESTION_TYP = "Type";
	public static final String HEADER_QUESTION_WT = "Weightage";
	public static final String HEADER_QUESTION_NC = "Is NC";
	public static final String HEADER_QUESTION_REM = "Remark";
	public static final String HEADER_QUESTION_REM_REQ = "RemarkReq";
	public static final String HEADER_QUESTION_ATT_MIN = "Min-Attachments";
	public static final String HEADER_QUESTION_ATT_MAX = "Max-Attachments";
	public static final String HEADER_QUESTION_OPT = "Option";
	public static final String HEADER_ORDER = "Order";
	public static final String HEADER_SECTION_SNO = "SecSNO";

	

	public static QuestionDTO buildQuestion(JSONObject jsObj) {

		QuestionBuilder builder = new QuestionBuilder(QuestionType.valueOf(jsObj.get(HEADER_QUESTION_TYP).toString()),
				jsObj.get(HEADER_QUESTION).toString());
		builder.setAttrib(HEADER_SNO, jsObj.get(HEADER_SNO));
		builder.setAttrib(HEADER_QUESTION_ATT_MIN, jsObj.get(HEADER_QUESTION_ATT_MIN));
		builder.setAttrib(HEADER_QUESTION_ATT_MAX, jsObj.get(HEADER_QUESTION_ATT_MAX));
		builder.setAttrib(HEADER_QUESTION_REM, jsObj.get(HEADER_QUESTION_REM));
		builder.setAttrib(HEADER_QUESTION_REM_REQ, jsObj.get(HEADER_QUESTION_REM_REQ));
		builder.setAttrib(HEADER_QUESTION_OPT, jsObj.get(HEADER_QUESTION_OPT));
		builder.setAttrib(HEADER_RESPONSIBILITY, jsObj.get(HEADER_RESPONSIBILITY));
		builder.setAttrib(HEADER_QUESTION_WT, jsObj.get(HEADER_QUESTION_WT));
		builder.setAttrib(HEADER_QUESTION_PARENT, jsObj.get(HEADER_QUESTION_PARENT));
		builder.setAttrib(HEADER_QUESTION_NC, jsObj.get(HEADER_QUESTION_NC));
		builder.setAttrib(HEADER_ORDER, jsObj.get(HEADER_ORDER));

		return builder.build();
	}

}
