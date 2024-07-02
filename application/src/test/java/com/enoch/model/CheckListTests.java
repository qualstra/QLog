package com.enoch.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dao.CheckListDao;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;
import com.enoch.exception.EntityException;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CheckListTests {

	@Autowired
	private TestEntityManager entityManager;
	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY",
			true, "it@enoch.com","",Helper.getAuditTrail());
	final CheckListDTO dto = new CheckListDTO(null, null, null, ChecklistType.SIMPLE, "Test", "Short Desc", "Long Desc",
			"remarks", "{}" ,comp.transform(), State.DRAFT, true, "None", "None", Helper.getAuditTrail());
	final SectionDTO secDTO = new SectionDTO(null,null,UUID.randomUUID(), null, "SecName","DESC",
			"LDESC","{}",comp.transform(),State.DRAFT,true,"NOPRE","NOPOST",
			Helper.getAuditTrail());
	
	
	
	@Autowired
	private CheckListDao checklistDAO;

	// write test cases here
	@Test
	public void testCreateChecklist() throws DataException {
		CheckListDTO dtoTmp = checklistDAO.createCheckList(dto);
		assert dtoTmp.getId() != null : "Not able to save";
	}

	@Test
	public void testSaveDraftChecklist() throws DataException {
		CheckListDTO dtoTmp = checklistDAO.createCheckList(dto);
		dtoTmp.setLongDesc("Some Change");
		dtoTmp = checklistDAO.saveCheckList(dtoTmp);
		assert dtoTmp.getId() != null : "Not able to save";
	}

	@Test
	public void testSavePublishedChecklist() throws DataException {
		CheckListDTO copy = dto.transform().transform();
		copy.setState(State.PUBLISHED);
		CheckListDTO dtoTmp = checklistDAO.createCheckList(copy);
		dtoTmp.setLongDesc("Some Change");
		assertThrows(EntityException.class, () -> {
			checklistDAO.saveCheckList(dtoTmp);
		});
	}

	@Test
	public void testLoadChecklist() throws DataException {
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		assert checklistDAO.loadCheckList(temp.getId()).isPresent() : "Not able to save";

	}
	@Test
	public void testMakeChecklistEditable() throws DataException {
		CheckListDTO copy = dto.transform().transform();
		copy.setState(State.PUBLISHED);
		CheckListDTO dtoTmp = checklistDAO.createCheckList(dto);
		dtoTmp = checklistDAO.makeCheckListEditable(dtoTmp);
		dtoTmp.setLongDesc("Second Version");
		
		dtoTmp = checklistDAO.saveCheckList(dtoTmp);
		assert dtoTmp.getState() == State.DRAFT: "Version not incremented";
	}

	@Test
	public void testMultiChecklist() throws DataException {
		CheckListDTO copy = dto.transform().transform();
		copy.setState(State.PUBLISHED);
		CheckListDTO dtoTmp = checklistDAO.createCheckList(copy);
		dtoTmp = checklistDAO.makeCheckListEditable(dtoTmp);
		dtoTmp.setLongDesc("Second Version");
		dtoTmp = checklistDAO.saveCheckList(dtoTmp);
		assert dtoTmp.getVersion() > 1 : "Version not incremented";

		List<CheckListDTO> checklists = checklistDAO.loadByCheckListId(dtoTmp.getCheckId());
		assert checklists.size() == 2 : "Two version not present";
	}
	

	@Test
	public void testSectionsCreateNoCL() throws DataException {
		SectionDTO dto = secDTO.transform().transform();
		dto = checklistDAO.createSection(dto);
		assert dto.getId() != null : "Not saved";
	}
	@Test
	public void testChecklistSectionsCreate() throws DataException {
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		SectionDTO dto = secDTO.transform().transform();
		dto = checklistDAO.createSection(dto);
		assert dto.getId() != null : "Not able to save Empty Session";
		checklistDAO.addSecToCL(temp,dto);
		
	}

	@Test
	public void testLoadChecklistSections() throws DataException {
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		SectionDTO dto = secDTO.transform().transform();
		dto = checklistDAO.createSection(dto);	
		checklistDAO.addSecToCL(temp,dto);
		List<SectionDTO> sections = checklistDAO.loadAllSections(temp);
		assert sections.size() == 1 : "Section Not attached";
	}

	@Test
	public void testRemoveSection() throws DataException{
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		SectionDTO dto = secDTO.transform().transform();
		dto = checklistDAO.createSection(dto);	
		checklistDAO.addSecToCL(temp,dto);
		List<SectionDTO> sections = checklistDAO.loadAllSections(temp);
		assert sections.size() == 1 : "Secion Not attached";
		checklistDAO.remSecToCL(temp,dto);
		assert sections.size() == 1 : "Secion Not removed";
	}

	@Test
	public void testAddDuplicateSection() throws DataException {
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		SectionDTO dtoT = secDTO.transform().transform();
		final SectionDTO dto = checklistDAO.createSection(dtoT);	
		checklistDAO.addSecToCL(temp,dto);
		assert checklistDAO.loadAllSections(temp).size() == 1 : "Section Not attached";
		assertThrows(DataException.class, () ->
		checklistDAO.addSecToCL(temp,dto));
		assert checklistDAO.loadAllSections(temp).size() == 1 : "Section Duplicated";
	}

	@Test
	public void testRemoveDupSection() throws DataException {
		CheckListDTO temp = checklistDAO.createCheckList(dto);
		SectionDTO dto = secDTO.transform().transform();
		dto = checklistDAO.createSection(dto);	
		checklistDAO.addSecToCL(temp,dto);
		
		assert checklistDAO.loadAllSections(temp).size() == 1 : "Secion Not attached";
		checklistDAO.remSecToCL(temp,dto);
		assert checklistDAO.loadAllSections(temp).size() == 0 : "Secion Not removed";
		checklistDAO.remSecToCL(temp,dto);
		assert checklistDAO.loadAllSections(temp).size() == 0 : "Secion Not removed";
	}

}