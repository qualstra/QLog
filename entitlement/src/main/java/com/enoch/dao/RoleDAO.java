package com.enoch.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.enoch.exception.DataException;
import com.enoch.role.dto.PrivSearchCriteria;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.role.dto.RolePrivillegeDTO;
import com.enoch.role.dto.UserPrivillegeDTO;

public interface RoleDAO {
	
	// CRUDS
	public Optional<PrivillegeDTO> loadPrivillege(String privName);
	public PrivillegeDTO createPrivillege(PrivillegeDTO priv) throws DataException;

	public Optional<RoleDTO> loadRole(String qualifier, String roleName);
	public RoleDTO createRole(RoleDTO role) throws DataException;

	public void updateRolePriv(RolePrivillegeDTO... rolePriv);
	public void updateUserPriv(UserPrivillegeDTO... rolePriv);

	// Assign to users/role
	public void addPrivToRole(RoleDTO role, PrivillegeDTO... priv);
	public void addPrivToUser(String userId, PrivillegeDTO... priv);
	public void removePrivFromRole(RoleDTO role, PrivillegeDTO... priv);
	public void removePrivFromUser(String userId, PrivillegeDTO... priv);

	// Assign to company/role
	public List<RoleDTO> loadRolesForQualifier(String userId);
	
	
	// Search Specific
	public List<PrivillegeDTO> loadPrivileges(RoleDTO role);
	public List<PrivillegeDTO> searchPrivilleges(PrivSearchCriteria crit);
	
	// User specific API
	public Map<PrivillegeDTO,Boolean> loadPrivileges(String userId);
	public List<RoleDTO> loadAllRoles(String userId);
	public void addRoleToUser(String userId, RoleDTO... roles);
	public void removeRoleFromUser(String userId, RoleDTO... roles);
	
}
