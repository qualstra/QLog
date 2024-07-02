package com.enoch.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.enoch.common.exception.ServiceException;
import com.enoch.role.dto.PrivSearchCriteria;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.utils.CollectionUtil;

public interface EntitlementService {

	public List<RoleDTO> getRoles(String userId) throws ServiceException;

	public List<PrivillegeDTO> getPrivileges(RoleDTO role) throws ServiceException;

	public Map<PrivillegeDTO, Boolean> getPrivileges(String user);

	public RoleDTO createRole(RoleDTO role);

	public void addPrivToRole(RoleDTO role, PrivillegeDTO... privs);
	
	public void removePrivToRole(RoleDTO role, PrivillegeDTO... privs);

	public void addPrivToUser(String userId, PrivillegeDTO... privs);

	public void addRoleToUser(String userId, RoleDTO... roles);

	public List<PrivillegeDTO> searchPrivilleges(PrivSearchCriteria crit);

	public List<RoleDTO> loadRolesForQualifier(String qualifier);
	
	public Optional<RoleDTO> loadRoleQualifier(String roleName,String qualifier);
	
	default public Map<PrivillegeDTO, Boolean> getAllPrivileges(String user) throws ServiceException {
		final Map<PrivillegeDTO, Boolean> res = new HashMap<PrivillegeDTO, Boolean>();
		getRoles(user).forEach(role -> {
			getPrivileges(role).forEach(priv -> {
				res.put(priv, true);
			});
		});
		res.putAll(getPrivileges(user));
		return res;
	}

	default public Boolean hasPermission(String userId, String permission) {
		Boolean res = getAllPrivileges(userId).get(new PrivillegeDTO(permission));
		return res != null ? res : false;
	}
}
