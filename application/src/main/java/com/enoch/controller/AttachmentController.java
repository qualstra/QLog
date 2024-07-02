package com.enoch.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.enoch.common.exception.ServiceException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.config.ApplicationProperties;
import com.enoch.master.service.MasterService;
import com.enoch.service.EntitlementService;
import com.enoch.service.RankService;
import com.enoch.service.UserService;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.image.Compressor;
import com.enoch.vo.AttachmentVO;

@RestController
@CrossOrigin
public class AttachmentController {

	protected final Log logger = LogFactory.getLog(AttachmentController.class);

	@Autowired
	MasterService masterService;
	@Autowired
	EntitlementService entitlementService;
	@Autowired
	AttachmentService attachmentService;

	@Autowired
	RankService rankService;
	@Autowired
	UserService uService;
	@Autowired
	ApplicationProperties appProperties;

	@RequestMapping(value = "/rs/createAttachments", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> attachments(@ModelAttribute List<AttachmentVO> files) throws Exception {
		try {
			List<AttachmentDTO> result = new ArrayList<AttachmentDTO>();
			for(AttachmentVO file : files) {
				AttachmentDTO attachmentDTO = Helper.toDTO(file);
				attachmentDTO.setAuditTrail(com.enoch.service.Helper.getAuditTrail());
				Compressor.saveImage(file.getFile().getInputStream(),
						appProperties.getResourceFolder() + File.separatorChar + attachmentDTO.getLocationNoExt(),attachmentDTO.getExt());
				attachmentDTO = attachmentService.create(attachmentDTO);
				result.add(attachmentDTO); 
			}
			return ResponseEntity.ok(result);
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error listing ranks", e);
//		}
	}

	
	@RequestMapping(value = "/rs/createAttachment", method = RequestMethod.POST, consumes = "multipart/form-data")
	public ResponseEntity<?> attach(@ModelAttribute AttachmentVO file) throws Exception {
		try {

			AttachmentDTO attachmentDTO = Helper.toDTO(file);
			attachmentDTO.setAuditTrail(com.enoch.service.Helper.getAuditTrail());
			Compressor.saveImage(file.getFile().getInputStream(),
					appProperties.getResourceFolder() + File.separatorChar + attachmentDTO.getLocationNoExt(),attachmentDTO.getExt());
			/*
			 * CopyUtil.writeToFile(file.getFile().getInputStream(),
			 * appProperties.getResourceFolder() + File.separatorChar +
			 * attachmentDTO.getLocation());
			 */			attachmentDTO = attachmentService.create(attachmentDTO);
			return ResponseEntity.ok(attachmentDTO);
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error listing ranks", e);
//		}
	}

	@RequestMapping(value = "/rs/deleteAttachment/{id}", method = RequestMethod.POST)
	public ResponseEntity<?> deleteAttachment(@PathVariable("id") Long id) throws Exception {
		try {
			attachmentService.delete(id);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			throw new ServiceException("Error Deleting Attachment ",e);
		}

	}
	@RequestMapping(value = "/rs/updateAttachment")
	public ResponseEntity<AttachmentDTO> updateAttachment(@RequestBody AttachmentVO vo) throws Exception {
		try {
			Optional<AttachmentDTO> dbAttach = attachmentService.load(vo.getId());
			if(dbAttach.isPresent()) {
				AttachmentDTO attachmentDTO = dbAttach.get();
				attachmentDTO.setAuditTrail(com.enoch.service.Helper.getAuditTrail());
				attachmentDTO.setHeader(vo.getHeader());
				attachmentDTO.setDesc(vo.getDesc());
				attachmentDTO = attachmentService.update(attachmentDTO);
				return ResponseEntity.ok(attachmentDTO);
			} else {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
			}
			
			
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error updating attachments", e);
//		}
	}

}
