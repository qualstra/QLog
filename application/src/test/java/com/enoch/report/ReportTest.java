package com.enoch.report;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.attachment.service.AttachmentService;
import com.enoch.controller.MasterCheckListController;
import com.enoch.controller.TestUtils;
import com.enoch.dao.ShipDAO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.model.Company;
import com.enoch.model.Helper;
import com.enoch.model.TestUtils2;
import com.enoch.model.checklist.inst.Association;
import com.enoch.reports.CASReport;
import com.enoch.reports.HTMLEngine;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.QuestionInstVO;
import com.enoch.vo.SectionInstVO;
import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;


@DataJpaTest
@RunWith(SpringRunner.class)
public class ReportTest {
	@Autowired
	CASReport rEngine;

	@Autowired
	private AttachmentService attServ;

	private QuestionInstVO mapToVO(QuestionInstDTO que) {
		QuestionInstVO res = com.enoch.controller.Helper.toVO(que); 
		res.setAttachments(CopyUtil.map(attServ.getAll(que,"PIC"), att -> com.enoch.controller.Helper.toVO(att)));
		res.setChildQuestions(CopyUtil.map(que.getChildQuestions(), inst -> { 
			QuestionInstVO childVO = com.enoch.controller.Helper.toVO(inst);
			childVO.setAttachments(CopyUtil.map(attServ.getAll(inst,"PIC"), att -> com.enoch.controller.Helper.toVO(att)));
			return childVO;}));
		return res;
	}
	@Autowired
	CheckListInstService chkInstService;

	public CheckListInstVO populate(CheckListInstDTO checklistDTO){
		CheckListInstVO vo = com.enoch.controller.Helper.toVO(checklistDTO);
		List<SectionInstDTO> res = chkInstService.loadSections(checklistDTO);
		vo.setSectionInstVOs(CopyUtil.map(res, a -> {
			SectionInstVO secVO = com.enoch.controller.Helper.toVO(a);
			Collection<QuestionInstDTO> questions = chkInstService.loadQuestions(a);
			List<QuestionInstVO> questionVOs = CopyUtil.map(questions, que -> {
				QuestionInstVO queV = mapToVO(que);
				return queV;
			});
			Integer ansQuestions = questions.stream().mapToInt(q -> {return q.getAnswerBy() != null ? 1 : 0;}).sum();
			secVO.setQuestions(questionVOs);
			secVO.setAnsQuesCount(ansQuestions);
			secVO.setQuesCount(questionVOs.size());
			return secVO;
		}));
		vo.setAnsQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getAnsQuesCount()).sum());
		vo.setQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getQuesCount()).sum());
		
		return vo;
	}
	
	
	@Test
	public void headerTest() throws Exception {
		HTMLEngine engine = new HTMLEngine();
		CheckListInstDTO dto = cloneChecklist("CAS");
		answer(dto);
		final String result = engine.process(rEngine.getReport(populate(dto)));
		System.out.println("result:" + result);
		Document document = new Document();
		PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("src/main/resources/html.pdf"));
		document.open();
		XMLWorkerHelper.getInstance().parseXHtml(writer, document, new ByteArrayInputStream(result.getBytes()));
		document.close();
	//	new ExcelExporter().exportHtml(result, new File("src/main/resources/excel.xlsx"));
	}
	@Autowired
	TestUtils utils;

	private void answer(CheckListInstDTO dto) {
		List<SectionInstDTO> res = checklistInstServ.loadSections(dto);

		List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
		res.forEach(secInst -> {
			Collection<QuestionInstDTO> questions = checklistInstServ.loadQuestions(secInst);
			questions.forEach(q -> {
				toAnswer.add(q);
				toAnswer.addAll(q.getChildQuestions());
			});
			;
		});
		toAnswer.forEach(utils::answerQuestions);
	}
	@Autowired
	private MasterCheckListController controller;
	@Autowired
	private CheckListInstService checklistInstServ;
	@Autowired
	private CheckListService checklistServ;
	@Autowired
	private ShipDAO shipDAO;

	

	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
			"CITY", true, "it@enoch.com","", Helper.getAuditTrail());
	
	private CheckListInstDTO cloneChecklist(String chekcListName) throws Exception {
		
		List<CheckListDTO> checklists = checklistServ.getAllCheckLists();
		final ShipDTO ship = shipDAO.loadShipByName("GreatShip").get();
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
