package com.enoch.master.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.enoch.exception.DataException;
import com.enoch.master.dao.MasterDAO;
import com.enoch.master.dto.CountryDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;


@Service
public class MasterDAOImpl implements MasterDAO {
@Autowired
CountryRepository countryRepository;
@Autowired
PortRepository portRepo; 
	@Override
	public List<Country> getAllCountry() {
		return countryRepository.findAll();
	}
	@Override
	public CountryDTO saveCountry(CountryDTO countryDTO) {
		return countryRepository.save(countryDTO.transform()).transform();
		
	}
	@Override
	public List<Port> getAllPorts(String countryCode) {
		return portRepo.getAllPorts(countryCode);
	}
	@Override
	public PortDTO savePort(PortDTO dto) {
		return portRepo.save(dto.transform()).transform();
	}
	@Override
	public Optional<PortDTO> getPort(String portCode) {
		try {
			return portRepo.findByCode(portCode).map(some -> some.transform());
		}catch (Exception e) {
				throw new DataException("Error loading port " + portCode,e);
		}
	}
	@Override
	public List<PortDTO> getPortContaining(String portCode) {
	    try {
	        List<Port> ports = portRepo.findByCodeContaining(portCode);
	        return ports.stream()
	                    .map(Port::transform)
	                    .collect(Collectors.toList());
	    } catch (Exception e) {
	        throw new DataException("Error loading ports with code " + portCode, e);
	    }
	}


}


interface PortRepository extends JpaRepository<Port, String> {
	@Query("select port from com.enoch.master.model.Port port where port.country.code=?1")
	List<Port> getAllPorts(String countryCode);
	List<Port> findByCodeContaining(String portCode);
	Optional<Port> findByCode(String code);
	
}
interface CountryRepository extends JpaRepository<Country, String> {}

