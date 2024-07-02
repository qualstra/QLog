/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.enoch.model.AuditTrail;
import com.enoch.model.User;
import com.enoch.utils.Transform;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Data
@NoArgsConstructor
public class UserSearchDTO {

	private Long id;
	private CompanyDTO company;
	// EMAIL
	private String userName;
	private String firstName;
	private String lastName;
	private String rank;
	
	private Date dob;
	private String cdc;
	private String passport;
	private String name;
	private Integer reportingTo;
	private boolean isActive;

	
}
