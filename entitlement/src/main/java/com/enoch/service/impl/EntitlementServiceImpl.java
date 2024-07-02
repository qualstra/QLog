package com.enoch.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.common.exception.ServiceException;
import com.enoch.dao.RoleDAO;
import com.enoch.role.dto.PrivSearchCriteria;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.EntitlementService;

@Service("EntitlementService")
public class EntitlementServiceImpl implements EntitlementService {

	private static Logger logger = LoggerFactory.getLogger(EntitlementServiceImpl.class);
	@Autowired
	RoleDAO reopsitory;



	@Override
	public List<RoleDTO> getRoles(String userId) throws ServiceException {
		try {
			return reopsitory.loadAllRoles(userId);
		} catch (Exception e) {
			logger.error("Error loading Roles", e);
			throw new ServiceException(
					String.format("Error loading Roles for User [%s]", userId), e);
		}
	}

	@Override
	public List<PrivillegeDTO> getPrivileges(RoleDTO role) throws ServiceException {
		try {
			return reopsitory.loadPrivileges(role);
		} catch (Exception e) {
			logger.error("Error loading Privileges", e);
			throw new ServiceException(
					String.format("Error loading privleges for Role [%s] ", role.getName()), e);
		}
	}

	@Override
	public Map<PrivillegeDTO, Boolean> getPrivileges(String userId) {
		try {
			return reopsitory.loadPrivileges(userId);
		} catch (Exception e) {
			logger.error("Error loading Privileges", e);
			throw new ServiceException(
					String.format("Error loading privleges for User [%s] ", userId), e);
		}
	}


	@Override
	public RoleDTO createRole(RoleDTO role) {
		try {
			return reopsitory.createRole(role);
		} catch (Exception e) {
			logger.error("Error Creating role", e);
			throw new ServiceException(
					String.format("Error creating role [%s] ", role.getName()), e);
		}
	}

	@Override
	public void addPrivToRole(RoleDTO role, PrivillegeDTO... privs) {
		try {
			 reopsitory.addPrivToRole(role, privs); 
		} catch (Exception e) {
			logger.error("Error adding Privillege to role ", e);
			throw new ServiceException(
					String.format("Error adding Priv to role [%s] ", role.getName()), e);
		}

	}
	@Override
	public void removePrivToRole(RoleDTO role, PrivillegeDTO... privs) {
		try {
			 reopsitory.removePrivFromRole(role, privs); 
		} catch (Exception e) {
			logger.error("Error removing Privillege to role ", e);
			throw new ServiceException(
					String.format("Error removing Priv from role [%s] ", role.getName()), e);
		}
	}

	@Override
	public void addPrivToUser(String userId, PrivillegeDTO... privs) {
		try {
			 reopsitory.addPrivToUser(userId, privs); 
		} catch (Exception e) {
			logger.error("Error adding Privillege to User ", e);
			throw new ServiceException(
					String.format("Error adding Priv to User[%s] ", userId), e);
		}
	}

	@Override
	public void addRoleToUser(String userId, RoleDTO... roles) {
		try {
			 reopsitory.addRoleToUser(userId, roles); 
		} catch (Exception e) {
			logger.error("Error adding Role to User ", e);
			throw new ServiceException(
					String.format("Error adding Role to User[%s] ", userId), e);
		}
	}
	@Override
	public List<PrivillegeDTO> searchPrivilleges(PrivSearchCriteria crit) {
		try {
			 return reopsitory.searchPrivilleges(crit); 
		} catch (Exception e) {
			logger.error("Error adding Role to User ", e);
			throw new ServiceException(
					String.format("Error searching privillege] "), e);
		}
	}

	@Override
	public List<RoleDTO> loadRolesForQualifier(String qualifier) {
		try {
			 return reopsitory.loadRolesForQualifier(qualifier); 
		} catch (Exception e) {
			logger.error("Error adding Role to User ", e);
			throw new ServiceException(
					String.format("Error loading Roles for Qualifier[%s] ", qualifier), e);
		}
	}

	@Override
	public Optional<RoleDTO> loadRoleQualifier(String roleName, String qualifier) {
		try {
			 return reopsitory.loadRole(qualifier, roleName); 
		} catch (Exception e) {
			logger.error("Error adding Role to User ", e);
			throw new ServiceException(
					String.format("Error loading Roles for Qualifier[%s] ", qualifier), e);
		}
	}

}
