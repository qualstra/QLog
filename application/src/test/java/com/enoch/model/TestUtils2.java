package com.enoch.model;

import static com.enoch.builder.QuestionConstants.ANSWER;
import static com.enoch.builder.QuestionConstants.CODE;
import static com.enoch.builder.QuestionConstants.OPTION;
import static com.enoch.builder.QuestionConstants.REMARK;
import static com.enoch.builder.QuestionConstants.WEIGHTAGE;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.controller.MasterCheckListController;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.model.checklist.inst.Association;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.QuestionService;
import com.enoch.service.impl.QuestionUtils;
import com.enoch.utils.StringUtils;
import com.enoch.vo.ChecklistUploadVO;

@Component
public class TestUtils2 {

	@Autowired
	private CheckListInstService checklistInstServ;
	@Autowired
	private CheckListService checklistServ;
	@Autowired
	QuestionService qService;
	@Autowired
	private MasterCheckListController controller;
	@Autowired
	AttachmentService aServ ;
	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
			"CITY", true, "it@enoch.com", "",Helper.getAuditTrail());

	final ShipDTO ship = new ShipDTO(1L, UUID.randomUUID(), "Test", "Test", "Test", "Test", "Test", "Test", "Test",
			comp.transform(), "Test", "Test@Test.com", new Date(), Helper.getAuditTrail());

	public CheckListInstDTO cloneChecklist(String chekcListName, boolean upload) throws Exception {
		
		if(upload) {
			ChecklistUploadVO uVO = new ChecklistUploadVO();
			uVO.setFile(getMFile(new File(CheckListInstTests.class.getResource("questions.xlsx").getFile())));
			controller.mcUpload(uVO);
		}
			
		List<CheckListDTO> checklists = checklistServ.getAllCheckLists();
		assert checklists.size() == 9 : "many checklist it should be 9";
		CheckListDTO chDto = checklists.stream().filter(a -> a.getName().equalsIgnoreCase(chekcListName)).findFirst()
				.get();
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
	public void answerQuestions(List<QuestionInstDTO> toAnswer) {
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
				Tuple<Integer,Integer> qAttMinMax = QuestionUtils.getAttachmentTupple(o);
				Tuple<Integer,Integer> oAttMinMax = QuestionUtils.getAttachmentTupple(value);
				Integer minA = QuestionUtils.getMinAttachment(qAttMinMax, oAttMinMax);
				Integer maxA = QuestionUtils.getMaxAttachment(qAttMinMax, oAttMinMax);
				for (int i = 0; i < minA; i++) {
					aServ.create(new AttachmentDTO(null,question.getUUID(),"PIC","location","location","JPG","JPG","Sample","",true,Helper.getAuditTrail()));
				}
				answer.put(REMARK, "I like this");
				answer.put(WEIGHTAGE, "25");
				answer.put(ANSWER, value.get(CODE));
				break;
			case String:
				JSONObject data = new JSONObject(question.getData());
				if(StringUtils.getBoolVal(data.optString(REMARK, "Y"), true)) {
					answer.put(REMARK, "I like this");	
				}
				answer.put(WEIGHTAGE, "30");
				break;
			}
			QuestionInstDTO dto = checklistInstServ.answer(question.getUUID(), answer.toString());
			JSONObject o = new JSONObject(dto.getAnswer());
			String remark =  o.optString(REMARK);
			String weigtage = (String) o.optString(WEIGHTAGE);

			switch (dto.getType()) {
			case Option:
				String ans = (String) o.get(ANSWER);
				assert remark.contentEquals("I like this") : "Remark is different";
				assert weigtage.contentEquals("25") : "weightage is different";
				assert ans.contentEquals("Y") : "Answer is different";
				break;
			case String:

				JSONObject data = new JSONObject(question.getData());
				if(!StringUtils.getBoolVal(data.optString(REMARK, "Y"), true)) {
					assert remark.contentEquals("") : "Remark is not empty";
				} else {
					assert remark.contentEquals("I like this") : "Remark is different";
				}

				
				assert weigtage.contentEquals("30") : "weightage is different";
				break;
			}
		});
	}
	public int countQuestionInsts(Collection<QuestionInstDTO> questions) {
		return questions.stream().mapToInt(que -> {
			return que.getChildQuestions().size() + 1;
		}).sum();
	}

	public int countQuestions(Collection<QuestionDTO> questions) {
		return questions.stream().mapToInt(que -> {
			return que.getChildQuestions().size() + 1;
		}).sum();
	}

	public int countQuestions(final SectionInstDTO secInst, List<SectionDTO> res) {
		return res.stream().filter(sec -> sec.getName().equalsIgnoreCase(secInst.getName())).findFirst()
				.map(sec -> countQuestions(qService.loadAllQuestios(sec))).orElse(new Integer(0));
	}

	public static MultipartFile getMFile(File f) {
		Path path = Paths.get(f.getAbsolutePath());
		String name = f.getName();
		String originalFileName = f.getName();
		String contentType = "application/exe";
		byte[] content = null;
		try {
		    content = Files.readAllBytes(path);
		} catch (final IOException e) {
		}
		MultipartFile result = new MockMultipartFile(name,
		                     originalFileName, contentType, content);
		
		return result;
	}
}
