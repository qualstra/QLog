package com.enoch.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.dao.VoyageDAO;
import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;
import com.enoch.model.Helper;
import com.enoch.service.VoyageService;

@Service
public class VoyageServiceImpl implements VoyageService {

	@Autowired
	VoyageDAO voyageDAO;
	
	@Override
	public VoyageDTO create(VoyageDTO dto) {
		try {
			dto.setAuditTrail(Helper.getAuditTrail());
			return voyageDAO.create(dto);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while create or update voyage", e);
		}
	}
	
	@Override
	public List<VoyageDTO> search(VoyageSearchDTO search){
		try {
			return voyageDAO.search(search);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while search voyage", e);
		}
	}
	
	@Override
	public VoyageDTO load(String voyageNo) {
		try {
			return voyageDAO.loadVoyageByName(voyageNo);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while load voyage", e);
		}
	}
}
