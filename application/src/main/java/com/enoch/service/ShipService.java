package com.enoch.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;

public interface ShipService {
	
	public ShipDTO create(ShipDTO vo);
	
	public List<ShipDTO> search(ShipSearchDTO search);
	
	public Optional<ShipDTO> load(String name);
	
//	public ShipDTO load(UUID id);
	

}
