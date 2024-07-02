package com.enoch.test.role;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.dao.RoleDAO;
import com.enoch.exception.DataException;
import com.enoch.role.dto.PrivillegeDTO;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class PrivillegeTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoleDAO roleRepository;

	// write test cases here
	@Test
	public void testPrivCreate() throws DataException {
		PrivillegeDTO dto = new PrivillegeDTO(null, "key", "Test priv", true, false);
		roleRepository.createPrivillege(dto);
	}
	@Test
	public void testPrivCreateDuplicate() throws DataException {
		PrivillegeDTO dto = new PrivillegeDTO(null, "key", "Test priv", true, false);
		roleRepository.createPrivillege(dto);
		assertThrows(DataException.class, () -> {
			dto.setId(null);
			roleRepository.createPrivillege(dto);	
		} );
	}
}