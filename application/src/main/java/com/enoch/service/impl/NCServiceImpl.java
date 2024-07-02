/**
 * 
 */
package com.enoch.service.impl;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.NCDAO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.exception.DataException;
import com.enoch.model.AuditTrail;
import com.enoch.service.EntitlementService;
import com.enoch.service.NCService;

/**
 * @author vijay
 *
 */
@Service
public class NCServiceImpl implements NCService {

	@Autowired
	NCDAO ncDAO;
	@Autowired
	EntitlementService secRepo;

	@Override
	public NCDTO createNC(NCDTO ncdto) {
		try {
			ncdto.setUUID(UUID.randomUUID());
			return ncDAO.createNC(ncdto);
		}catch (DataException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<NCDTO> loadNCSForChecklistInst(UUID chkInstId) {
		try {
			return ncDAO.loadNCSForChecklistInst(chkInstId);
		}catch (DataException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public List<NCDTO> loadNCSForChecklistInst() {
		try {
			secRepo.hasPermission(ApplicationContext.getContext().getUser().getUUID().toString(), "");
			return ncDAO.loadNCSForChecklistInst(ApplicationContext.getContext().getCompany());
		}catch (DataException e) {e.printStackTrace();
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public NCDTO save(NCDTO dto) {
		try {
			boolean bool = secRepo.hasPermission(ApplicationContext.getContext().getUser().getUUID().toString(), "nc.close");
			if(true || bool) {
			return ncDAO.save(dto);
			} else throw new ServiceException("User is not entitled to save NC");
		}catch (DataException e) {e.printStackTrace();
			throw new ServiceException(e.getMessage(),e);
		}
	}

	@Override
	public int clearNCSForChecklistInst(UUID chkInstId) {
		try {
			return ncDAO.clearNCSForChecklistInst(chkInstId);
		}catch (DataException e) {
			throw new ServiceException(e.getMessage(),e);
		}
	}

}
