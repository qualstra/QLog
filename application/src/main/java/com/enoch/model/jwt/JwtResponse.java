package com.enoch.model.jwt;

import java.io.Serializable;

import com.enoch.vo.ContextVO;

import lombok.Getter;
import lombok.Setter;

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;
	@Getter @Setter
	private ContextVO loggedInContext;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
	
	
}