package com.enoch.service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.json.JSONObject;

import com.enoch.ApplicationContext;
import com.enoch.dto.UserDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;

import net.minidev.json.JSONValue;

public interface CheckListInstService {
	
	/**
	 * Creates an instance for the checklist
	 * @param checklistDTO
	 */
	public CheckListInstDTO clone(CheckListInstCreateDTO checklistDTO);

	public Optional<CheckListInstDTO> loadCheckListInst(UUID chekInstId);

	public List<SectionInstDTO> loadSections(CheckListInstDTO chkInstDB);

	// By default it will be filtered pass the filter if you need any change
	default public Collection<QuestionInstDTO> loadQuestions(SectionInstDTO secInst){
		return loadQuestions(secInst,(QuestionInstDTO que) -> {
			UserDTO user = ApplicationContext.getContext().getUser();
			String filter = que.getFilter();
			if(filter == null) {
				return true;
			}
			JSONObject filterJson = new JSONObject(filter);
			try {
				
				return filterJson.optJSONArray("rank").toList().stream().filter(som -> som.toString().equalsIgnoreCase(user.getRank().getCode())).findAny().isPresent();
			} catch (Exception e) {
				
			}
			return filterJson.optString("rank","").equalsIgnoreCase(user.getRank().getCode());
		});
	}
	public Collection<QuestionInstDTO> loadQuestions(SectionInstDTO secInst,Function<QuestionInstDTO,Boolean> allow);

	

	public List<CheckListInstDTO> fetchAllChkIns();

	public QuestionInstDTO answer(UUID uuid, String answerStr, boolean b);

	default public QuestionInstDTO answer(UUID uuid, String answerStr) {
		return answer(uuid, answerStr, true);
	}

	public SectionInstDTO save(SectionInstDTO sec);

	public CheckListInstDTO save(CheckListInstDTO checkListInst);
	
	public List<CheckListInstDTO> getPreviousChecklist(CheckListInstDTO checkInst, int number); 
	
	default public JSONObject getSummaryData(UUID checkInstId) {
		return getSummaryData(checkInstId, false);
	}
	
	public JSONObject getSummaryData(UUID checkInstId, boolean includeAll);

	public JSONObject saveSummaryData(UUID uuid, JSONObject summaryData);

	public void process(UUID uuid, String processor);
}
