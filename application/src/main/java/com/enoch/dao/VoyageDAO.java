package com.enoch.dao;

import java.util.List;

import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;
import com.enoch.exception.DataException;
import com.querydsl.core.types.Predicate;

public interface VoyageDAO {
	
	VoyageDTO create(VoyageDTO voyage) throws DataException;
	
	VoyageDTO loadVoyageByName(String voyageNo) throws DataException;
	
	List<VoyageDTO> search(VoyageSearchDTO search);
	
	List<VoyageDTO> search(Predicate search);
	
}
