package com.enoch.service;

import java.io.File;
import java.util.List;
import java.util.UUID;

import com.enoch.common.exception.ServiceException;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.SectionDTO;

public interface CheckListService {
	
	
	/** 
	 * List all the checklist available for the company
	 * the user has logged into
	 *  
	 */
	public List<CheckListDTO> getAllCheckLists(); 
	
	/**
	 * Tis met
	 * @param checklistDTO
	 */
	public CheckListDTO create(CheckListDTO checklistDTO);

	public List<CheckListDTO> search(CheckListSearchDTO chkVo);

	public List<CheckListDTO> findCheckListByUID(UUID checkId);
	
	public List<SectionDTO> loadAllSections(CheckListDTO dto) throws ServiceException ;
	// To Create a Section
	public ChecklistSectionDTO create(SectionDTO section, CheckListDTO checklist);

	public SectionDTO loadSection(Long parseLong);

	public List<CheckListDTO> buildChecklist(CompanyDTO comp, File file,boolean validateOnly);

}
