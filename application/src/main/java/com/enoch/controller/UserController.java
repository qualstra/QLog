package com.enoch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.enoch.common.exception.ServiceException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.ApplicationContext;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.service.UserService;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.UserVO;

@RestController
@CrossOrigin
public class UserController {
	
	@Autowired
	UserService userService;
	@Autowired
	AttachmentService attachService;
	@RequestMapping(value = "/rs/createMasterUser", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody UserVO userVo)
			throws Exception {
		try {
		userService.create(Helper.toDTO(userVo),ApplicationContext.getContext().getCompany());
		return ResponseEntity.ok().build();
		
	}catch (ServiceException e) {
		throw new ServiceException("business error", e);
	}
//		catch(Exception e) {e.printStackTrace();
//		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating user", e);
//	}
	}
	
	
	@RequestMapping(value = "/rs/searchMasterUser", method = RequestMethod.POST)
	public ResponseEntity<List<UserVO>> search(@RequestBody UserVO userVo)
			throws Exception {
		try {
		UserSearchDTO sdto = Helper.toSearchDTO(userVo);
		List<UserVO> vos  = new ArrayList<>();
		userService.search(sdto).forEach(dto -> {
			UserVO uVo = Helper.toVO(dto.getUser());
			populateCompShipDetails(dto, uVo);
			vos.add(uVo );
		});
		return ResponseEntity.ok(vos);
		
	}catch (ServiceException e) {
		throw new ServiceException("business error", e);
	}
//		catch(Exception e) {e.printStackTrace();
//		throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching user", e);
//	}
	}


	private void populateCompShipDetails(CompanyUserDTO dto, UserVO uVo) {
		if (dto.getCompany() != null) {
			uVo.setCompanyId(dto.getCompany().getId());
			uVo.setShipcompany(dto.getCompany().getName());
		}
		if (dto.getShip() != null) {
			uVo.setVesselId(dto.getShip().getId());
			uVo.setVesselName(dto.getShip().getName());
		}
	}
	
	
	@RequestMapping(value = "/rs/findMasterUserListById", method = RequestMethod.POST)
	public ResponseEntity<UserVO> findMasterUserListById(@RequestBody UserVO userVo)
			throws Exception {
		try {
			UserDTO dto = userService.load(userVo.getUserName());
			UserVO vo = Helper.toVO(dto);
		
			List<AttachmentDTO> attacments = attachService.getAll(dto);
			Optional<AttachmentDTO> profileAtt = attacments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("PROFILE") ).findAny();
			Optional<AttachmentDTO> signAtt = attacments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("SIGN") ).findAny();
			profileAtt.ifPresent(a -> vo.setProlileAttachment(a));
			signAtt.ifPresent(a -> vo.setSignature(a));
			
			return ResponseEntity.ok(vo);	
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch(Exception e) {e.printStackTrace();
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while loading user", e);
//		}
			
	}	
	
	@RequestMapping(value = "/rs/updateMasterUser/{id}", method = RequestMethod.PUT)
	public ResponseEntity<UserVO> updateUser(@RequestBody UserVO userDto ) throws Exception{
		try {
			UserDTO user=userService.update(Helper.toDTO(userDto));
			return ResponseEntity.ok(Helper.toVO(user));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {e.printStackTrace();
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"error while updating user",e);
//		}
	
	}
	
//	private void populateProfileAttachment(UserDTO dto, final UserVO vo ) {
//		try {
//		List<AttachmentDTO> attachments = attachService.getAll(dto);
//		attachments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("PROFILE")).findAny()
//				.ifPresent(a -> vo.setProlileAttachment(a));
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		}catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while populateProfileAttachment", e);
//		}
//	}
	
//	@RequestMapping(value = "/rs/updateMasterUser/{id}", method = RequestMethod.PUT)
//	public ResponseEntity<UserVO> update(@RequestBody UserVO userVo) throws Exception {
//		try {
//			userService.create(Helper.toDTO(userVo));
//			return ResponseEntity.ok().build();
//		} catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while updating user", e);
//		}
//	}
//	
}