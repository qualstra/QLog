package com.enoch.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;

public interface CheckListDao {

	Optional<CheckListDTO> loadCheckList(Long id);

	CheckListDTO createCheckList(CheckListDTO dto) throws DataException;

	List<SectionDTO> loadAllSections(CheckListDTO dto) throws DataException;

	SectionDTO createSection(SectionDTO secDTO) throws DataException;

	CheckListDTO saveCheckList(CheckListDTO dtoTmp) throws DataException;

	CheckListDTO makeCheckListEditable(CheckListDTO dto) throws DataException;

	List<CheckListDTO> loadByCheckListId(UUID checkId);

	SectionDTO saveSection(SectionDTO dto) throws DataException;

	List<ChecklistSectionDTO> addSecToCL(CheckListDTO temp, SectionDTO... dto) throws DataException;

	void remSecToCL(CheckListDTO temp, SectionDTO... dto) throws DataException;

	List<CheckListDTO> getAllChecklist();

	SectionDTO loadSection(Long parseLong);

	List<CheckListDTO> search(CheckListSearchDTO chkVo);

	//

}
