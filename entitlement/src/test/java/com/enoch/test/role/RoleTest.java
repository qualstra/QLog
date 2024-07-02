package com.enoch.test.role;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.RoleApplication;
import com.enoch.dao.RoleDAO;
import com.enoch.exception.DataException;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
public class RoleTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private RoleDAO roleRepository;

	private RoleDTO role = new RoleDTO(null, "qualifier", "Test Role", "Test Role", "Test Role Desc", true, false);
	// write test cases here
	@Test
	public void testRoleCreate() throws DataException {
		RoleDTO dto = role;
		roleRepository.createRole(dto);
	}

	@Test
	public void testRoleCreateDuplicate() throws DataException {
		RoleDTO dto = role;
		roleRepository.createRole(dto);
		assertThrows(DataException.class, () -> {
			dto.setId(null);
			roleRepository.createRole(dto);
		});
	}

	@Test
	public void testAddPrivRole() throws DataException {
		RoleDTO roleDto = role;
		roleDto = roleRepository.createRole(roleDto);
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		assert roleRepository.loadPrivileges(roleDto).size() == 0 : "Already privillege attached to role";

		roleRepository.addPrivToRole(roleDto, priv);

		assert roleRepository.loadPrivileges(roleDto).size() == 1 : "privillege not attached to role";

	}

	public void testRemovePrivRole() throws DataException {

		RoleDTO roleDto = role;
		roleDto = roleRepository.createRole(roleDto);
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		roleRepository.addPrivToRole(roleDto, priv);

		assert roleRepository.loadPrivileges(roleDto).size() == 1 : "privillege not attached to role";

		roleRepository.removePrivFromRole(roleDto, priv);

		assert roleRepository.loadPrivileges(roleDto).size() == 0 : "Not able to unmap privillege";
	}

	public void testDuplicatePrivRole() throws DataException {
		RoleDTO roleDto = role;
		roleDto = roleRepository.createRole(roleDto);
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		roleRepository.addPrivToRole(roleDto, priv, priv);

		assert roleRepository.loadPrivileges(roleDto).size() == 1 : "Duplicate privillege role";

	}

	@Test
	public void testAddPrivUser() throws DataException {
		String userId = "TU1";
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		assert roleRepository.loadPrivileges(userId).size() == 0 : "Already privillege attached to user";

		roleRepository.addPrivToUser(userId, priv);

		assert roleRepository.loadPrivileges(userId).size() == 1 : "privillege not attached to role";
	}

	@Test
	public void testRemovePrivUser() throws DataException {
		String userId = "TU1";
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		roleRepository.addPrivToUser(userId, priv);

		assert roleRepository.loadPrivileges(userId).size() == 1 : "privillege not attached to role";

		roleRepository.removePrivFromUser(userId, priv);

		assert roleRepository.loadPrivileges(userId).size() == 0 : "Not able to unmap privillege";

	}

	@Test
	public void testDupPrivUser() throws DataException {
		String userId = "TU1";
		PrivillegeDTO priv = new PrivillegeDTO(null, "key", "Test priv", true, false);
		priv = roleRepository.createPrivillege(priv);

		roleRepository.addPrivToUser(userId, priv, priv);

		assert roleRepository.loadPrivileges(userId).size() == 1 : "Duplicate privillege role";

	}

	@Test
	public void testAddRoleUser() throws DataException {
		String userId = "TU1";
		RoleDTO roleDTO = new RoleDTO(null, "key", "Test Role", "Test Role", "Test Role", true, false);
		roleDTO = roleRepository.createRole(roleDTO);
		assert roleRepository.loadAllRoles(userId).size() == 0 : "Already role attached to user";
		roleRepository.addRoleToUser(userId, roleDTO);
		assert roleRepository.loadAllRoles(userId).size() == 1 : "role not attached to role";

	}

	@Test
	public void testRemRoleUser() throws DataException {
		String userId = "TU1";
		RoleDTO roleDTO = new RoleDTO(null, "key", "Test priv", "Test Role", "Test Role", true, false);
		roleDTO = roleRepository.createRole(roleDTO);
		roleRepository.addRoleToUser(userId, roleDTO);
		roleRepository.removeRoleFromUser(userId, roleDTO);
		assert roleRepository.loadAllRoles(userId).size() == 0 : "Not able to unmap role";

	}

	@Test
	public void testDupRoleUser() throws DataException {
		String userId = "TU1";
		RoleDTO roleDTO = new RoleDTO(null, "key", "Test priv", "Test Role", "Test Role", true, false);
		roleDTO = roleRepository.createRole(roleDTO);
		roleRepository.addRoleToUser(userId, roleDTO, roleDTO);
		assert roleRepository.loadAllRoles(userId).size() == 1 : "Duplicate privillege role";

	}

}
