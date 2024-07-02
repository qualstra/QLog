package com.enoch.vo;

import static com.enoch.builder.QuestionConstants.OPTION;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.inst.Association;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionInstVO {
	private Long id;

	private UUID UUID;
	private UUID parentUUID; 
	
	private UUID masterUUID;
	private UUID masterParentUUID;
	
	private CompanyVO company;
	private ShipVO vessel;

	private Integer serNo;
	private Integer order;
	private String questionText;
	private String questionHelp;

	private QuestionType type;
	private String data;

	private String preProcessor;
	private String postProcessor;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy'T'HH:mm", timezone = "IST")
	private String answer;
	
	private Date answerDate;
	private String answerBy;
	private Association assocType;
	private String assocTo;
	
	private List<AttachmentVO> attachments;

	
	private List<QuestionInstVO> childQuestions = new ArrayList<QuestionInstVO>();
	
	public String getAnswerAttrib(String key) {
		if(answer != null) {
			String result = (new JSONObject(answer)).optString(key);
			if(type == QuestionType.Option && key.equalsIgnoreCase("answer")) {
				JSONObject o = new JSONObject(getData());
				Object orgvalue = o.get(OPTION);
				JSONArray optValues = null;
				if (orgvalue instanceof JSONArray) {
					optValues = (JSONArray) orgvalue;
				} else if (orgvalue instanceof JSONObject) {
					if (!((JSONObject) orgvalue).isNull("values")) {
						optValues = ((JSONObject) orgvalue).getJSONArray("values");
					}
				}	
				for(int index = optValues.length() -1 ; index >= 0; index--) {
					JSONObject selection = ((JSONObject)optValues.get(index));
					if(selection.optString("code").equalsIgnoreCase(result)) {
						result = selection.optString("name");
						break;
					}
				}
			}
			return result;
		} else {
			return "";
		}
	}
}
