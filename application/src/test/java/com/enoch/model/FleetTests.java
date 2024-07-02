package com.enoch.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dto.RankDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.fleet.FleetDTO;
import com.enoch.dto.fleet.FleetShipDTO;
import com.enoch.exception.DataException;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;
import com.enoch.service.FleetService;
import com.enoch.service.UserService;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class FleetTests {

	@Autowired
	UserService uService;
	@Autowired
	FleetService fleetService;

	private static final Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
			"CITY", true, "it@enoch.com","", Helper.getAuditTrail());
	final CheckListDTO dto = new CheckListDTO(null, null, null, ChecklistType.SIMPLE, "Test", "Short Desc", "Long Desc",
			"remarks", "{}", comp.transform(), State.DRAFT, true, "None", "None", Helper.getAuditTrail());
	final SectionDTO secDTO = new SectionDTO(null, null, null, null, "SecName", "DESC", "LDESC", "{}", comp.transform(),
			State.DRAFT, true, "NOPRE", "NOPOST", Helper.getAuditTrail());
	final ShipDTO ship = new ShipDTO(1L, UUID.randomUUID(), "Test", "Test", "Test", "Test", "Test", "Test", "Test",
			comp.transform(), "Test", "Test@Test.com", new Date(), Helper.getAuditTrail());
	UserDTO user ;
	// write test cases here
	@Test
	public void testCreateFleet() throws DataException {

		 user = uService.create(
				new UserDTO(null, null, "VJ2@test.com", "$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG",
						"Vijay", "Jeyam", new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(),
						"CDC", "PASS5", "NAME", null, null, true, Helper.getAuditTrail()),
				comp.transform()).getUser();

		FleetDTO fleet = new FleetDTO();
		fleet.setName("Test Fleet");
		fleet.setFleetManager(user);
		fleet.setCompany(comp.transform());
		FleetDTO created = fleetService.create(fleet);
		System.out.println(created.getUuid());
		assert created.getUuid() != null : "Not able to save";
	}

	@Test
	public void testAddVessel() throws DataException {

		user = uService.load("VJ2@test.com");
		FleetDTO fleet = new FleetDTO();
		fleet.setName("Test Fleet1");
		fleet.setFleetManager(user);
		fleet.setCompany(comp.transform());
		FleetDTO created = fleetService.create(fleet);
		assert created.getUuid() != null : "Not able to save";
		
		created = fleetService.addVessel(created,ship);
		//assert created.getShips().size() == 1 : "Ship Not added";
	}

	@Test
	public void testAddVessel2() throws DataException {
		assertThrows(Exception.class, () -> {
		user = uService.load("VJ2@test.com");
		FleetDTO fleet = new FleetDTO();
		fleet.setName("Test Fleet");
		fleet.setFleetManager(user);
		fleet.setCompany(comp.transform());
		FleetDTO created = fleetService.create(fleet);
		assert created.getUuid() != null : "Not able to save";
		
		created = fleetService.addVessel(created,ship);
		//assert created.getShips().size() == 1 : "Ship Not added";
		fleetService.addVessel(created,ship);
	    });
		
	}
	@Test
	public void testRemove() throws DataException {
		user = uService.load("VJ2@test.com");
		FleetDTO fleet = fleetService.loadByName("Test Fleet1");
		/*
		 * for(FleetShipDTO fsDTO:fleet.getShips()) { fleet =
		 * fleetService.removeVessel(fleet,ship); }
		 */	}
	
	@Test
	public void testFreeShips() throws DataException {
		user = uService.load("VJ2@test.com");
		List<ShipDTO> ships = fleetService.loadUnassignedShips();
		assert ships.size() == 1 : "Ship Not loaded";
	}

}