package com.enoch.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.ApplicationContext;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.service.CompanyService;
import com.enoch.service.RankService;
import com.enoch.service.UserService;

@DataJpaTest
@RunWith(SpringRunner.class)
public class UserTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private UserService service;
	@Autowired
	private CompanyService compSservice;
	@Autowired
	private RankService rankServ;
	@Test
	public void testUserCreate() throws DataException {
		Company comp = compSservice.create(new CompanyDTO(null, "T Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2",
				"CITY", true, "it@enoch.in","", Helper.getAuditTrail())).transform();
		
		rankServ.addRanks(comp.transform(), new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null));
		UserDTO user = service.create(new UserDTO(null, null, "VJ2@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay", "Jeyam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS5", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();

	}

	@Test
	public void testUserList() throws DataException {

		Company comp = new Company(1L, "T Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
				"it@enoch.in","", Helper.getAuditTrail());

		UserDTO user = service.create(new UserDTO(null, null, "VJ2@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay", "Jeyam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS6", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();

		UserDTO user2 = service.create(new UserDTO(null, null, "VJ3@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay", "Jeyam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS7", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();
		ApplicationContext.getContext().setUser(user);
		UserSearchDTO search = new UserSearchDTO();
		search.setFirstName("Vijay");
		List<CompanyUserDTO> users = service.search(search);

		assert users.size() == 3 : "Error searching user";
	}

	@Test
	public void testUserListLike() throws DataException {
		Company comp = new Company(1L, "T Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
				"it@it.com","", Helper.getAuditTrail());
		UserDTO user = service.create(new UserDTO(null, null, "VJ2@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay", "Jeyam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS3", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();

		UserDTO user2 = service.create(new UserDTO(null, null, "VJ3@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay", "Jeyam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS1", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();
		UserDTO user3 = service.create(new UserDTO(null, null, "VJ4@test.com",
				"$2a$10$Eho02HVc4s89SGCXcRHLUuHchavu9oRP1pN5kvp0qWLkMUDGbPVTG", "Vijay2", "Jeam",
				// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
				new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS2", "NAME", null,
				null, true, Helper.getAuditTrail()), comp.transform()).getUser();
		ApplicationContext.getContext().setUser(user);
		UserSearchDTO search = new UserSearchDTO();
		search.setFirstName("Vijay");
		search.setLastName("Jeyam");
		List<CompanyUserDTO> users = service.search(search);

		assert users.size() == 3 : "Error searching user";
	}

}
