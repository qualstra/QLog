package com.enoch.model;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.service.UserService;
import com.enoch.utils.CopyUtil;


@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class UserQuerydslTest {

	@Autowired
	private UserService repo;

	private UserDTO userJohn;
	private UserDTO  userTom;

	@Before
	public void init() {
		CompanyDTO comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
				"it@enoch.com","",Helper.getAuditTrail()).transform();

		userJohn = new UserDTO();
		userJohn.setUUID(UUID.randomUUID());
		userJohn.setFirstName("John");
		userJohn.setLastName("Doe");
		userJohn.setPassword("Doe");
		userJohn.setUserName("john@doe.com");
		userJohn.setRank(new RankDTO("CAP","CAP","DESCRIPTION",true,false,Helper.getAuditTrail()));
		userJohn.setAuditTrial(Helper.getAuditTrail());
		userJohn.setIsActive(true);
		repo.create(userJohn, comp);

		userTom = new UserDTO();
		userTom.setUUID(UUID.randomUUID());
		userTom.setFirstName("Tom");
		userTom.setLastName("Doe");
		userTom.setPassword("Doe");
		userTom.setIsActive(true);
			userTom.setUserName("tom@doe.com");
		userTom.setRank(new RankDTO("CAP","CAP","DESCRIPTION",true,false,Helper.getAuditTrail()));
		userTom.setAuditTrial(Helper.getAuditTrail());
		repo.create(userTom, comp);
	}
	
	@Test
	public void givenLast_whenGettingListOfUsers_thenCorrect() {

		UserSearchDTO dto = new UserSearchDTO();
		dto.setLastName("Doe");
	    List<UserDTO> results = CopyUtil.map(repo.search(dto),a -> a.getUser());
	    assertThat(results, containsInAnyOrder(userJohn, userTom));
	}
}