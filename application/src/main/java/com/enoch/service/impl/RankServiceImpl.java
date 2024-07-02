package com.enoch.service.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.RankDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.RankDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Helper;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.EntitlementService;
import com.enoch.service.RankService;

@Service
public class RankServiceImpl implements RankService {
	protected final Log logger = LogFactory.getLog(RankServiceImpl.class);

	@Autowired
	RankDAO rankDAO;
	
	@Autowired
	EntitlementService entitlementService;

	@Override
	public List<RankDTO> getRanks(CompanyDTO comp) {
		try {
			return rankDAO.getRanks(comp);
		} catch (DataException e) {
			logger.error("Error retriving ranks", e);
			throw new ServiceException("Error retriving ranks");
		}
	}
	@Override
	public List<RankDTO> searchRanks(RankDTO dto) {
		try {
			return rankDAO.searchRanks(ApplicationContext.getContext().getCompany(),dto);
		} catch (DataException e) {
			logger.error("Error retriving ranks", e);
			throw new ServiceException("Error retriving ranks");
		}
	}


	@Override
	public List<RankDTO> addRanks(CompanyDTO comp, RankDTO... dtos) {
		try {			
			String compID = comp.getId().toString();
			for(RankDTO dto:dtos) {
				dto.setAuditTrail(Helper.getAuditTrail());
				entitlementService.createRole(
						new RoleDTO(null,compID,
								"RANK_"+dto.getName(),
								dto.getCode(),
								dto.getDescription(),
								dto.getIsAvailable(),false));
				
			}
			
			return rankDAO.addRanks(comp, dtos);
		} catch (DataException e) {
			logger.error("Adding ranks", e);
			throw new ServiceException("Error retriving ranks");
		}
	}

}
