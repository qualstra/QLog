package com.enoch.controller;

import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.enoch.common.exception.ServiceException;
import com.enoch.ApplicationContext;
import com.enoch.config.jwt.JwtTokenUtil;
import com.enoch.dao.UserDAO;
import com.enoch.dto.ContextDTO;
import com.enoch.dto.UserDTO;
import com.enoch.model.jwt.JwtRequest;
import com.enoch.model.jwt.JwtResponse;
import com.enoch.service.UserService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.ContextVO;
import com.enoch.vo.UserPwdChangeVO;
import com.enoch.vo.UserVO;

@RestController
@CrossOrigin
public class JwtAuthenticationController {

	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private UserDAO dao;
	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private UserDetailsService jwtInMemoryUserDetailsService;

	@RequestMapping(value = "/rs/getAllTenantCodes", method = RequestMethod.POST)
	public ResponseEntity<?> getAllTenantCodes(@RequestBody JwtRequest authenticationRequest) throws Exception {
		return ResponseEntity.ok().build();

	}

	@RequestMapping(value = "/rs/userlogin", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(HttpServletRequest req,
			@RequestBody JwtRequest authenticationRequest) throws Exception {
		try {
		ApplicationContext.initContext("VJ1");
		UserDTO userdto = authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());
		ApplicationContext.getContext().setUser(userdto);

		HttpSession session = req.getSession(true);
		// THis is for the UI
		ContextVO contextVO = new ContextVO(userdto);
		// THis is for the application

		final UserDetails userDetails = jwtInMemoryUserDetailsService
				.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);
		JwtResponse jwtResponse = new JwtResponse(token);

		ContextDTO contextDTO = new ContextDTO();
		helper.populateContext(contextDTO, userdto);
		if (contextDTO.getShip() != null)
			contextVO.setVessel(Helper.toVO(contextDTO.getShip()));

		contextVO.setPrivs(contextDTO.getPrivs());
		contextVO.setCompany(Helper.toVO(contextDTO.getCompUser().getCompany()));
		contextVO.setShips(CopyUtil.map(contextDTO.getShips(), a -> Helper.toVO(a)));
		jwtResponse.setLoggedInContext(contextVO);

		session.setAttribute("CONTEXT", contextDTO);
		return ResponseEntity.ok(jwtResponse);
	}
	catch(Exception e){
		throw new Exception("INVALID CREDENTIALS");
	}
	}

	@Autowired
	UserService userService;

	@RequestMapping(value = "/rs/changepassword", method = RequestMethod.POST)
	public ResponseEntity<UserVO> UpdateConfirmPassword(@RequestBody UserPwdChangeVO userVO) throws Exception {
		try {
			UserDTO userdto = authenticate(userVO.getUserName(), userVO.getOldpassword());
			return ResponseEntity.ok(Helper.toVO(userService.updateConfirmPassword(userdto, userVO)));
		} catch (Exception e) {
			throw new Exception(e);
		}

	}

	@Autowired
	Helper helper;

	private UserDTO authenticate(String username, String password) throws Exception {
		Objects.requireNonNull(username);
		Objects.requireNonNull(password);

		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
			return dao.loadUserByName(username);
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
