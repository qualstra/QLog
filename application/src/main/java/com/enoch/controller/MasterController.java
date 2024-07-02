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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.constant.VoyageStatus;
import com.enoch.controller.CheckListInstController.ServiceUnavailableException;
import com.enoch.dto.PortSearchDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;
import com.enoch.master.service.MasterService;
import com.enoch.service.EntitlementService;
import com.enoch.service.RankService;
import com.enoch.service.UserService;
import com.enoch.vo.PortVO;

@RestController
@CrossOrigin
public class MasterController {

	protected final Log logger = LogFactory.getLog(MasterController.class);

	@Autowired
	MasterService masterService;
	@Autowired
	EntitlementService entitlementService;

	@Autowired
	UserService uService;
	@Autowired
	RankService rankService;
	
	
	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}


	@RequestMapping(value = "/rs/voyagestatus", method = RequestMethod.GET)
	public ResponseEntity<?> getAllVOyageStatuses() throws Exception {
//		try {
//		return ResponseEntity.ok(VoyageStatus.values());
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while getAllVoyageStatuses", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	/*
	 * @RequestMapping(value = "/rs/ranks", method = RequestMethod.GET) public
	 * ResponseEntity<List<RankVO>> getAllRanks() throws Exception { List<RankVO>
	 * res =
	 * CopyUtil.map(rankService.getRanks(ApplicationContext.getContext().getCompany(
	 * )), mapp -> { return Helper.toVO(mapp);} ); return ResponseEntity.ok(res); }
	 */
	
	@RequestMapping(value = "/rs/getAllCountry", method = RequestMethod.GET)
	public ResponseEntity<List<Country>> create() throws Exception {
		try {
		List<Country> countries = masterService.getAllCountry();
		return ResponseEntity.ok(countries);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while getAllCountry", e);
//		}
	}

	@RequestMapping(value = "/rs/getseaportbycriteria", method = RequestMethod.POST)
	public ResponseEntity<List<Port>> searchSeaPort(@RequestBody PortSearchDTO searchDTO) throws Exception {
		try {
		List<Port> ports = masterService.getAllPorts(searchDTO.getCountryCode());
		return ResponseEntity.ok(ports);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching seaport", e);
//		}
	}

	@RequestMapping(value = "/rs/createSeaport", method = RequestMethod.POST)
	public ResponseEntity<PortVO> create(@RequestBody PortVO portDTO) throws Exception {
//		try {
//			PortDTO port = masterService.createPort(Helper.toDTO(portDTO));
//			return ResponseEntity.ok(Helper.toVO(port));
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating seaport", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/findSeaportLikeCode", method = RequestMethod.POST)
	public ResponseEntity<List<PortVO>> findSeaportLikeCode(@RequestBody String code) throws Exception {
		try {
		List<PortDTO> ports = masterService.getPortContaining(code);
		List<PortVO> portsVO = ports.stream()
                .map(pDto -> Helper.toVO(pDto))
                .collect(Collectors.toList());
			return ResponseEntity.ok(portsVO);
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		}
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching seaport", e);
//		}
	}

	@RequestMapping(value = "/rs/findSeaportByCode", method = RequestMethod.POST)
	public ResponseEntity<Optional<PortVO>> findSeaportByCode(@RequestBody String code) throws Exception {
		try {
			Optional<PortDTO> port = masterService.getPort(code);
			return ResponseEntity.ok(port.map(pDto -> Helper.toVO(pDto)));
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while loading seaport", e);
//		}
	}
	@RequestMapping(value = "/rs/updateSeaport", method = RequestMethod.PUT)
	public ResponseEntity<PortVO> updateSeaport(@RequestBody PortVO portDTO) throws Exception {
//		try {
//			PortDTO port = masterService.createPort(Helper.toDTO(portDTO));
//			return ResponseEntity.ok(Helper.toVO(port));
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while updating seaport", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
}
