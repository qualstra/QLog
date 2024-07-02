package com.enoch.controller;

import java.util.ArrayList;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import com.enoch.common.exception.ServiceException;
import com.enoch.controller.CheckListInstController.ServiceUnavailableException;
import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;
import com.enoch.events.VoyageEvent;
import com.enoch.service.VoyageService;
import com.enoch.vo.VoyageSearchVO;
import com.enoch.vo.VoyageVO;

@RestController
@CrossOrigin
public class VoyageController {

	@Autowired
	VoyageService service;
	@Autowired
    private ApplicationEventPublisher applicationEventPublisher;
	
	
	public class ServiceUnavailableException extends RuntimeException {
	    public ServiceUnavailableException(String message) {
	        super(message);
	    }
	}
	
	@RequestMapping(value = "/rs/createVoyage", method = RequestMethod.POST)
	public ResponseEntity<VoyageVO> create(@RequestBody VoyageVO vo) throws Exception{
//		try {
//			VoyageDTO voyage = service.create(Helper.toDTO(vo));
//			applicationEventPublisher.publishEvent(new VoyageEvent(voyage));
//			return ResponseEntity.ok(Helper.toVO(voyage));
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch(Exception e) {e.printStackTrace();
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating voyage", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "/rs/findVoyageListById", method = RequestMethod.POST)
	public ResponseEntity<VoyageVO> findVoyageListById(@RequestBody VoyageVO vo) throws Exception{
//		try {
//			return ResponseEntity.ok(Helper.toVO(service.load(vo.getVoyageNo())));
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch(Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while loading voyage", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
	@RequestMapping(value = "/rs/updateVoyage/{id}", method = RequestMethod.PUT)
	public ResponseEntity<VoyageVO> update(@RequestBody VoyageVO vo) throws Exception {
//			try {
//				return ResponseEntity.ok(Helper.toVO(service.create(Helper.toDTO(vo))));
//			}catch (ServiceException e) {
//				throw new ServiceException("business error", e);
//			}
////			catch(Exception e) {e.printStackTrace();
////					throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while updating voyage", e);
////				}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

			}
	
	@RequestMapping(value = "/rs/searchVoyageByCriteria", method = RequestMethod.POST)
	public ResponseEntity<List<VoyageVO>> search(@RequestBody VoyageSearchVO vo) throws Exception {
//		try {
//			VoyageSearchDTO sdto = Helper.toSearchDTO(vo);
//			List<VoyageVO> vos = new ArrayList<>();
//			service.search(sdto).forEach(dto -> {
//				vos.add(Helper.toVO(dto));
//			});
//			return ResponseEntity.ok(vos);
//		}catch (ServiceException e) {
//			throw new ServiceException("business error", e);
//		} 
////		catch(Exception e) {
////			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching voyage", e);
////		}
        throw new ServiceUnavailableException("Service is unavailable for QLOG");

	}
	
}