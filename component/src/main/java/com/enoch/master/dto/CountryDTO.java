/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.master.dto;

import java.math.BigDecimal;

import org.springframework.beans.BeanUtils;

import com.enoch.master.model.Country;
import com.enoch.model.AuditTrail;
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
public class CountryDTO implements Transform<Country> {

	private String code;
	private String name;
	private String description;
	private String taxLabel;
	private String defaultcurrency;
	private String defaultlanguage;
	private BigDecimal taxrate;
	private AuditTrail auditTrail;

	@Override
	public Country transform() {
		Country country = new Country();
		BeanUtils.copyProperties(this, country);
		return country;
	}
}
