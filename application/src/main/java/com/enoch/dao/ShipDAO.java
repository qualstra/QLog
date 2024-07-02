package com.enoch.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;
import com.enoch.exception.DataException;
import com.querydsl.core.types.Predicate;

public interface ShipDAO {

	//create
	ShipDTO create(ShipDTO ship) throws DataException ;
	Optional<ShipDTO> loadShipByName(String name) throws DataException;
	
	List<ShipDTO> search(ShipSearchDTO search);
	
	List<ShipDTO> search(Predicate search);
	
//	ShipDTO load(UUID id);
	
}
