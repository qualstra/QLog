package com.enoch.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.enoch.common.exception.ServiceException;
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
import com.enoch.dto.RankDTO;
import com.enoch.master.service.MasterService;
import com.enoch.role.dto.PrivSearchCriteria;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.EntitlementService;
import com.enoch.service.RankService;
import com.enoch.service.UserService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.entitlement.RankVO;
import com.enoch.vo.entitlement.RoleVO;

@RestController
@CrossOrigin
public class EntitlementController {

	protected final Log logger = LogFactory.getLog(EntitlementController.class);

	@Autowired
	MasterService masterService;
	@Autowired
	EntitlementService entitlementService;

	@Autowired
	RankService rankService;
	@Autowired
	UserService uService;

	@RequestMapping(value = "/rs/ranks", method = RequestMethod.GET)
	public ResponseEntity<List<RankVO>> listRanks() throws Exception {
		try {
			List<RankVO> countries = CopyUtil.map(rankService.getRanks(), (some) -> Helper.toVO(some));
			return ResponseEntity.ok(countries);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new Exception("error while listing ranks");
//		}
	}
	
	@RequestMapping(value = "/rs/ranks", method = RequestMethod.POST)
	public ResponseEntity<List<RankVO>> searchRanks(@RequestBody RankVO rank) throws Exception {
		try {
			List<RankVO> ranks = CopyUtil.map(rankService.searchRanks(Helper.toDTO(rank)), (some) -> Helper.toVO(some));
			return ResponseEntity.ok(ranks);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new Exception("error while searching ranks");
//		}
	}
	
	@RequestMapping(value = "/rs/privs", method = RequestMethod.GET)
	public ResponseEntity<List<PrivillegeDTO>> listPrivilleges() throws Exception {
		try {
			return ResponseEntity.ok(entitlementService.searchPrivilleges(new PrivSearchCriteria()));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new Exception("error while listing privilleges");
//		}
	}
	
	@RequestMapping(value = "/rs/privs/role/{roleid}", method = RequestMethod.GET)
	public ResponseEntity<List<PrivillegeDTO>> rolePrivs(@PathVariable Long roleid) throws Exception {
		try {
			return ResponseEntity.ok(entitlementService.getPrivileges(new RoleDTO(roleid)));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new Exception("error while role privilleges");
//		}
	}
	
	@RequestMapping(value = "/rs/rank/create", method = RequestMethod.POST)
	public ResponseEntity<RankVO> createRank(@RequestBody RankVO roleVO) throws ResponseStatusException{
		try {

			List<RankDTO> roles = rankService
					.addRanks(Helper.toDTO(roleVO));
			if(roles != null && roles.size() ==1 ) {
				return ResponseEntity.ok(Helper.toVO(roles.get(0)));	
			} 
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Rank while already exists");
		} catch(ResponseStatusException ex) { 
			throw ex;
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new Exception("error while creating rank");
//		}
	}

	@RequestMapping(value = "/rs/roles", method = RequestMethod.GET)
	public ResponseEntity<List<RoleDTO>> listRoles() throws Exception {
		try {
			List<RoleDTO> roles = entitlementService
					.loadRolesForQualifier(ApplicationContext.getContext().getCompany().getId().toString());
			return ResponseEntity.ok(roles);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing roles", e);
//		}
	}
	
	@RequestMapping(value = "/rs/role/{roleid}/priv/update", method = RequestMethod.POST)
	public ResponseEntity<String> updaeRolePrivs(@RequestBody List<PrivillegeDTO> privs,@PathVariable Long roleid) throws Exception {
		try {

			List<PrivillegeDTO> addedPrivs =  privs.stream().filter(a -> a.isStatus()).collect(Collectors.toList());
			List<PrivillegeDTO> removePrivs =  privs.stream().filter(a -> !a.isStatus()).collect(Collectors.toList());
			Optional<RoleDTO> selRole = entitlementService.loadRolesForQualifier(ApplicationContext.getContext().getCompany().getId().toString()).stream().filter(a -> a.getId().equals(roleid)).findFirst();
			selRole.ifPresent(mapper -> {
				entitlementService.addPrivToRole(mapper, addedPrivs.toArray(new PrivillegeDTO[0]));
				entitlementService.removePrivToRole(mapper, removePrivs.toArray(new PrivillegeDTO[0]));
			});
			return ResponseEntity.ok("Success");
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating role", e);
//		}
	}	
	@RequestMapping(value = "/rs/role/create", method = RequestMethod.POST)
	public ResponseEntity<RoleVO> createRole(@RequestBody RoleVO roleVO) throws Exception {
		try {

			RoleDTO role =  Helper.toDTO(roleVO);
			role.setQualifier(ApplicationContext.getContext().getCompany().getId().toString());
			role = entitlementService
					.createRole(role);
			return ResponseEntity.ok(Helper.toVO(role));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			logger.error("Error listing Ranks", e);
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating role", e);
//		}
	}	
}
