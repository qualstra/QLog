package com.enoch.dao.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enoch.role.dto.RoleDTO;
import com.enoch.role.model.Privillege;
import com.enoch.role.model.Role;
import com.enoch.role.model.RolePrivillege;
import com.enoch.role.model.UserPrivillege;
import com.enoch.role.model.UserRole;

interface RoleRepo extends JpaRepository<Role, Long> {

	@Query("select rol from com.enoch.role.model.Role rol where rol.qualifier = ?1 and rol.name = ?2")
	public Optional<Role> loadRole(String qualifier, String roleName);


}

interface PrivRepo extends JpaRepository<Privillege, Long> {

	@Query("select priv from com.enoch.role.model.Privillege priv where priv.name = ?1")
	Optional<Privillege> loadPrivillege(String privName);

}

interface RolePrivRepo extends JpaRepository<RolePrivillege, Long> {

	@Query("select rp from com.enoch.role.model.RolePrivillege rp where rp.role = ?1")
	List<RolePrivillege> loadPrivilleges(Role role);

}

interface UserPrivRepo extends JpaRepository<UserPrivillege, Long> {

	@Query("select up from com.enoch.role.model.UserPrivillege up where up.userId = ?1")
	List<UserPrivillege> loadPrivilleges(String userId);

	@Query("select up from com.enoch.role.model.Role up where up.qualifier = ?1")
	List<Role> loadRolesForQualifier(String qualifier);

}

interface UserRoleRepo extends JpaRepository<UserRole, Long> {
	@Query("select urol from com.enoch.role.model.UserRole urol where urol.userId = ?1")
	public List<UserRole> loadAllRoles(String userId);
}