package com.enoch.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.FleetDAO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.fleet.FleetDTO;
import com.enoch.dto.fleet.FleetShipDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Helper;
import com.enoch.service.FleetService;

@Service
public class FleetServiceImpl implements FleetService {
	protected final Log logger = LogFactory.getLog(FleetServiceImpl.class);
	@Autowired
	FleetDAO dao;

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public FleetDTO create(FleetDTO dto) {

		try {
			dto.setAuditTrail(Helper.getAuditTrail());
			if (dto.getUuid() == null) {
				dto.setUuid(UUID.randomUUID());
			}
			if (dto.getFleetManager() == null) {
				throw new ServiceException("Fleet Manager cannot be null");
			}
			if (dto.getCompany() == null) {
				dto.setCompany(ApplicationContext.getContext().getCompany());
			}
			return dao.create(dto);
		} catch (DataException e) {
			logger.error("Error Creating fleet", e);
			throw new ServiceException("Error Creating fleet");
		}
	}

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public FleetDTO addVessel(FleetDTO fleet, ShipDTO ship) {

		try {

			if (fleet == null) {
				throw new ServiceException("Cannot add ship to null fleet");
			}
			if (ship == null) {
				throw new ServiceException("Cannot add null ship to fleet");
			}

			if (!fleet.getCompany().getUUID().equals(ship.getCompany().getUUID())) {
				throw new ServiceException("Fleet and ship should belong to the same company");
			}
			FleetShipDTO fs = dao.addVessel(fleet, ship);
			return fleet;
		} catch (DataException e) {
			logger.error("Error Creating fleet", e);
			throw new ServiceException("Error Creating fleet");
		}
	}

	@Override
	public FleetDTO removeVessel(FleetDTO fleet, ShipDTO ship) {

		try {

			if (fleet == null) {
				throw new ServiceException("Cannot add ship to null fleet");
			}
			if (ship == null) {
				throw new ServiceException("Cannot add null ship to fleet");
			}

			if (!fleet.getCompany().getUUID().equals(ship.getCompany().getUUID())) {
				throw new ServiceException("Fleet and ship should belong to the same company");
			}
			FleetShipDTO fs = dao.removeVessel(fleet, ship);
			return fleet;
		} catch (DataException e) {
			logger.error("Error Creating fleet", e);
			throw new ServiceException("Error Creating fleet");
		}
	}

	@Override
	public FleetDTO loadByName(String fleetName) {
		try {

			if (fleetName == null) {
				throw new ServiceException("Empty fleet name to load");
			}
			return dao.loadByName(ApplicationContext.getContext().getCompany(), fleetName);
		} catch (DataException e) {
			logger.error("Error Creating fleet", e);
			throw new ServiceException("Error loading fleet");
		}
	}

	@Override
	public List<ShipDTO> loadUnassignedShips() {
		try {
			return dao.loadUnassignedShips(ApplicationContext.getContext().getCompany());
		} catch (DataException e) {
			logger.error("Error loading unassigned ships", e);
			throw new ServiceException("Error loading unassigned ships");
		}
	}

	@Override
	public List<FleetDTO> listAll() {
		try {
			return dao.listAll(ApplicationContext.getContext().getCompany());
		} catch (DataException e) {
			logger.error("Error listing fleets", e);
			throw new ServiceException("Error listing fleets");
		}
	}
	@Override
	public List<ShipDTO> listVessels(UUID uid) {
		try {
			return dao.listVessels(ApplicationContext.getContext().getCompany(),uid);
		} catch (DataException e) {
			logger.error("Error listing fleets", e);
			throw new ServiceException("Error listing fleets");
		}
	}

	@Override
	public FleetDTO loadByID(UUID uid) {
		try {

			if (uid == null) {
				throw new ServiceException("Empty fleet to load");
			}
			return dao.loadByID(ApplicationContext.getContext().getCompany(),uid);
		} catch (DataException e) {
			logger.error("Error loading fleet", e);
			throw new ServiceException("Error loading fleet");
		}
	}

}
