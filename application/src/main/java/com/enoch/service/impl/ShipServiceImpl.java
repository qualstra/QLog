package com.enoch.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.dao.ShipDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;
import com.enoch.events.CheckListEvent;
import com.enoch.events.VesselEvent;
import com.enoch.events.publisher.CheckListInstEventPublisher;
import com.enoch.events.publisher.VesselEventPublisher;
import com.enoch.exception.DataException;
import com.enoch.model.Helper;
import com.enoch.service.ShipService;

@Service
public class ShipServiceImpl implements ShipService {

	@Autowired
	ShipDAO shipDAO;
	@Autowired
	VesselEventPublisher vesselPub;

	@Override
	public ShipDTO create(ShipDTO dto) {
		try {
			boolean publishEvent = false;
			if (dto.getUUID() == null) {
				dto.setUUID(UUID.randomUUID());
				publishEvent = true;
			}
			dto.setAuditTrial(Helper.getAuditTrail());
			dto = shipDAO.create(dto);
			if(publishEvent)
				vesselPub.publishEvent(new VesselEvent(dto));
			return dto;
		} catch (Exception e) {
			throw new ServiceException("error while create or update ship", e);
		}
	}

	@Override
	public List<ShipDTO> search(ShipSearchDTO search) {
		try {
			return shipDAO.search(search);
		} catch (Exception e) {
			throw new ServiceException("error while search ship", e);
		}
	}

	@Override
	public Optional<ShipDTO> load(String name) {
		try {
			return shipDAO.loadShipByName(name);
		} catch (Exception e) {
			throw new ServiceException("error while load ship", e);
		}
	}

//	@Override
//	public ShipDTO load(UUID id) {
//		try {
//			return shipDAO.load(id);
//		} catch (DataException ex) {
//			throw new ServiceException(ex.getMessage());
//		}
//	}
	
}