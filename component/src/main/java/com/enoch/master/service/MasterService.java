package com.enoch.master.service;

import java.util.List;
import java.util.Optional;

import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;


public interface MasterService {

	List<Country> getAllCountry();

	

	
	PortDTO createPort(PortDTO portDTO);

	List<Port> getAllPorts(String countryCode);
	List<PortDTO> getPortContaining(String portCode);
	Optional<PortDTO> getPort(String portCode);
	
	
	
}
