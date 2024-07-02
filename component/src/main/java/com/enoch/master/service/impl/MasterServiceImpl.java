package com.enoch.master.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.common.exception.ServiceException;
import com.enoch.master.dao.MasterDAO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;
import com.enoch.master.service.MasterService;

@Service
public class MasterServiceImpl implements MasterService {

	@Autowired
	MasterDAO masterDao;

	@Override
	public List<Country> getAllCountry() {
		return masterDao.getAllCountry();
	}

	@Override
	public List<Port> getAllPorts(String countryCode) {
		return masterDao.getAllPorts(countryCode);
	}

	@Override
	public PortDTO createPort(PortDTO portDTO) {
		try {
			return masterDao.savePort(portDTO);
		} catch (Exception e) {
			throw new ServiceException("Error creating port", e);
		}
	}

	@Override
	public Optional<PortDTO> getPort(String portCode) {
		try {
			return masterDao.getPort(portCode);
		} catch (Exception e) {
			throw new ServiceException("Error creating port", e);
		}
	}
	@Override
	public List<PortDTO> getPortContaining(String portCode) {
		try {
			return masterDao.getPortContaining(portCode);
		} catch (Exception e) {
			throw new ServiceException("Error creating port", e);
		}
	}
}
