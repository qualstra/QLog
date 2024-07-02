package com.enoch.dao;


import java.util.List;

import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.exception.DataException;
import com.querydsl.core.types.Predicate;

public interface UserDAO {

	UserDTO loadUserByName(String userName) throws DataException;
	UserDTO create(UserDTO user) throws DataException ;
	List<CompanyUserDTO> search(UserSearchDTO search);
	List<CompanyUserDTO> search(Predicate search);
	List<CompanyUserDTO> loadCompUser(String userName);
	List<CompanyUserDTO> search(CompanyUserDTO dto);
	CompanyUserDTO update(CompanyUserDTO compUser);
}
