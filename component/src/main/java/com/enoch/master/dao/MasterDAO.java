package com.enoch.master.dao;

import java.util.List;
import java.util.Optional;

import com.enoch.master.dto.CountryDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;


public interface MasterDAO {

	List<Country> getAllCountry();

	public CountryDTO saveCountry(CountryDTO countryDTO);


	PortDTO savePort(PortDTO dto);

	List<Port> getAllPorts(String countryCode);
	List<PortDTO> getPortContaining(String portCode);
	Optional<PortDTO> getPort(String portCode);
}
