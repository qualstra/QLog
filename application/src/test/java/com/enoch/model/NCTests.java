package com.enoch.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.NCService;
import com.enoch.service.QuestionService;
import com.enoch.service.processor.Processor;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class NCTests {

	@Autowired
	private CheckListInstService checklistInstServ;
	@Autowired
	private CheckListService checklistServ;
	@Autowired
	TestUtils2 utils;
	@Autowired
	QuestionService qService;
	@Resource(name = "processor")
	Processor provess;

	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
			"CITY", true, "it@enoch.com","", Helper.getAuditTrail());
	final CheckListDTO dto = new CheckListDTO(null, null, null, ChecklistType.SIMPLE, "Test", "Short Desc", "Long Desc",
			"remarks", "{}", comp.transform(), State.DRAFT, true, "None", "None", Helper.getAuditTrail());
	final SectionDTO secDTO = new SectionDTO(null, null, null, null, "SecName", "DESC", "LDESC", "{}", comp.transform(),
			State.DRAFT, true, "NOPRE", "NOPOST", Helper.getAuditTrail());
	final ShipDTO ship = new ShipDTO(1L, UUID.randomUUID(), "Test", "Test", "Test", "Test", "Test", "Test", "Test",
			comp.transform(), "Test", "Test@Test.com", new Date(), Helper.getAuditTrail());

	@Test
	public void calculateNC() throws Exception {
		CheckListInstDTO chkInst = utils.cloneChecklist("CAS",true);
		CheckListInstDTO chkInstDB = checklistInstServ.loadCheckListInst(chkInst.getCheckId()).get();
		List<SectionInstDTO> sections = checklistInstServ.loadSections(chkInstDB);
		CheckListDTO checklistDTO = checklistServ.findCheckListByUID(chkInst.getMChk()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		List<SectionDTO> res = checklistServ.loadAllSections(checklistDTO);

		List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
		sections.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = utils.countQuestions(secInst, res);
			questions.forEach(q -> {
				toAnswer.add(q);
				toAnswer.addAll(q.getChildQuestions());
			});
			;
			assert utils.countQuestionInsts(questions) == cehcklistcount : String
					.format("Wrong no of questions %s expected %s", questions.size(), cehcklistcount);
		});
		utils.answerQuestions(toAnswer);

		provess.process(chkInst.getCheckId());

		List<NCDTO> ncs = ncService.loadNCSForChecklistInst(chkInst.getCheckId());
		System.out.println(this.checklistInstServ.getSummaryData(chkInst.getCheckId()));
		assert ncs.size() == 2 : "NC count is wrong";

	}
	@Test
	public void calculateHint() throws Exception {
		CheckListInstDTO chkInst = utils.cloneChecklist("CAS",true);
		CheckListInstDTO chkInstDB = checklistInstServ.loadCheckListInst(chkInst.getCheckId()).get();
		List<SectionInstDTO> sections = checklistInstServ.loadSections(chkInstDB);
		CheckListDTO checklistDTO = checklistServ.findCheckListByUID(chkInst.getMChk()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		List<SectionDTO> res = checklistServ.loadAllSections(checklistDTO);

		List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
		sections.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = utils.countQuestions(secInst, res);
			questions.forEach(q -> {
				toAnswer.add(q);
				toAnswer.addAll(q.getChildQuestions());
			});
			;
			assert utils.countQuestionInsts(questions) == cehcklistcount : String
					.format("Wrong no of questions %s expected %s", questions.size(), cehcklistcount);
		});
		utils.answerQuestions(toAnswer);
		chkInstDB.setState(com.enoch.model.checklist.inst.State.COMPLETED);
		chkInstDB = checklistInstServ.save(chkInstDB);
		CheckListInstDTO chkInst2 = utils.cloneChecklist("CAS",false);
		CheckListInstDTO chkInstDB2 = checklistInstServ.loadCheckListInst(chkInst2.getCheckId()).get();
		List<SectionInstDTO> sections2 = checklistInstServ.loadSections(chkInstDB2);
		CheckListDTO checklistDTO2 = checklistServ.findCheckListByUID(chkInst2.getMChk()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		List<SectionDTO> res2 = checklistServ.loadAllSections(checklistDTO2);

		List<QuestionInstDTO> toAnswer2 = new ArrayList<QuestionInstDTO>();
		sections2.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = utils.countQuestions(secInst, res2);
			questions.forEach(q -> {
				toAnswer2.add(q);
				toAnswer2.addAll(q.getChildQuestions());
			});
			;
			assert utils.countQuestionInsts(questions) == cehcklistcount : String
					.format("Wrong no of questions %s expected %s", questions.size(), cehcklistcount);
		});

	}
	@Autowired
	NCService ncService;

}