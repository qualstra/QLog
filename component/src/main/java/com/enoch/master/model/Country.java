/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.master.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.master.dto.CountryDTO;
import com.enoch.model.AuditTrail;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Entity
@Table(name = "COUNTRY")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Country implements Transform<CountryDTO>{

    @Id
    @GenericGenerator(name = "gen", strategy = "assigned")
    @GeneratedValue(generator = "gen")
    @Column(name = "CODE")
    private String code;

    @Column(name = "NAME")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "TAX_LABEL")
    private String taxLabel;

    @Column(name = "DEFAULT_CURRENCY")
    private String defaultcurrency;

    @Column(name = "DEFAULT_LANGUAGE")
    private String defaultlanguage;

    @Column(name = "TAX_RATE")
    private BigDecimal taxrate;

    @Embedded
	private AuditTrail auditTrail = new AuditTrail();

	@Override
	public CountryDTO transform() {
		CountryDTO countryDTO = new CountryDTO();
		BeanUtils.copyProperties(this, countryDTO);
		return countryDTO;
	}
}
