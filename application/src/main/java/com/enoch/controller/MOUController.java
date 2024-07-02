package com.enoch.controller;

import static com.enoch.controller.Helper.toDTO;

import java.util.List;
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

import com.enoch.dto.MouDTO;
import com.enoch.service.MOUService;
import com.enoch.vo.MouVO;
@RestController
@CrossOrigin
public class MOUController {

	@Autowired
	MOUService mouService;

	@RequestMapping(value = "/rs/createMOU", method = RequestMethod.POST)
	public ResponseEntity<?> create(@RequestBody MouVO mouVo) throws Exception {
		try {
		mouService.create(toDTO(mouVo));
		return ResponseEntity.ok().build();
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
			}
//		catch (Exception e) {
//				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while creating", e);
//				}
		}

	@RequestMapping(value = "/rs/searchmou", method = RequestMethod.POST)
	public ResponseEntity<List<MouDTO>> search() throws Exception {
		try {
		List<MouDTO> mous = mouService.search();
		return ResponseEntity.ok().build();
		}catch (ServiceException e) {
			throw new ServiceException("business error", e);
			}
//		catch (Exception e) {
//				throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while searching", e);
//				}
		}
}
