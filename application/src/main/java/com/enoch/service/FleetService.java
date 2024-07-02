package com.enoch.service;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.ShipDTO;
import com.enoch.dto.fleet.FleetDTO;

public interface FleetService {

	FleetDTO create(FleetDTO dto);

	FleetDTO addVessel(FleetDTO fleet, ShipDTO ship);

	FleetDTO removeVessel(FleetDTO created, ShipDTO ship);

	FleetDTO loadByName(String string);
	FleetDTO loadByID(UUID uid);

	List<ShipDTO> loadUnassignedShips();

	List<FleetDTO> listAll();

	List<ShipDTO> listVessels(UUID id);

}
