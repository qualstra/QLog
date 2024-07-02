package com.enoch.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.Resource;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.enoch.ApplicationContext;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.common.exception.ServiceException;
import com.enoch.config.ApplicationProperties;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.misc.Tuple;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.inst.State;
import com.enoch.reports.CASReport;
import com.enoch.reports.HTMLEngine;
import com.enoch.reports.PdfExporter;
import com.enoch.reports.Report;
import com.enoch.reports.risq.RISQReport;
import com.enoch.service.CheckListInstService;
import com.enoch.service.NCService;
import com.enoch.service.processor.Processor;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.QuestionInstVO;
import com.enoch.vo.SectionInstVO;

@RestController
@CrossOrigin
public class CheckListInstController {

	@Autowired
	CheckListInstService chkInstService;

	@Autowired
	private AttachmentService attServ;
	
	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}


	@RequestMapping(value = "/rs/processChecklist/{uuid}/{processor}", method = RequestMethod.POST)
	public void processChecklist(@PathVariable("uuid") UUID uuid, @PathVariable("processor") String processor)
			throws Exception {
//		try {
//			chkInstService.process(uuid, processor);
//		} catch (Exception e) {
//			throw new ServiceException("error while processing checklist ", e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/searchCheckList", method = RequestMethod.POST)
	public ResponseEntity<List<CheckListInstVO>> search(@RequestBody CheckListSearchDTO chkVo) throws Exception {
//		try {			
//			List<CheckListInstDTO> mChecklist = chkInstService.fetchAllChkIns();
//			List<CheckListInstVO> cos = CopyUtil.map(mChecklist, (a) -> {
//				return Helper.toVO(a);
//			});
//			return ResponseEntity.ok(cos);
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} catch (Exception e) {
//			throw new ServiceException("error while searching masterCheckList ",e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@RequestMapping(value = "/rs/loadCheckListInstById/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<CheckListInstVO> findMasterChecklist(@PathVariable("uuid") UUID uuid) throws Exception {
//		try {
//			Optional<CheckListInstDTO> checklistDTO = chkInstService.loadCheckListInst(uuid);
//			if (checklistDTO.isPresent()) {
//				CheckListInstVO vo = Helper.toVO(checklistDTO.get());
//				List<SectionInstDTO> res = chkInstService.loadSections(checklistDTO.get());
//				vo.setSectionInstVOs(CopyUtil.map(res, a -> {
//					SectionInstVO secVO = Helper.toVO(a);
//					Collection<QuestionInstDTO> questions = chkInstService.loadQuestions(a);
//					List<QuestionInstVO> questionVOs = CopyUtil.map(questions, que -> {
//						QuestionInstVO queV = mapToVO(que);
//						return queV;
//					});
//					Integer ansQuestions = questions.stream().mapToInt(q -> {
//						return q.getAnswerBy() != null ? 1 : 0;
//					}).sum();
//					secVO.setQuestions(questionVOs);
//					secVO.setAnsQuesCount(ansQuestions);
//					secVO.setQuesCount(questionVOs.size());
//					return secVO;
//				}));
//				vo.setAnsQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getAnsQuesCount()).sum());
//				vo.setQuesCount(vo.getSections().stream().mapToInt(sec -> sec.getQuesCount()).sum());
//				return ResponseEntity.ok(vo);
//			} else {
//				return ResponseEntity.ok().build();
//			}
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} catch (Exception e) {
//			e.printStackTrace();
//			throw new ServiceException("error while loading masterCheckList ",e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	private QuestionInstVO mapToVO(QuestionInstDTO que) {
//		QuestionInstVO res = Helper.toVO(que);
//		res.setAttachments(CopyUtil.map(attServ.getAll(que, "PIC"), att -> Helper.toVO(att)));
//		res.setChildQuestions(CopyUtil.map(que.getChildQuestions(), inst -> {
//			QuestionInstVO childVO = Helper.toVO(inst);
//			childVO.setAttachments(CopyUtil.map(attServ.getAll(inst, "PIC"), att -> Helper.toVO(att)));
//			return childVO;
//		}));
//		return res;
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@Resource(name = "processor")
	Processor provess;
	@Autowired
	TestUtils tu;
	@Autowired
	NCService ncService;

	@RequestMapping(value = "/rs/createChecklistInst", method = RequestMethod.POST)
	public ResponseEntity<CheckListInstDTO> createChecklistInst(@RequestBody CheckListInstCreateDTO chkCreae)
			throws Exception {
//		if (chkCreae.getVessel() != null) {
//			chkCreae.setCompany(chkCreae.getVessel().getCompany());
//			chkCreae.setVessel(chkCreae.getVessel());
//		} else {
//			chkCreae.setCompany(ApplicationContext.getContext().getCompany());
//			chkCreae.setVessel(ApplicationContext.getContext().getShip());
//		}
//		CheckListInstDTO chkInst = chkInstService.clone(chkCreae);
//		// TO answer the question for testing
//		// tu.answerCheckList(chkInst);
//		CheckListInstDTO chkInstDB = chkInstService.loadCheckListInst(chkInst.getCheckId()).get();
//		return ResponseEntity.ok(chkInstDB);
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@RequestMapping(value = "/rs/submitChecklist/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<CheckListInstDTO> checklistSubmit(@PathVariable("uuid") UUID uuid,
			@RequestBody String remarks) throws Exception {
//		CheckListInstDTO chkInstDB = chkInstService.loadCheckListInst(uuid).get();
//
//		if (chkInstDB.getAnsQuesCount().compareTo(chkInstDB.getQuesCount()) == 0) {
//			Tuple<Boolean, String> submitRes = canSubmit(chkInstDB.getState());
//			if (submitRes.getValue1()) {
//				chkInstDB.setState(State.COMPLETED);
//				chkInstDB = chkInstService.save(chkInstDB);
//				provess.process(chkInstDB);
//				return ResponseEntity.ok(chkInstDB);
//			} else {
//				throw new ServiceException(submitRes.getValue2());
//			}
//		} else {
//			throw new ServiceException("Complte checklist to submit");
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@Autowired
	CASReport casReport;
	@Autowired
	ApplicationProperties properties;

	@Resource(name = "reportMap")
	private Map<String, Report> processorMap;
	
	private Report getReport(CheckListInstDTO cheDTO) {
		return processorMap.getOrDefault(cheDTO.getType(), casReport);
	}
	
	
	@RequestMapping(value = "/rs/report/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<org.springframework.core.io.Resource> name(@PathVariable("uuid") UUID uuid) throws Exception {
//		try {
//			HTMLEngine engine = new HTMLEngine();
//			Optional<CheckListInstDTO> checklistDTO = chkInstService.loadCheckListInst(uuid);
//			File file = File.createTempFile("REPORT", "pdf");
//			file.deleteOnExit();
//			if(checklistDTO.isPresent()){
//				CheckListInstDTO checkListInst = checklistDTO.get();
//				Report report  = getReport(checkListInst);
//				final String result = //new String(Files.readAllBytes(Paths.get("C:\\Users\\hp\\Downloads\\right_report\\report.html"))); 
//						engine.process(report.getReport(checkListInst));
//				/*//print result to html file
//		        try {
//		            FileWriter fileWriter = new FileWriter("C:\\Users\\Akrivis\\Desktop\\result.html");
//		            fileWriter.write(result);
//		            fileWriter.close();
//		            System.out.println("HTML file created successfully.");
//		        } catch (IOException e) {
//		            System.out.println("An error occurred while writing the HTML file: " + e.getMessage());
//		        }
//		        */
//				
//				PdfExporter exporter = new PdfExporter(properties);
//				exporter.exportHtml(result, file);
//				return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/pdf"))
//						.header(HttpHeaders.CONTENT_DISPOSITION,
//								"attachment; filename=\"" + report.getReportName() + ".pdf\"")
//						.body(loadFileAsResource(file.getAbsolutePath()));
//			}
//			return ResponseEntity.notFound().build();
//		} catch (Exception e) {
//			return ResponseEntity.notFound().build();
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	public org.springframework.core.io.Resource loadFileAsResource(String fileName) throws Exception {
		try {
			Path filePath = Paths.get(fileName).toAbsolutePath().normalize();
			org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());
			if (resource.exists()) {
				return resource;
			} else {
				throw new FileNotFoundException("File not found " + fileName);
			}
		} catch (MalformedURLException ex) {
			throw new FileNotFoundException("File not found " + fileName);
		}
	}

	private Tuple<Boolean, String> canSubmit(State state) {
		switch (state) {
		case NOTSTARTED:
		case STARTED:
		case INPROGRESS:
			return new Tuple<Boolean, String>(false, "Checklist not completed");
		case COMPLETED:
		case CLOSED:
			return new Tuple<Boolean, String>(false, "Checklist already completed");
		case PENDING_SUBMISSION:
			return new Tuple<Boolean, String>(true, "Checklist can be closed");
		default:
			return new Tuple<Boolean, String>(false, "Unknown status");
		}

	}

	@RequestMapping(value = "/rs/answer/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<QuestionInstDTO> answerSubmit(@PathVariable("uuid") UUID uuid, @RequestBody String remarks)
		throws Exception {
//			try {
//				QuestionInstDTO dto = chkInstService.answer(uuid, remarks);
//				return ResponseEntity.ok(dto);
//		}
//			catch (Exception e) {
//			throw new ServiceException("Error saving checklist ", e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@RequestMapping(value = "/rs/answer/save/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<QuestionInstDTO> answerSave(@PathVariable("uuid") UUID uuid, @RequestBody String remarks)
		throws Exception {
		
//		try {
//			QuestionInstDTO dto = chkInstService.answer(uuid, remarks, false);
//			return ResponseEntity.ok(dto);
//		}
//		catch (Exception e) {
//			throw new ServiceException("Error saving checklist ", e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@RequestMapping(value = "/rs/summaryData/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<String> getSummaryData(@PathVariable("uuid") UUID uuid) throws Exception {
//		return ResponseEntity.ok(chkInstService.getSummaryData(uuid).toString());
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}

	@RequestMapping(value = "/rs/summaryData/{uuid}", method = RequestMethod.PUT)
	public ResponseEntity<String> saveSummaryData(@PathVariable("uuid") UUID uuid, @RequestBody String jsonData)
			throws Exception {
//		JSONObject summaryData = new JSONObject(jsonData);
//		return ResponseEntity.ok(chkInstService.saveSummaryData(uuid, summaryData).toString());
        throw new ServiceUnavailableException("Service is unavailable for QLOG");
	}
}
