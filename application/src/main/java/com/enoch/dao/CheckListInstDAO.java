package com.enoch.dao;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.ChecklistSectionInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.checklist.inst.SectionQuestionInstDTO;
import com.enoch.model.checklist.inst.State;

public interface CheckListInstDAO {

	SectionQuestionInstDTO associate(SectionInstDTO sectDTO, QuestionInstDTO qInst);

	ChecklistSectionInstDTO associate(CheckListInstDTO inst, SectionInstDTO sectDTO);

	QuestionInstDTO create(QuestionInstDTO inst);

	SectionInstDTO create(SectionInstDTO inst);

	CheckListInstDTO create(CheckListInstDTO inst);

	Optional<QuestionInstDTO> loadQuestion(UUID uuid);
	
	Integer getUnAsweredCount(UUID parentUUID);
	List<QuestionInstDTO> loadQuestions(SectionInstDTO secInst);

	List<SectionInstDTO> loadSections(CheckListInstDTO chkInstDB);

	Optional<CheckListInstDTO> load(UUID checkId);

	QuestionInstDTO answer(QuestionInstDTO question);

	List<CheckListInstDTO> loadAll();

	Optional<CheckListInstDTO> loadLatestChkForMasterUUID(UUID masterChkUUID, ShipDTO shipDTO);

	List<CheckListInstDTO> getPreviousChecklist(CheckListInstDTO checkInst, int number);

	void updateCheckListStatusAsOf(Date date, State state);

	

}
