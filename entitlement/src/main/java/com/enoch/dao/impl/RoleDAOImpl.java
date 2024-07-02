package com.enoch.dao.impl;

import static com.enoch.utils.CopyUtil.map;
import static com.enoch.utils.CopyUtil.transform;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import com.enoch.dao.RoleDAO;
import com.enoch.exception.DataException;
import com.enoch.role.dto.PrivSearchCriteria;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.role.dto.RolePrivillegeDTO;
import com.enoch.role.dto.UserPrivillegeDTO;
import com.enoch.role.dto.UserRoleDTO;
import com.enoch.role.model.Privillege;
import com.enoch.role.model.Role;
import com.enoch.role.model.RolePrivillege;
import com.enoch.role.model.UserPrivillege;
import com.enoch.role.model.UserRole;
import com.enoch.utils.CollectionUtil;
import com.enoch.utils.CopyUtil;

@Repository("RoleRepository")
public class RoleDAOImpl implements RoleDAO {

	private static Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);
	@Autowired
	RoleRepo roleRepo;
	@Autowired
	PrivRepo privRepo;
	@Autowired
	RolePrivRepo rpRepo;
	@Autowired
	UserPrivRepo upRepo;
	@Autowired
	UserRoleRepo urRepo;

	@Override
	public Optional<PrivillegeDTO> loadPrivillege(String privName) {
		return privRepo.loadPrivillege(privName).map(a -> a.transform());
	}

	@Override
	public PrivillegeDTO createPrivillege(PrivillegeDTO priv) throws DataException {
		Privillege newPriv = priv.transform();// privRepo.findAll()
		try {
			return privRepo.saveAndFlush(newPriv).transform();
		} catch (DataIntegrityViolationException e) {
			logger.error("Error Saving Privillege", e);
			throw new DataException(String.format("Error Saving Privillege[%s,%s]", priv.getKey(), priv.getName()));
		}

	}

	@Override
	public Optional<RoleDTO> loadRole(String qualifier, String roleName) {
		return roleRepo.loadRole(qualifier, roleName).map(a -> a.transform());
	}

	@Override
	public RoleDTO createRole(RoleDTO role) throws DataException {
		Role newRole = role.transform();
		try {//roleRepo.findAll()
			return roleRepo.saveAndFlush(newRole).transform();
		} catch (DataIntegrityViolationException e) {
			logger.error("Error Saving Role", e);
			throw new DataException(String.format("Error Saving Role[%s,%s]", role.getQualifier(), role.getName()));
		}

	}

	@Override
	public void updateRolePriv(RolePrivillegeDTO... rolePriv) {
		for (RolePrivillegeDTO dto : rolePriv) {
			rpRepo.save(dto.transform());
		}
	}

	@Override
	public void updateUserPriv(UserPrivillegeDTO... rolePrivs) {
		for (UserPrivillegeDTO dto : rolePrivs) {
			upRepo.save(dto.transform());
		}
	}

	@Override
	public void addPrivToRole(RoleDTO role, PrivillegeDTO... privDTOs) {
		List<Privillege> privs = map(rpRepo.loadPrivilleges(role.transform()), a -> a.getPrivillege());
		for (PrivillegeDTO priv : privDTOs) {
			Optional<Privillege> dbPriv = CollectionUtil.find(privs, priv, (ths, tht) -> {
				String key1 = String.format("%s:%s", ths.getKey(), ths.getName());
				String key2 = String.format("%s:%s", tht.getKey(), tht.getName());
				return key1.equalsIgnoreCase(key2);
			});
			if (!dbPriv.isPresent()) {
				RolePrivillege dto = rpRepo.save(new RolePrivillegeDTO(role, priv).transform());
				privs.add(priv.transform());
			}
		}
	}

	@Override
	public void addPrivToUser(String userId, PrivillegeDTO... privDTOs) {
		List<Privillege> privs = map(upRepo.loadPrivilleges(userId), a -> a.getPrivillege());
		for (PrivillegeDTO priv : privDTOs) {
			Optional<Privillege> match = CollectionUtil.find(privs, priv, (a, b) -> {
				String key1 = String.format("%s:%s", a.getKey(), a.getName());
				String key2 = String.format("%s:%s", b.getKey(), b.getName());
				return key1.equalsIgnoreCase(key2);
			});

			if (!match.isPresent()) {
				upRepo.save(new UserPrivillegeDTO(userId, priv).transform());
				privs.add(priv.transform());
			}

		}

	}

	@Override
	public void removePrivFromRole(RoleDTO role, PrivillegeDTO... privDTOs) {
		List<RolePrivillege> privs = rpRepo.loadPrivilleges(role.transform());
		for (PrivillegeDTO priv : privDTOs) {
			Optional<RolePrivillege> match = CollectionUtil.find(privs, priv, (a, b) -> {
				String key1 = String.format("%s:%s", a.getPrivillege().getKey(), a.getPrivillege().getName());
				String key2 = String.format("%s:%s", b.getKey(), b.getName());
				return key1.equalsIgnoreCase(key2);
			});
			match.ifPresent(rolePriv -> rpRepo.delete(rolePriv));
		}
	}

	@Override
	public void removePrivFromUser(String userId, PrivillegeDTO... privDTOs) {

		List<UserPrivillege> privs = upRepo.loadPrivilleges(userId);
		for (PrivillegeDTO priv : privDTOs) {
			Optional<UserPrivillege> match = CollectionUtil.find(privs, priv, (a, b) -> {
				String key1 = String.format("%s:%s", a.getPrivillege().getKey(), a.getPrivillege().getName());
				String key2 = String.format("%s:%s", b.getKey(), b.getName());
				return key1.equalsIgnoreCase(key2);
			});
			match.ifPresent(userPriv -> upRepo.delete(userPriv));
		}
	}

	@Override
	public List<PrivillegeDTO> loadPrivileges(RoleDTO role) {
		return map(rpRepo.loadPrivilleges(role.transform()), a -> a.getPrivillege().transform());
	}
	@Override
	public List<PrivillegeDTO> searchPrivilleges(PrivSearchCriteria crit) {
		return transform(privRepo.findAll());
	}

	@Override
	public Map<PrivillegeDTO, Boolean> loadPrivileges(String userId) {
		return upRepo.loadPrivilleges(userId).stream().collect(Collectors.<UserPrivillege, PrivillegeDTO, Boolean>toMap(
				keyM -> keyM.getPrivillege().transform(), vMap -> vMap.isEnabled()));

	}

	public List<RoleDTO> loadAllRoles(String userId) {
		return map(urRepo.loadAllRoles(userId), ur -> ur.getRole().transform());
	}

	@Override
	public void addRoleToUser(String userId, RoleDTO... roles) {
		List<Role> dbRoles = map(urRepo.loadAllRoles(userId), a -> a.getRole());
		for (RoleDTO role : roles) {
			Optional<Role> match = CollectionUtil.find(dbRoles, role, (a, b) -> {
				String key1 = String.format("%s:%s", a.getQualifier(), a.getName());
				String key2 = String.format("%s:%s", b.getQualifier(), b.getName());
				return key1.equalsIgnoreCase(key2);
			});
			if (!match.isPresent()) {
				urRepo.save(new UserRoleDTO(userId, role).transform());
				dbRoles.add(role.transform());
			}
		}
	}

	@Override
	public void removeRoleFromUser(String userId, RoleDTO... roles) {
		List<UserRole> dbRoles = urRepo.loadAllRoles(userId);
		for (RoleDTO role : roles) {
			Optional<UserRole> match = CollectionUtil.find(dbRoles, role, (a, b) -> {
				String key1 = String.format("%s:%s", a.getRole().getQualifier(), a.getRole().getName());
				String key2 = String.format("%s:%s", b.getQualifier(), b.getName());
				return key1.equalsIgnoreCase(key2);
			});
			match.ifPresent(uRole -> {
				dbRoles.remove(uRole);
				urRepo.delete(uRole);
			});
		}
	}

	@Override
	public List<RoleDTO> loadRolesForQualifier(String qualifier) {
		try {
			return CopyUtil.transform(upRepo.loadRolesForQualifier(qualifier));
		}
		catch (Exception e) {
			logger.error("Error loading Roles for Qualifier",e);
			throw new ServiceException("Error loading Roles for Qualifier");
		}
	}

}
