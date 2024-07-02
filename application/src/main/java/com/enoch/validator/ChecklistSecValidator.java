package com.enoch.validator;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;

import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;

public class ChecklistSecValidator implements Validator<List<SectionDTO>> {

	private CheckListDTO checklistDTO;

	public ChecklistSecValidator(CheckListDTO checklistDTO) {
		this.checklistDTO = checklistDTO;
	}

	@Override
	public void validate(List<SectionDTO> sections) {
		// checkListInst.get
		JSONObject chkData = JSONUtils.getJSObject(checklistDTO.getData());
		Object weightageObj = chkData.opt(QuestionConstants.WEIGHTAGE);
		boolean isPercent = (weightageObj instanceof String) && ("percent".equalsIgnoreCase(weightageObj.toString()));
		Double chkWt = StringUtils.getDoubleVal(weightageObj.toString(), isPercent ? 100D : 0);
		Double wt = sections.stream().map(secInst -> {
			JSONObject secOpt = JSONUtils.getJSObject(secInst.getData());
			Double ansWt = secOpt.optDouble(QuestionConstants.WEIGHTAGE, 0);
			return ansWt == -1 ? 0 : ansWt;
		}).collect(Collectors.summingDouble(Double::doubleValue));
		if( Math.abs(chkWt - wt ) > 0.001) {
			throw new ServiceException(String.format("Checklist[%s] sum of weightage of the sections[% ,.2f] is not equal to checklist weightage[% ,.2f] ", checklistDTO.getName(),wt,chkWt));
		}
	}

	public static void main(String[] args) {
		System.out.printf("% ,.2f\n% ,.2f\n", 1234567.123, -1234567.123);
		System.out.println(String.format("Checklist[%s] sum of weightage of the sections[% ,.2f] is not equal to checklist weightage[% ,.2f] ", "Vijay",1/3D,2/4D));
	}
}