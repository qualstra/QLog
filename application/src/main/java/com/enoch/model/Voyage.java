/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.model;

import java.util.Date;

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

import com.enoch.constant.VoyageStatus;
import com.enoch.dto.VoyageDTO;
import com.enoch.master.model.Port;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Entity
@Table(name = "VOYAGE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Voyage implements Transform<VoyageDTO> {

    @Id
    @GenericGenerator(name = "gen", strategy = "increment")
    @GeneratedValue(generator = "gen")
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
    
    @ManyToOne
	@JoinColumn(name = "VESSELID", nullable = false)
    private Ship ship;

    @Column(name = "VOYAGENO", nullable = false,length = 15,unique = true )
    private String voyageNo = "DEF";


    @ManyToOne
	@JoinColumn(name = "ORIGIN", nullable = false)
    private Port orgin;

    
    @ManyToOne
	@JoinColumn(name = "DESTINATION", nullable = false)
    private Port destination;

    @Column(name = "ETD")
    private Date etd;
    @Column(name = "ATD")
    private Date atd;
    @Column(name = "ETA")
    private Date eta;

    @Column(name = "CHECKLISTETC")
    private Date checklist_etc;

    @ManyToOne
	@JoinColumn(name = "COMP_ID", nullable = false)
    private Company company;

    @Column(name = "STATUS")
    private VoyageStatus status;

    @Embedded
    private AuditTrail auditTrail = Helper.getAuditTrail();
	
    @Override
	public VoyageDTO transform() {
    	VoyageDTO dto = new VoyageDTO();
    	BeanUtils.copyProperties(this, dto);
    	dto.setCompany(company.transform());
    	dto.setShip(ship.transform());
		return dto;
	}
}
