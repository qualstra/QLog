package com.enoch.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.enoch.dao.UserDAO;
import com.enoch.dto.UserDTO;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	UserDAO service;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
			UserDTO user = service.loadUserByName(username);
			UserDetails details = new User(user.getUserName(), user.getPassword(),
					new ArrayList<>());
			return details;
	}

	public UserDTO loadUserDtoByUsername(String username) throws UsernameNotFoundException {
			UserDTO user = service.loadUserByName(username);
			return user;
	}
	
}