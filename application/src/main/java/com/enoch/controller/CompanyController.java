package com.enoch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.ApplicationContext;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.common.exception.ServiceException;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.master.service.MasterService;
import com.enoch.service.CompanyService;
import com.enoch.service.EntitlementService;
import com.enoch.vo.AttachmentVO;
import com.enoch.vo.CompanyVO;
import javax.validation.ConstraintViolationException;
@RestController
@CrossOrigin
public class CompanyController {

	protected final Log logger = LogFactory.getLog(CompanyController.class);

	@Autowired
	MasterService masterService;
	@Autowired
	EntitlementService entitlementService;
	@Autowired
	AttachmentService attachService;

	@Autowired
	CompanyService compService;
	
	
//
//	@RequestMapping(value = "/rs/SearchMasterShipCompany", method = RequestMethod.POST)
//	public ResponseEntity<List<CompanyVO>> create(@RequestBody CompanySearchDTO dto) throws Exception {
//		List<CompanyVO> companies = CopyUtil.map(compService.search(dto), (comp) -> Helper.toVO(comp));
//		return ResponseEntity.ok(companies);
//
//	}

	@RequestMapping(value = "/rs/SearchMasterShipCompany", method = RequestMethod.POST)
	public ResponseEntity<List<CompanyVO>> search(@RequestBody CompanyVO companyvo) throws Exception {
		try {
			CompanySearchDTO cdto = Helper.toSearchDTO(companyvo);
			List<CompanyVO> vos = new ArrayList<>();
			compService.search(cdto).forEach(dto -> {
				CompanyVO vo = Helper.toVO(dto);
				populateProfileAttachment(dto, vo);
				vos.add(vo);
			});
			return ResponseEntity.ok(vos);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching company", e);
//		}
		
			
		
		
	}

	private void populateProfileAttachment(CompanyDTO dto, final CompanyVO vo ) {
		try {
		List<AttachmentDTO> attachments = attachService.getAll(dto);
		attachments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("PROFILE")).findAny()
				.ifPresent(a -> vo.setProlileAttachment(a));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while populateProfileAttachment", e);
//		}
	}

	@RequestMapping(value = "/rs/company/{uuid}", method = RequestMethod.POST)
	public ResponseEntity<CompanyVO> load(@PathVariable UUID uuid) throws Exception {
		try {
			CompanyDTO dto =  compService.load(uuid);
			CompanyVO company = Helper.toVO(dto);
			populateProfileAttachment(dto, company);
			return ResponseEntity.ok(company);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while loading company", e);
//		}

	}
	
	@RequestMapping(value = "/rs/company/staticReports", method = RequestMethod.POST)
	public ResponseEntity<List<AttachmentVO>> getCompanyReports(){
		try {
			List<AttachmentDTO> attachments = attachService.getAll(ApplicationContext.getContext().getCompany());
			List<AttachmentVO> attachmentsVo = attachments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("Report"))
					.map(a -> Helper.toVO(a)).collect(Collectors.toList());
			return ResponseEntity.ok(attachmentsVo);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while getCompanyReports", e);
//		}
	}

	@RequestMapping(value = "/rs/createCompany", method = RequestMethod.POST)
	public ResponseEntity<?> createCompany(@RequestBody CompanyVO compVo) throws Exception {
		try {
			compService.create(Helper.toDTO(compVo));
			return ResponseEntity.ok().build();
		}catch (ServiceException e) {
			throw new ServiceException("business error",e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating company", e);
//			
//		}
	}

	@RequestMapping(value = "/rs/updateMasterShipCompany/{id}", method = RequestMethod.PUT)
	public ResponseEntity<CompanyVO> updateCompany(@RequestBody CompanyVO compVo) throws Exception {
		try {
			return ResponseEntity.ok(Helper.toVO(compService.create(Helper.toDTO(compVo))));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while updating company", e);
//		}
	}

}
