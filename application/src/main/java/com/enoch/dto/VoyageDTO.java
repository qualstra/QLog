/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.enoch.constant.VoyageStatus;
import com.enoch.master.model.Port;
import com.enoch.model.AuditTrail;
import com.enoch.model.Voyage;
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
public class VoyageDTO implements Transform<Voyage> {

    private Long id;
    private ShipDTO ship;
    private String voyageNo;
    private Port orgin;
    private Port destination;
   // private Date etd;
    private Date atd;
    private Date eta;
    private Date checklist_etc;
    private CompanyDTO company;
    private VoyageStatus status;
    private AuditTrail auditTrail;
	
    @Override
	public Voyage transform() {
    	Voyage dto = new Voyage();
    	BeanUtils.copyProperties(this, dto);
    	if(company!= null)
    		dto.setCompany(company.transform());
    	if(ship != null)
    		dto.setShip(ship.transform());
		return dto;
	}


}
