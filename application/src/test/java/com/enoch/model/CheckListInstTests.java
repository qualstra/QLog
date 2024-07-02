package com.enoch.model;

import static com.enoch.builder.QuestionConstants.ANSWER;
import static com.enoch.builder.QuestionConstants.CODE;
import static com.enoch.builder.QuestionConstants.OPTION;
import static com.enoch.builder.QuestionConstants.REMARK;
import static com.enoch.builder.QuestionConstants.WEIGHTAGE;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.multipart.MultipartFile;

import com.enoch.builder.QuestionBuilder;
import com.enoch.builder.QuestionHelper;
import com.enoch.controller.MasterCheckListController;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.exception.DataException;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;
import com.enoch.model.checklist.inst.Association;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.NCService;
import com.enoch.service.QuestionService;
import com.enoch.service.processor.Processor;
import com.enoch.utils.StringUtils;
import com.enoch.vo.ChecklistUploadVO;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class CheckListInstTests {

	@Autowired
	private TestUtils2 tu;
	@Autowired
	private MasterCheckListController controller;
	@Autowired
	private CheckListInstService checklistInstServ;
	@Autowired
	private CheckListService checklistServ;
	@Autowired
	private QuestionService qServ;

	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
			"CITY", true, "it@enoch.com","", Helper.getAuditTrail());
	final CheckListDTO dto = new CheckListDTO(null, null, null, ChecklistType.SIMPLE, "Test", "Short Desc", "Long Desc",
			"remarks", "{}", comp.transform(), State.DRAFT, true, "None", "None", Helper.getAuditTrail());
	final SectionDTO secDTO = new SectionDTO(null, null, null, null, "SecName", "DESC", "LDESC", "{}", comp.transform(),
			State.DRAFT, true, "NOPRE", "NOPOST", Helper.getAuditTrail());
	final ShipDTO ship = new ShipDTO(1L, UUID.randomUUID(), "Test", "Test", "Test", "Test", "Test", "Test", "Test",
			comp.transform(), "Test", "Test@Test.com", new Date(), Helper.getAuditTrail());

	// write test cases here
	//@Test
	public void testCreateChecklistInst() throws DataException {
		CheckListDTO dtoTmp = checklistServ.create(dto);
		ChecklistSectionDTO secDto = checklistServ.create(secDTO, dtoTmp);
		QuestionDTO question = new QuestionBuilder("THis is a sample question").setType(QuestionType.String)
				.setAttrib(QuestionHelper.HEADER_QUESTION_OPT, new JSONObject("{\"min-len\":100,\"max-len\":500}"))
				.build();
		question.setCompany(comp.transform());
		qServ.save(question, secDto.getSection());
		CheckListInstCreateDTO checklistDTO = new CheckListInstCreateDTO();
		checklistDTO.setAssocType(Association.RANK);
		checklistDTO.setMasterChkUUID(dtoTmp.getCheckId());
		checklistDTO.setCompany(comp.transform());
		checklistDTO.setEndDate(new Date());
		checklistDTO.setStartDate(new Date());
		checklistDTO.setVessel(ship);
		checklistInstServ.clone(checklistDTO);
		assert dtoTmp.getId() != null : "Not able to save";
	}

	//@Test
	public void testUpload() throws Exception {
		ChecklistUploadVO uVO = new ChecklistUploadVO();
		uVO.setFile(tu.getMFile(new File(CheckListInstTests.class.getResource("questions.xlsx").getFile())));
		controller.mcUpload(uVO);
		List<CheckListDTO> checklists = checklistServ.getAllCheckLists();
		assert checklists.size() == 9 : "many checklist it should be 9";
		CheckListDTO chDto = checklists.get(0);
		List<SectionDTO> res = checklistServ.loadAllSections(chDto);

		CheckListInstCreateDTO checklistDTO = new CheckListInstCreateDTO();
		checklistDTO.setAssocType(Association.RANK);
		checklistDTO.setMasterChkUUID(chDto.getCheckId());
		checklistDTO.setCompany(comp.transform());
		checklistDTO.setStartDate(new Date());
		checklistDTO.setEndDate(new Date());
		checklistDTO.setVessel(ship);
		CheckListInstDTO chkInst = checklistInstServ.clone(checklistDTO);

		CheckListInstDTO chkInstDB = checklistInstServ.loadCheckListInst(chkInst.getCheckId()).get();
		List<SectionInstDTO> sections = checklistInstServ.loadSections(chkInstDB);
		sections.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = getQuestions(secInst, res);
			assert questions.size() == cehcklistcount : 
				String.format("Wrong no of questions %s expected %s", questions.size(),cehcklistcount);
		});
	}

	@Autowired
	QuestionService qService;

	private int getQuestions(final SectionInstDTO secInst, List<SectionDTO> res) {
		return res.stream().filter(sec -> sec.getName().equalsIgnoreCase(secInst.getName())).findFirst()
				.map(sec -> qService.loadAllQuestios(sec).size()).orElse(new Integer(0));
	}

	//@Test
	public void testAnswer() throws Exception {
		CheckListInstDTO chkInst = cloneChecklist("AMSA");
		CheckListInstDTO chkInstDB = checklistInstServ.loadCheckListInst(chkInst.getCheckId()).get();
		List<SectionInstDTO> sections = checklistInstServ.loadSections(chkInstDB);
		CheckListDTO checklistDTO = checklistServ.findCheckListByUID(chkInst.getMChk()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		List<SectionDTO> res = checklistServ.loadAllSections(checklistDTO);
		
		List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
		sections.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = getQuestions(secInst, res);
			toAnswer.addAll(questions);
			assert questions.size() == cehcklistcount : 
				String.format("Wrong no of questions %s expected %s", questions.size(),cehcklistcount);
		});
		answerQuestions(toAnswer);
	}
	
	@Resource(name = "processor")
	Processor provess;

	@Test
	public void calculateNC() throws Exception {
		CheckListInstDTO chkInst = cloneChecklist("AMSA");
		CheckListInstDTO chkInstDB = checklistInstServ.loadCheckListInst(chkInst.getCheckId()).get();
		List<SectionInstDTO> sections = checklistInstServ.loadSections(chkInstDB);
		CheckListDTO checklistDTO = checklistServ.findCheckListByUID(chkInst.getMChk()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		List<SectionDTO> res = checklistServ.loadAllSections(checklistDTO);
		
		List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
		sections.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			int cehcklistcount = getQuestions(secInst, res);
			toAnswer.addAll(questions);
			assert questions.size() == cehcklistcount : 
				String.format("Wrong no of questions %s expected %s", questions.size(),cehcklistcount);
		});
		answerQuestions(toAnswer);
		
		provess.process(chkInst.getCheckId());
		
		List<NCDTO> ncs = ncService.loadNCSForChecklistInst(chkInst.getCheckId());
		
		assert ncs.size() == 2 : "NC count is wrong" ;
		
	}
	@Autowired
	NCService ncService;
	private void answerQuestions(List<QuestionInstDTO> toAnswer) {
		toAnswer.forEach(question -> {
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
				answer.put(REMARK, "I like this");
				answer.put(WEIGHTAGE, "25");
				answer.put(ANSWER, value.get(CODE));
				break;
			case String:
				JSONObject data = new JSONObject(question.getData());
				JSONObject options = data.getJSONObject(OPTION);
				if(StringUtils.getBoolVal(options.optString(REMARK, "Y"), true)) {
					answer.put(REMARK, "I like this");	
				}
				answer.put(WEIGHTAGE, "30");
				break;
			}
			QuestionInstDTO dto = checklistInstServ.answer(question.getUUID(), answer.toString());
			JSONObject o = new JSONObject(dto.getAnswer());
			String remark = (String) o.get(REMARK);
			String weigtage = (String) o.get(WEIGHTAGE);

			switch (dto.getType()) {
			case Option:
				String ans = (String) o.get(ANSWER);
				assert remark.contentEquals("I like this") : "Remark is different";
				assert weigtage.contentEquals("25") : "weightage is different";
				assert ans.contentEquals("Y") : "Answer is different";
				break;
			case String:
				assert remark.contentEquals("I like this") : "Remark is different";
				assert weigtage.contentEquals("30") : "weightage is different";
				break;
			}
		});
	}

	private CheckListInstDTO cloneChecklist(String chekcListName) throws Exception {
		ChecklistUploadVO uVO = new ChecklistUploadVO();
		uVO.setFile(tu.getMFile(new File(CheckListInstTests.class.getResource("questions.xlsx").getFile())));
		controller.mcUpload(uVO);
		List<CheckListDTO> checklists = checklistServ.getAllCheckLists();
		assert checklists.size() == 9 : "many checklist it should be 9";
		CheckListDTO chDto = checklists.stream().filter(a -> a.getName().equalsIgnoreCase(chekcListName)).findFirst().get();
		CheckListInstCreateDTO checklistDTO = new CheckListInstCreateDTO();
		checklistDTO.setAssocType(Association.RANK);
		checklistDTO.setMasterChkUUID(chDto.getCheckId());
		checklistDTO.setCompany(comp.transform());
		checklistDTO.setStartDate(new Date());
		checklistDTO.setEndDate(new Date());
		checklistDTO.setVessel(ship);
		CheckListInstDTO chkInst = checklistInstServ.clone(checklistDTO);
		return chkInst;
	}
}