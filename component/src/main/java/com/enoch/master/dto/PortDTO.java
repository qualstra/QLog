/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.master.dto;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.beans.BeanUtils;

import com.enoch.master.model.Country;
import com.enoch.master.model.Port;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PortDTO implements Transform<Port> {
	private String code;
	private String city;
	private String description;
	private CountryDTO country;
	@Override
	public Port transform() {
		Port port = new Port();
		BeanUtils.copyProperties(this, port);
		port.setCountry(country.transform());
		return port;
	}
	public PortDTO(String portCode) {
		this.code = portCode;
	}

}
