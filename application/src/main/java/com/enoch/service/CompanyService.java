package com.enoch.service;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.dto.ShipDTO;


public interface CompanyService {

	public List<CompanyDTO> search(CompanySearchDTO  search);

	public CompanyDTO load(UUID id);
	
	public CompanyDTO create(CompanyDTO companyDTO) ;

	public boolean checkAssociation(CompanyDTO companyDTO,ShipDTO shipDTO);
	
}
