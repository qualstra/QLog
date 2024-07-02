package com.enoch.service.impl;

import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.model.Helper;
import com.enoch.model.checklist.inst.State;

public class ChkLstInstHelper {

	public static void populate(CheckListDTO checklistDTO, CheckListInstDTO inst) {
		
		inst.setMChk(checklistDTO.getCheckId());
		inst.setType(checklistDTO.getName());
		inst.setName(checklistDTO.getName());
		inst.setShortDesc(checklistDTO.getShortDesc());
		inst.setLongDesc(checklistDTO.getLongDesc());
		inst.setData(checklistDTO.getData());
		inst.setPreProcessor(checklistDTO.getPreProcessor());
		inst.setPostProcessor(checklistDTO.getPostProcessor());
		inst.setAuditTrail(Helper.getAuditTrail());
		inst.setState(State.NOTSTARTED);
	}

}
