package com.enoch.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import com.enoch.common.exception.ServiceException;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.config.ApplicationProperties;
import com.enoch.controller.CheckListInstController.ServiceUnavailableException;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.master.service.MasterService;
import com.enoch.model.nc.Status;
import com.enoch.reports.HTMLEngine;
import com.enoch.reports.PdfExporter;
import com.enoch.reports.excel.ExcelEngine;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CompanyService;
import com.enoch.service.EntitlementService;
import com.enoch.service.NCService;
import com.enoch.service.processor.Processor;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.NCSearchVO;
import com.enoch.vo.NCVO;

@RestController
@CrossOrigin
public class NCController {

	protected final Log logger = LogFactory.getLog(NCController.class);

	@Autowired
	MasterService masterService;
	@Autowired
	EntitlementService entitlementService;
	@Autowired
	AttachmentService attachService;

	@Autowired
	CompanyService compService;

	@Autowired
	CheckListInstService chkInstService;
	@Resource(name = "processor")
	Processor provess;
	@Autowired
	NCService ncService;

	@Autowired
	ApplicationProperties props;

	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}
	@RequestMapping(value = "/rs/ncreport/{uuid}", method = RequestMethod.GET)
	public ResponseEntity<org.springframework.core.io.Resource> ncReport(@PathVariable UUID uuid) throws Exception {
//		try {
//			ExcelEngine engine = new ExcelEngine(props);
//			List<NCVO> ncs = CopyUtil.map(ncService.loadNCSForChecklistInst(uuid), nc -> {
//				NCVO vo = Helper.toVO(nc);
//				populateAttachment(nc, vo);
//				return vo;
//			});
//			File file = File.createTempFile("REPORT", "pdf");
//			file.deleteOnExit();
//			engine.process(ncs,file );
//			return ResponseEntity.ok().contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
//					.header(HttpHeaders.CONTENT_DISPOSITION,
//							"attachment; filename=\"" + "nc" + ".xlsx\"")
//					.body(loadFileAsResource(file.getAbsolutePath()));
//		} catch (Exception e) {
//			logger.error(e);e.printStackTrace();
//			return ResponseEntity.notFound().build();
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}	
	private org.springframework.core.io.Resource loadFileAsResource(String fileName) throws Exception {
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

	@RequestMapping(value = "/rs/updateNC", method = RequestMethod.POST)
	public ResponseEntity<NCVO> updateNC(@RequestBody NCVO ncVO) throws Exception {
//		try {
//			NCDTO dto = Helper.toDTO(ncVO);
//			if(dto.getRemarks() == null || dto.getRemarks().trim().isEmpty() ) {
//				throw new ServiceException("Remarks is mandatory");
//			}
//			if(dto.getClosureTime() == null ) {
//				dto.setClosureTime(new Date());
//				
//			}
//			List<AttachmentDTO> attachments = attachService.getAll(dto,"NC_CLOSURE");
//			if(attachments == null || attachments.size() ==0) {
//				throw new ServiceException("Attachment is mandatory");
//			}
//			if(dto.getStatus() == Status.OPEN) {
//				dto.setStatus(Status.CLOSE);
//			}
//			return  ResponseEntity.ok(Helper.toVO(ncService.save(dto)));
//		}catch (ServiceException e) {
//			throw new ServiceException("NC error ", e);
//		}
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error saving NC", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "/rs/nc", method = RequestMethod.POST)
	public ResponseEntity<List<NCVO>> search(@RequestBody NCSearchVO searchVO) throws Exception {
//		try {
//			boolean todo = false;
//			UUID checkInstId = searchVO.getCheckListInstId();
//			if (todo) {
//				chkInstService.fetchAllChkIns().stream().filter(a -> a.getName().equalsIgnoreCase("cas")).forEach(chkInst -> {
//					List<SectionInstDTO> sections = chkInstService.loadSections(chkInst);
//					List<QuestionInstDTO> toAnswer = new ArrayList<QuestionInstDTO>();
//					sections.forEach(secInst -> {
//						Collection<QuestionInstDTO> questions = chkInstService.loadQuestions(secInst);
//						questions.forEach(que -> {
//							toAnswer.add(que);
//							toAnswer.addAll(que.getChildQuestions());
//						});
//					});
//					toAnswer.forEach(utils::answerQuestions);
//					provess.process(chkInst.getCheckId());
//
//				});
//
//			}
//			
//			List<NCDTO> ncs = checkInstId == null?  ncService.loadNCSForChecklistInst():ncService.loadNCSForChecklistInst(checkInstId);
//			return ResponseEntity.ok(CopyUtil.map(ncs, nc -> {
//				NCVO vo = Helper.toVO(nc);
//				populateAttachment(nc, vo);
//				return vo;
//			}));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching NC", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	private void populateAttachment(NCDTO dto, final NCVO vo) {
		try {
			List<AttachmentDTO> attachments = attachService.getAll(dto.getQuestion(),"PIC");
			vo.getNcAttachments().addAll(CopyUtil.map(attachments, a -> Helper.toVO(a)));
			List<AttachmentDTO> attachmentsClosure = attachService.getAll(dto,"NC_CLOSURE");
			vo.getNcClosureAttachments().addAll(CopyUtil.map(attachmentsClosure, a -> Helper.toVO(a)));
		} catch (ServiceException e) {
			throw new ServiceException("NC error ", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while populateProfileAttachment",
//					e);
//		}
	}
	@Autowired
	TestUtils utils;
}
