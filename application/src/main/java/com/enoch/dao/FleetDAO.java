package com.enoch.dao;

import java.util.List;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.fleet.FleetDTO;
import com.enoch.dto.fleet.FleetShipDTO;

public interface FleetDAO {

	FleetDTO create(FleetDTO dto);

	FleetShipDTO addVessel(FleetDTO fleet, ShipDTO ship);

	FleetShipDTO removeVessel(FleetDTO fleet, ShipDTO ship);

	List<FleetShipDTO> getFleetShips(FleetDTO fleet);

	FleetDTO loadByName(CompanyDTO comp, String fleetName);

	List<ShipDTO> loadUnassignedShips(CompanyDTO company);

	List<FleetDTO> listAll(CompanyDTO company);

	List<ShipDTO> listVessels(CompanyDTO company, UUID uid);

	FleetDTO loadByID(CompanyDTO comp, UUID uid);

}
