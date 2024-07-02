package com.enoch.controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.enoch.common.exception.ServiceException;
import com.enoch.controller.CheckListInstController.ServiceUnavailableException;

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
import com.enoch.attachment.service.AttachmentService;
import com.enoch.dto.RankDTO;
import com.enoch.service.FleetService;
import com.enoch.service.RankService;
import com.enoch.service.UserService;
import com.enoch.service.client.AttributeClient;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.ShipVO;
import com.enoch.vo.UserVO;
import com.enoch.vo.fleet.FleetVO;

@RestController
@CrossOrigin
public class FleetController {
	@Autowired
	AttributeClient attribService;

	@Autowired
	AttachmentService attachService;

	@Autowired
	FleetService fleetService;
	
	
	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}

	@RequestMapping(value = "/rs/fleet/create", method = RequestMethod.POST)
	public ResponseEntity<FleetVO> create(@RequestBody FleetVO vo) throws Exception {
//		try {
//			return ResponseEntity.ok(Helper.toVO(fleetService.create(Helper.toDTO(vo))));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating fleet", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/fleet/{uuid}/addVessel", method = RequestMethod.POST)
	public ResponseEntity<FleetVO> addVessel(@RequestBody ShipVO vo,@PathVariable("uuid") UUID uuid) throws Exception {
//		try {
//			return ResponseEntity.ok(Helper.toVO(fleetService.addVessel(fleetService.loadByID(uuid), Helper.toDTO(vo))));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating fleet", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "/rs/fleet/{uuid}/removeVessel", method = RequestMethod.POST)
	public ResponseEntity<FleetVO> removeVessel(@RequestBody ShipVO vo,@PathVariable("uuid") UUID uuid) throws Exception {
//		try {
//			return ResponseEntity.ok(Helper.toVO(fleetService.removeVessel(fleetService.loadByID(uuid), Helper.toDTO(vo))));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating fleet", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "/rs/fleet/list", method = RequestMethod.POST)
	public ResponseEntity<List<FleetVO>> list() throws Exception {
//		try {
//			return ResponseEntity.ok(CopyUtil.map2(fleetService.listAll(), Helper::toVO));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing fleet", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/fleet/assigned/{id}", method = RequestMethod.POST)
	public ResponseEntity<List<ShipVO>> list(@PathVariable("id") UUID id) throws Exception {
//		try {
//			return ResponseEntity.ok(CopyUtil.map2(fleetService.listVessels(id), Helper::toVO));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing ship", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@RequestMapping(value = "/rs/fleet/unassigned", method = RequestMethod.POST)
	public ResponseEntity<List<ShipVO>> listUnAssigned() throws Exception {
//		try {
//			return ResponseEntity.ok(CopyUtil.map2(fleetService.loadUnassignedShips(), Helper::toVO));
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing ship", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

	@Autowired
	RankService rankService;
	@Autowired
	UserService userService;

	@RequestMapping(value = "/rs/fleet/manager", method = RequestMethod.POST)
	public ResponseEntity<List<UserVO>> listManagers() throws Exception {
//		Optional<RankDTO> rank = null;
//
//		try {
//			final String roleName = attribService.getStringAttribute(ApplicationContext.getContext().getCompany().getUUID(),
//					"FM");
//			rank = rankService.getRanks(ApplicationContext.getContext().getCompany()).stream()
//					.filter(rankfound -> rankfound.getName().equalsIgnoreCase(roleName)).findAny();
//			if (rank.isPresent()) {
//				return ResponseEntity.ok(CopyUtil.map2(userService.loadUsers(rank.get()), Helper::toVO));
//			}
//
//		} catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch (Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing fleet manager", e);
////		}
//
//		throw new ServiceException("FM Rank not found");
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}

}
