package com.enoch.service;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.nc.NCDTO;

public interface NCService {

	NCDTO createNC(NCDTO ncDTO);
	List<NCDTO> loadNCSForChecklistInst(UUID chkInstId);
	List<NCDTO> loadNCSForChecklistInst();
	int clearNCSForChecklistInst(UUID chkInstId);
	NCDTO save(NCDTO dto);
}
