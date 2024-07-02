package com.enoch.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.enoch.common.exception.ServiceException;

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

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;
import com.enoch.service.ShipService;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.ShipVO;

@RestController
@CrossOrigin
public class ShipController {

	@Autowired
	AttachmentService attachService;

	@Autowired
	ShipService shipService;

	@RequestMapping(value = "/rs/createMasterVessel", method = RequestMethod.POST)
	public ResponseEntity<ShipVO> createMasterVessel(@RequestBody ShipVO vo) throws Exception {
		try {
			shipService.create(Helper.toDTO(vo));
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating ship", e);
//		}
	}

	private void populateProfileAttachment(ShipDTO dto, final ShipVO vo) {
		try {
			List<AttachmentDTO> attachments = attachService.getAll(dto);
			attachments.stream().filter(a -> a.getSecondaryId().equalsIgnoreCase("PROFILE")).findAny()
					.ifPresent(a -> vo.setProlileAttachment(a));
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while populateProfileAttachment",
//					e);
//		}
	}

	@RequestMapping(value = "/rs/findMasterVesselById", method = RequestMethod.POST)
	public ResponseEntity<ShipVO> findMasterVesselById(@RequestBody ShipVO shipVo) throws Exception {
		try {
			Optional<ShipVO> ship = shipService.load(shipVo.getName()).map(dto -> {

				ShipVO vo = Helper.toVO(dto);
				populateProfileAttachment(dto, vo);
				return vo;
			});

			if (ship.isPresent()) {
				return ResponseEntity.ok(ship.get());
			} else {
				return ResponseEntity.notFound().build();
			}
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching ship", e);
//		}
	}

	@RequestMapping(value = "/rs/searchMastVessel", method = RequestMethod.POST)
	public ResponseEntity<List<ShipVO>> search(@RequestBody ShipVO shipVo) throws Exception {
		try {
			ShipSearchDTO sdto = Helper.toSearchDTO(shipVo);
			List<ShipVO> vos = new ArrayList<>();
			shipService.search(sdto).forEach(dto -> {
				vos.add(Helper.toVO(dto));
			});
			return ResponseEntity.ok(vos);
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while listing ship", e);
//		}
	}

	@RequestMapping(value = "/rs/updateMasterVessel/{id}", method = RequestMethod.PUT)
	public ResponseEntity<ShipVO> update(@RequestBody ShipVO vo) throws Exception {
		try {
			shipService.create(Helper.toDTO(vo));
			return ResponseEntity.ok().build();
		} catch (ServiceException e) {
			throw new ServiceException("business error", e);
		} 
//		catch (Exception e) {
//			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while updating ship", e);
//		}
	}

}
