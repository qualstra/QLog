/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.dto;


import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.enoch.constant.Status;
import com.enoch.master.model.Port;
import com.enoch.model.AuditTrail;
import com.enoch.model.User;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShipSearchDTO {

	private Long id;
	private String name;
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

}
