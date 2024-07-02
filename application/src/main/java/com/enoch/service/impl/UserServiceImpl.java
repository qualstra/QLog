package com.enoch.service.impl;

import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.UserDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.exception.DataException;
import com.enoch.model.Helper;
import com.enoch.service.RankService;
import com.enoch.service.UserService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.UserPwdChangeVO;

@Service
public class UserServiceImpl implements UserService {
	protected final Log logger = LogFactory.getLog(UserService.class);

	@Autowired
	UserDAO userDAO;
	@Autowired
	RankService rankService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional(value = TxType.REQUIRES_NEW)
	public CompanyUserDTO create(UserDTO dto,CompanyDTO compDTO) {
		try {
			final RankDTO toCheckRank = dto.getRank();
			Boolean compRank = rankService.getRanks(compDTO).stream().anyMatch(predicate -> toCheckRank.equals(predicate));
			if(!compRank) {
				throw new ServiceException(toCheckRank.getCode() + " is not associated with " + compDTO.getName());               
			}
			dto.setAuditTrial(Helper.getAuditTrail());
			dto.setUUID(UUID.randomUUID());
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			dto = userDAO.create(dto);
			CompanyUserDTO compUser = new CompanyUserDTO();
			compUser.setCompany(compDTO);
			compUser.setUser(dto);
			compUser.setRank(dto.getRank());
			return associateUser(compUser);
			
			
			
		} catch (DataException e) {
			throw new ServiceException("error while creating user", e);
		}
	}
	
	public UserDTO update(UserDTO dto) {
		try {
			
//			dto.setAuditTrial(Helper.getAuditTrail());
//			if (dto.getPassword() != null) {
//			    dto.setPassword(passwordEncoder.encode(dto.getPassword()));  
//			}
			UserDTO dtoDB =  userDAO.loadUserByName(dto.getUserName());
			System.out.println(dtoDB.getPassword());
		    dto.setPassword(dtoDB.getPassword());  
			return userDAO.create(dto); 
		} catch (DataException e) {
			throw new ServiceException("error while creating user", e);
		}
	}
	
	
	public UserDTO updatepass(UserDTO dto) {
		try {
			
//			dto.setAuditTrial(Helper.getAuditTrail());
//			if (dto.getPassword() != null) {
//			    dto.setPassword(passwordEncoder.encode(dto.getPassword()));  
//			}
		    dto.setPassword(passwordEncoder.encode(dto.getPassword()));  
			return userDAO.create(dto); 
		} catch (DataException e) {
			throw new ServiceException("error while creating user", e);
		}
	}

	@Override
	public List<CompanyUserDTO> search(UserSearchDTO search) {
		try {
			// TODO Auto-generated method stub
			return userDAO.search(search);
		} catch (Exception e) {
			throw new ServiceException("error while search user", e);
		}
	}

	@Override
	public UserDTO load(String userName) {
		try {
			return userDAO.loadUserByName(userName);
		} catch (Exception e) {
			throw new ServiceException("error while loading user", e);
		}
	}

	@Override
	public List<CompanyUserDTO> loadCompUser(String userName) {
		try {
			return userDAO.loadCompUser(userName);
		} catch (Exception e) {
			throw new ServiceException("error while loading Company user user", e);
		}
	}

	@Override
	public CompanyUserDTO associateUser(CompanyUserDTO compUser) {
		if(compUser.getUser() == null) {
			throw new ServiceException("Cannot associate null user ");
		}
		if(compUser.getCompany() == null) {
			throw new ServiceException("Cannot associate user to an null company");
		}
		if(compUser.getRank() != null) {
			if(!compUser.getRank().equals(compUser.getUser().getRank())) {
				throw new ServiceException("User is associated with wrong rank");
			}
		}
		try {
			List<CompanyUserDTO> result =  userDAO.search(compUser);
			if(result != null && result .size() > 1 && !compUser.getRank().getMultiUser())  {
				throw new ServiceException(compUser.getRank() +" does not support multiple users");
			}
			return userDAO.update(compUser);
		} catch (DataException e) {e.printStackTrace();
			throw new ServiceException("error while associating user", e);
		}
	}

	@Override
	public UserDTO updateConfirmPassword(UserDTO user, UserPwdChangeVO userDTO) {
		if(!userDTO.getPassword().contentEquals(userDTO.getConfirmpassword())) {
			throw new ServiceException("Confirm Password does not match");
		}
		user.setPassword(userDTO.getPassword());
		return updatepass(user);
		
	}

	@Override
	public List<UserDTO> loadUsers(RankDTO rank) {
		try {
			UserSearchDTO dto = new UserSearchDTO();
			dto.setRank(rank.getName());
			dto.setCompany(ApplicationContext.getContext().getCompany());
			return CopyUtil.map(userDAO.search(dto), map -> map.getUser());
		} catch (Exception e) {
			throw new ServiceException("error while listing rank users", e);
		}
	}

}
