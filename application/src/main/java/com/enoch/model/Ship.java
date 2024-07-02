/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.model;


import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.ShipDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "SHIP")
public class Ship implements Transform<ShipDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id")
	private Long id;

	@Column(name = "uuid", nullable=false)
	private UUID UUID;

	@Column(name = "NAME")
	private String name;

	@Column(name = "IMO")
	private String imo;

	@Column(name = "VESSEL_TYPE")
	private String vesselType;

	@Column(name = "CALSS")
	private String calss;

	@Column(name = "FLAG")
	private String flag;

	@Column(name = "GRT")
	private String grt;

	@Column(name = "CALL_SIGN")
	private String callSign;

	@ManyToOne
	@JoinColumn(name = "COMPANYID", nullable = false)
	private Company company;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "EMAIL")
	private String email;

	@Column(name = "ACTIVE_SINCE")
	private Date activesince;

	@Embedded
	private AuditTrail auditTrial;

	@Override
	public ShipDTO transform() {
		ShipDTO dto = new ShipDTO();
		
		BeanUtils.copyProperties(this, dto);
		if(company !=null) dto.setCompany(company.transform());
		return dto;
	}
}
