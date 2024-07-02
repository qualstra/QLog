package com.enoch.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import lombok.Data;

@Data
public class PortSearchDTO {
	@JsonAlias({ "country" })
	private String countryCode;
}
