package com.enoch.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dto.ShipDTO;
import com.enoch.service.ShipService;

@DataJpaTest
@RunWith(SpringRunner.class)
public class ShipTest {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired 
	private ShipService service;
	
	@Test
	public void testShipCreate() throws DataException {
		
		Company comp = new Company(2L, "T Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
				"it@enoch.in","",Helper.getAuditTrail());
		
		ShipDTO ship = service.create(new ShipDTO(null,null,"navanee","imo","vesseltype","class",
				"flag","grt","callsign",comp.transform(),"status","email",new Date(),Helper.getAuditTrail()));

	}

}
