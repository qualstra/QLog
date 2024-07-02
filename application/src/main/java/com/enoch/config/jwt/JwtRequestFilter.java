package com.enoch.config.jwt;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.enoch.ApplicationContext;
import com.enoch.controller.Helper;
import com.enoch.dto.ContextDTO;
import com.enoch.dto.UserDTO;
import com.enoch.service.JwtUserDetailsService;
import com.enoch.vo.ContextVO;

import io.jsonwebtoken.ExpiredJwtException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	protected final Log logger = LogFactory.getLog(JwtRequestFilter.class);

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	Helper helper;
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws ServletException, IOException {
		
		ApplicationContext.initContext("JWTUser");
		final String requestTokenHeader = request.getHeader("Authorization");
		HttpSession session = request.getSession(true);
		String username = null;
		String jwtToken = null;
		// JWT Token is in the form "Bearer token". Remove Bearer word and get only the
		// Token
		if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
			jwtToken = requestTokenHeader.substring(7);
			try {
				username = jwtTokenUtil.getUsernameFromToken(jwtToken);
			} catch (IllegalArgumentException e) {
				System.out.println("Unable to get JWT Token");
			} catch (ExpiredJwtException e) {
				System.out.println("JWT Token has expired");
			}
		} else {
			logger.warn("JWT Token does not begin with Bearer String");
		}
		if(!request.getRequestURI().startsWith("/resources")) {
		// Once we get the token validate it.
		if (username != null && !request.getRequestURI().endsWith("userlogin") 
				&& SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDTO user = null;
			
			ContextDTO contextDTO = (ContextDTO) session.getAttribute("CONTEXT");

			if (contextDTO != null) {
				user = contextDTO.getUser();
			} else {
				user = this.jwtUserDetailsService.loadUserDtoByUsername(username);
				ApplicationContext.getContext().setUser(user);
				contextDTO = new ContextDTO();
				helper.populateContext(contextDTO,user);
				session.setAttribute("CONTEXT", contextDTO);
			}

			UserDetails userDetails = new User(user.getUserName(), user.getPassword(), new ArrayList<>());
			ApplicationContext.initContext(user.getUserName());
			// if token is valid configure Spring Security to manually set authentication
			if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {

				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				// After setting the Authentication in the context, we specify
				// that the current user is authenticated. So it passes the Spring Security
				// Configurations successfully.
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

				ApplicationContext.getContext().setUser(user);
				if (contextDTO != null) {
					ApplicationContext.getContext().setShip(contextDTO.getShip());
					ApplicationContext.getContext().setCompany(contextDTO.getCompany());
				}
			}
		}
		} else {
			
		}
		chain.doFilter(request, response);
	}

}
