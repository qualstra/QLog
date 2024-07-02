package com.enoch.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.CompanyDAO;
import com.enoch.dao.RoleDAO;
import com.enoch.dao.ShipDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.UserDTO;
import com.enoch.exception.DataException;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.CompanyService;
import com.enoch.service.Helper;
import com.enoch.service.RankService;
import com.enoch.service.UserService;

@Service
public class CompanyServiceImpl implements CompanyService {
	protected final Log logger = LogFactory.getLog(CompanyServiceImpl.class);
	@Autowired
	CompanyDAO compDAO;
	@Autowired
	ShipDAO shipDAO;

	@Autowired
	UserService userService;
	@Autowired
	RankService rankService;

	public List<CompanyDTO> search(CompanySearchDTO search) {
		try {

			return compDAO.search(search);
		} catch (DataException e) {
			throw new ServiceException(e.getMessage());
		}
	}

	@Override
	public CompanyDTO load(UUID id) {
		try {
			return compDAO.load(id);
		} catch (DataException ex) {
			throw new ServiceException(ex.getMessage());
		}
	}

	@Override
	public CompanyDTO create(CompanyDTO companyDTO) {
		CompanyDTO successFullComp = null;
		boolean create = companyDTO.getId() == null;
		try {
			if (create) {
				companyDTO.setUUID(UUID.randomUUID());
				companyDTO.setAuditTrail(Helper.getAuditTrail());
			}
			successFullComp = compDAO.create(companyDTO);

			if (create) {
				addDefaultRanks(successFullComp);
				createITAdmin(successFullComp);
			}
			return successFullComp;
		} catch (Exception e) {
			throw new ServiceException("error while creating company", e);
		}
	}

	private void addDefaultRanks(CompanyDTO successFullComp) {
		List<RankDTO> ranks = rankService.addRanks(successFullComp, RankService.ITADMIN);
		ranks.forEach(rank ->{
			String roleName = String.format("RANK_%s", rank.getName());
			Optional<RoleDTO> optrole = roleDAO.loadRole(
					ApplicationContext.getContext().getCompany().getId()+"", roleName);
			optrole.ifPresent(role -> {
				final List<PrivillegeDTO> originalPrivs =  roleDAO.loadPrivileges(role);
				roleDAO.loadRole(successFullComp.getId()+"",roleName).ifPresent(addedRole -> {
					roleDAO.addPrivToRole(addedRole, originalPrivs.toArray(new PrivillegeDTO[0]));	
				});
				
			});
		});
	}
	@Autowired
	RoleDAO roleDAO;
	private CompanyUserDTO createITAdmin(CompanyDTO successFullComp) {
		UserDTO itAdmin = new UserDTO(null, null, successFullComp.getItAdminUser(),
				"welcome", "IT", "ADMIN", Helper.getITAdminRank(),
				new Date(), "CHANGEIT", successFullComp.getItAdminUser(), "CHANGEIT", null, null, true,
				Helper.getAuditTrail());
		CompanyUserDTO companyUserDTO = userService.create(itAdmin, successFullComp);
		companyUserDTO.setRank(Helper.getITAdminRank());
		return userService.associateUser(companyUserDTO);
	}

	@Override
	public boolean checkAssociation(CompanyDTO companyDTO, ShipDTO shipDTO) {
		logger.debug(
				String.format("Checking Associating %s Company to %s Ship", companyDTO.getName(), shipDTO.getName()));
		try {
			return shipDAO.loadShipByName(shipDTO.getName())
					.map(mapper -> mapper.getCompany().equals(companyDTO))
					.orElse(false);
		} catch (DataException e) {
			logger.error(e);
			throw new ServiceException("Error checking association", e);
		}

	}

}
