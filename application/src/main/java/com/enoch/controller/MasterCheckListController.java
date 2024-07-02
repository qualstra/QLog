package com.enoch.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import com.enoch.common.exception.ServiceException;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.ApplicationContext;
import com.enoch.config.ApplicationProperties;
import com.enoch.controller.CheckListInstController.ServiceUnavailableException;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;
import com.enoch.model.checklist.inst.Association;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.QuestionService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.CheckListSectionVO;
import com.enoch.vo.CheckListVO;
import com.enoch.vo.ChecklistUploadVO;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.MasterChecklistItemVO;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.spi.mapper.MappingException;

import net.minidev.json.JSONArray;
import net.minidev.json.JSONValue;
import net.minidev.json.parser.JSONParser;
import net.minidev.json.writer.JsonReaderI;

@RestController
@CrossOrigin
public class MasterCheckListController {

	@Autowired
	CheckListService mclService;
	@Autowired
	QuestionService qService;

	@Autowired
	CheckListInstService chkInstService;
	@Autowired
	ApplicationProperties aProp;
	
	
	
	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}


	@RequestMapping(value = "/rs/searchMastcheckList", method = RequestMethod.POST)
	public ResponseEntity<List<CheckListVO>> search(@RequestBody CheckListSearchDTO chkVo) throws Exception {
//		try {
//			List<CheckListDTO> mChecklist = mclService.getAllCheckLists();
//			List<CheckListVO> cos = CopyUtil.map(mChecklist, (a) -> {
//				return Helper.toVO(a);
//			});
//			return ResponseEntity.ok(cos);
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching masterCheckList",
////					e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/createMasterChecklist", method = RequestMethod.POST)
	public ResponseEntity<CheckListDTO> createMasterChecklist(@RequestBody CheckListVO chkVo) throws Exception {
//		try {
//			CheckListDTO checklistDTO = new CheckListDTO();
//			checklistDTO.setName(chkVo.getName());
//			checklistDTO.setRemarks(chkVo.getRemarks());
//			checklistDTO.setActive(true);
//			checklistDTO.setState(State.DRAFT);
//			checklistDTO.setShortDesc(chkVo.getRemarks());
//			checklistDTO.setLongDesc(chkVo.getRemarks());
//			checklistDTO.setData("{}");
//			checklistDTO = mclService.create(checklistDTO);
//			return ResponseEntity.ok(checklistDTO);
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating masterCheckList",
////					e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/findMasterCheckListById/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<CheckListVO> findMasterChecklist(@PathVariable("uuid") UUID uuid) throws Exception {
//		try {
//			Optional<CheckListDTO> checklistDTO = mclService.findCheckListByUID(uuid).stream()
//					.max((a, b) -> a.getVersion().compareTo(b.getVersion()));
//			if (checklistDTO.isPresent()) {
//				CheckListVO vo = Helper.toVO(checklistDTO.get());
//				List<SectionDTO> res = mclService.loadAllSections(checklistDTO.get());
//				vo.setMasterCheckListSectionVOs(CopyUtil.map(res, a -> {
//					CheckListSectionVO secVO = Helper.toVO(a);
//					Collection<QuestionDTO> questions = qService.loadAllQuestios(a);
//					List<MasterChecklistItemVO> questionVOs = CopyUtil.map(questions, que -> {
//						MasterChecklistItemVO queV = Helper.toVO(que);
//						queV.setSectionId(secVO.getId());
//						return queV;
//					});
//
//					secVO.setMasterChecklistItemVOs(questionVOs);
//					return secVO;
//				}));
//
//				return ResponseEntity.ok(vo);
//			} else {
//				return ResponseEntity.ok().build();
//			}
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while loading masterCheckList",
////					e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");


	}

	@RequestMapping(value = "/rs/createMasterChecklistSection", method = RequestMethod.POST)
	public ResponseEntity<CheckListSectionVO> createSection(@RequestBody CheckListSectionVO chkVo) throws Exception {
//		try {
//			SectionDTO section = Helper.toDTO(chkVo);
//			CheckListDTO checklist = new CheckListDTO();
//			checklist.setId(chkVo.getCheckListId());
//			section.setActive(true);
//			section.setState(State.DRAFT);
//			ChecklistSectionDTO chkSecDto = mclService.create(section, checklist);
//			CheckListSectionVO vo = Helper.toVO(chkSecDto.getSection());
//			vo.setCheckListId(chkSecDto.getChecklist().getId());
//			return ResponseEntity.ok(vo);
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while saving Section", e);
////
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");


	}

// TODO : DONOT use this method
	@RequestMapping(value = "rs/createMasterChecklistItem", method = RequestMethod.POST)
	public ResponseEntity<?> createQuestion(@RequestBody String chkVo) throws Exception {
//		try {
//			ObjectMapper mapper = new ObjectMapper();
//			Map<String, ?> values = mapper.readValue(chkVo, Map.class);
//			String secId = values.get("sectionId").toString();
//			SectionDTO secDto = mclService.loadSection(Long.parseLong(secId));
//			QuestionDTO q = null;// QuestionHelper.buildQuestion(values);
//			qService.save(q, secDto);
//			return ResponseEntity.ok().build();
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while createQuestion", e);
////
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");


	}

	@RequestMapping(value = "rs/questionTypes", method = RequestMethod.GET)
	public ResponseEntity<?> questionTypes() throws Exception {
//		try {
//			return ResponseEntity.ok(QuestionType.values());
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		}
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while questionTypes", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "rs/mcDownload/{checklist}/{company}", method = RequestMethod.GET)
	public ResponseEntity<?> mcDownload(@PathVariable String checklist,@PathVariable String company) throws Exception {
//		 try {
//			 
//	            // Find files that contain both s1 and s2 in their names
//	            Path file = Files.list(Paths.get(aProp.getResourceFolder() + "/masterchecklist"))
//	            	    .filter(path -> {
//	                        String fileName = path.getFileName().toString().toLowerCase();
//	                        return fileName.contains(checklist.toLowerCase()) && fileName.contains(company.toLowerCase());
//	                    }).findFirst()
//	                    .orElse(null);
//	            	//System.out.println(checklist+company+file);
//	            if (file != null) {
//	                Resource resource = new UrlResource(file.toUri());
//	                if (resource.exists()) {
//	                    return ResponseEntity.ok()
//	                            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
//	                            .body(resource);
//	                } else {
//	                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	                }
//	            } else {
//	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//	            }
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//	        }
		       throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@Autowired
	ApplicationProperties appProperties;

	@RequestMapping(value = "rs/mcUpload", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> mcUpload(@ModelAttribute ChecklistUploadVO file) throws Exception {
//		try {
//			File file2 = File.createTempFile("Qual2", "CHK");			
//			CompanyDTO comp = null;
//			try {
//				comp = Helper.toDTO(file);
//			} catch (Exception e) {
//				e.printStackTrace();
//				comp = ApplicationContext.getContext().getCompany();
//			}
//			file.getFile().transferTo(file2);
//			FileInputStream fis = new FileInputStream(file2);
//			Workbook workbook = WorkbookFactory.create(fis);
//			String checklistType= workbook.getSheetAt(0).getRow(3).getCell(0).toString();
//			File file3 = new File(aProp.getResourceFolder() + "/masterchecklist",checklistType+"_"+comp.getName()+".xlsx");
//			if (!file3.exists()) {
//	            file3.mkdirs();
//			}
//			Files.copy(file2.toPath(), file3.toPath(), StandardCopyOption.REPLACE_EXISTING);
//			mclService.buildChecklist(comp, file2, false);
//			return ResponseEntity.ok().build();
//		} 
//		catch (Exception e) {
//			e.printStackTrace();
//			throw new ServiceException("Checklist Error " + e.getMessage(), e);
//		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "rs/attrUpload", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> attrUpload(@ModelAttribute MultipartFile file) throws Exception {
//		File file2 = new File(aProp.getResourceFolder() + "/attribute","process.xlsx");
//		if (!file2.exists()) {
//            file2.mkdirs();
//		}
//		file.transferTo(file2);
//		//System.out.println("success");
//		return ResponseEntity.ok().build();
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	private static class UUIDReader extends JsonReaderI<UUID> {
		public UUIDReader() {
			super(null);
		}

		public UUID convert(Object src) {
			if (src == null) {
				return null;
			}
			if (src instanceof String) {
				return UUID.fromString((String) src);
			}
			if (UUID.class.isAssignableFrom(src.getClass())) {
				return (UUID) src;
			}
			throw new MappingException("can not map a " + src.getClass() + " to " + Boolean.class.getName());
		}
	}

}
