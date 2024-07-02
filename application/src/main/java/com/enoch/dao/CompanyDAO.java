package com.enoch.dao;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.exception.DataException;
import com.querydsl.core.types.Predicate;

public interface CompanyDAO {
	CompanyDTO create(CompanyDTO company)throws DataException;

	List<CompanyDTO> search(CompanySearchDTO search);
	
	List<CompanyDTO> search(Predicate search);
	

    CompanyDTO load(UUID id);
	//CompanyDTO load(String name) throws DataException;

}
