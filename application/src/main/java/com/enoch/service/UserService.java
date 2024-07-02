package com.enoch.service;

import java.util.List;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.vo.UserPwdChangeVO;

public interface UserService {

	public CompanyUserDTO create(UserDTO vo,CompanyDTO compDTO) ;
	public UserDTO update(UserDTO vo) ;
	/**
	 * This is where the USer gets associated with company ship and rank
	 * @param compUser
	 * @return
	 */
	public CompanyUserDTO associateUser(CompanyUserDTO compUser) ;
	public List<CompanyUserDTO> search(UserSearchDTO search);
	public UserDTO load(String userName);
	public List<CompanyUserDTO> loadCompUser(String userName);
	public UserDTO updateConfirmPassword(UserDTO userdto, UserPwdChangeVO userVO);
	
	public List<UserDTO> loadUsers(RankDTO rank);
	
}

