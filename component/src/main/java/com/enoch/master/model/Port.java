/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.master.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.master.dto.PortDTO;
import com.enoch.utils.Transform;

import lombok.Data;

/**
 *
 * @author Qualstra
 */
@Entity
@Table(name = "PORT")
@Data
public class Port implements Transform<PortDTO> {

    @Id
    @GenericGenerator(name = "gen", strategy = "assigned")
    @GeneratedValue(generator = "gen")
    @Column(name = "CODE")
    private String code;

    @Column(name = "CITY")
    private String city;

    @Column(name = "DESCRIPTION")
    private String description;
    
	@ManyToOne
	@JoinColumn(name = "CNTRY_ID", nullable = false)
    private Country country;

	@Override
	public PortDTO transform() {
		PortDTO port = new PortDTO();
		BeanUtils.copyProperties(this, port);
		port.setCountry(country.transform());
		return port;
	}
}
