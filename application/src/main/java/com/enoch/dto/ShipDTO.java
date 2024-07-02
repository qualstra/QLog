/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.dto;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.Attachable;
import com.enoch.model.AuditTrail;
import com.enoch.model.Ship;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ShipDTO implements Transform<Ship>,Attachable {

	@EqualsAndHashCode.Include
	private Long id;
	private UUID UUID;
	private String name;
	@EqualsAndHashCode.Include
	private String imo;
	private String vesselType;
	private String calss;
	private String flag;
	private String grt;
	private String callSign;
	private CompanyDTO company;
	private String status;
	private String email;
	private Date activesince;
	private AuditTrail auditTrial;

	@Override
	public Ship transform() {
		Ship dto = new Ship();
		BeanUtils.copyProperties(this, dto);
		if(company != null)
		dto.setCompany(company.transform());
		return dto;
	}

	public ShipDTO(Long vesseilId) {
		this.id = vesseilId;
	}
	
}
