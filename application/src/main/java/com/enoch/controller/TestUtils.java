package com.enoch.controller;

import static com.enoch.builder.QuestionConstants.ANSWER;
import static com.enoch.builder.QuestionConstants.CODE;
import static com.enoch.builder.QuestionConstants.OPTION;
import static com.enoch.builder.QuestionConstants.REMARK;
import static com.enoch.builder.QuestionConstants.WEIGHTAGE;

import java.util.Collection;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.model.Helper;
import com.enoch.service.CheckListInstService;
import com.enoch.service.QuestionService;
import com.enoch.service.impl.QuestionUtils;
import com.enoch.utils.StringUtils;

@Component
public class TestUtils {
	@Autowired
	AttachmentService aServ;

	@Autowired
	private CheckListInstService checklistInstServ;
	@Autowired
	QuestionService qService;

	public void answerCheckList(CheckListInstDTO checklistInst) {
		List<SectionInstDTO> res = checklistInstServ.loadSections(checklistInst);
		res.stream().map(checklistInstServ::loadQuestions).flatMap(Collection::stream).forEach(this::answerQuestions);
	}

	public void answerQuestions(QuestionInstDTO question) {
		JSONObject answer = new JSONObject();
		switch (question.getType()) {
		case Option:
			JSONObject o = new JSONObject(question.getData());
			Object orgvalue = o.get(OPTION);
			JSONArray optValues = null;
			if (orgvalue instanceof JSONArray) {
				optValues = (JSONArray) orgvalue;
			} else if (orgvalue instanceof JSONObject) {
				if (!((JSONObject) orgvalue).isNull("values")) {
					optValues = ((JSONObject) orgvalue).getJSONArray("values");
				}
			}
			JSONObject value = (JSONObject) optValues.get(0);
			Tuple<Integer, Integer> qAttMinMax = QuestionUtils.getAttachmentTupple(o);
			Tuple<Integer, Integer> oAttMinMax = QuestionUtils.getAttachmentTupple(value);
			Integer minA = QuestionUtils.getMinAttachment(qAttMinMax, oAttMinMax);
			Integer maxA = QuestionUtils.getMaxAttachment(qAttMinMax, oAttMinMax);
			for (int i = 0; i < minA; i++) {
				aServ.create(new AttachmentDTO(null, question.getUUID(), "PIC", "location", "location","JPG","JPG", "Sample", "", true,
						Helper.getAuditTrail()));
			}

			answer.put(REMARK, "I like this");
			answer.put(WEIGHTAGE, "25");
			answer.put(ANSWER, value.get(CODE));
			break;
		case String:
			JSONObject data = new JSONObject(question.getData());
			if (StringUtils.getBoolVal(data.optString(REMARK, "Y"), true)) {
				answer.put(REMARK, "I like this");
			}
			Tuple<Integer, Integer> qAttMinMaxS = QuestionUtils.getAttachmentTupple(data);
			for (int i = 0; i < qAttMinMaxS.getValue1(); i++) {
				aServ.create(new AttachmentDTO(null, question.getUUID(), "PIC", "location", "location","JPG", "JPG", "Sample", "", true,
						Helper.getAuditTrail()));
			}

			answer.put(WEIGHTAGE, "30");
			break;
		case Date:
			JSONObject dataDate = new JSONObject(question.getData());
			if (StringUtils.getBoolVal(dataDate.optString(REMARK, "Y"), true)) {
				answer.put(REMARK, "I like this");
			}
			Tuple<Integer, Integer> qAttMinMaxSDate = QuestionUtils.getAttachmentTupple(dataDate);
			for (int i = 0; i < qAttMinMaxSDate.getValue1(); i++) {
				aServ.create(new AttachmentDTO(null, question.getUUID(), "PIC", "location", "location","JPG", "JPG", "Sample", "", true,
						Helper.getAuditTrail()));
			}
			answer.put(ANSWER, "12-12-2020");
			answer.put(WEIGHTAGE, "30");
			break;

		}
		QuestionInstDTO dto = checklistInstServ.answer(question.getUUID(), answer.toString());
		JSONObject o = new JSONObject(dto.getAnswer());
		String remark = o.optString(REMARK);
		String weigtage = (String) o.optString(WEIGHTAGE);

		switch (dto.getType()) {
		case Option:
			String ans = (String) o.get(ANSWER);
			assert remark.contentEquals("I like this") : "Remark is different";
			assert weigtage.contentEquals("25") : "weightage is different";
			assert ans.contentEquals("Y") : "Answer is different";
			break;
		case String:

			JSONObject data = new JSONObject(question.getData());
			if (!StringUtils.getBoolVal(data.optString(REMARK, "Y"), true)) {
				assert remark.contentEquals("") : "Remark is not empty";
			} else {
				assert remark.contentEquals("I like this") : "Remark is different";
			}

			assert weigtage.contentEquals("30") : "weightage is different";
			break;
		}
	}

	public int countQuestionInsts(Collection<QuestionInstDTO> questions) {
		return questions.stream().mapToInt(que -> {
			return que.getChildQuestions().size() + 1;
		}).sum();
	}

	public int countQuestions(Collection<QuestionDTO> questions) {
		return questions.stream().mapToInt(que -> {
			return que.getChildQuestions().size() + 1;
		}).sum();
	}

	public int countQuestions(final SectionInstDTO secInst, List<SectionDTO> res) {
		return res.stream().filter(sec -> sec.getName().equalsIgnoreCase(secInst.getName())).findFirst()
				.map(sec -> countQuestions(qService.loadAllQuestios(sec))).orElse(new Integer(0));
	}

}
