package com.enoch.dao;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.nc.NCDTO;

public interface NCDAO {

	NCDTO createNC(NCDTO  ncDTO );

	List<NCDTO> loadNCSForChecklistInst(UUID chkInstId);

	List<NCDTO> loadNCSForChecklistInst(CompanyDTO company);

	NCDTO save(NCDTO dto);

	int clearNCSForChecklistInst(UUID chkInstId);

}
