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
import com.enoch.model.Rank;
import com.enoch.model.User;
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
public class UserUpdateDTO implements Transform<User>,Attachable {

	private Long id;
	private UUID UUID;
	// EMAIL
	private String userName;
	private String firstName;
	private String lastName;
	private RankDTO rank;
	
	private Date dob;
	private String cdc;
	private String passport;
	private String name;
	private Integer reportingTo;
	private Date dataExportDate;
	private Boolean isActive;
	private AuditTrail auditTrial;

	@Override
	public User transform() {
		User user = new User();
		BeanUtils.copyProperties(this, user);
		if (rank != null)
			user.setRank(rank.transform());
		return user;
	}

	@Override
	public boolean equals(Object other) {
		if(!(other instanceof UserUpdateDTO)) {
			return false;
		}
		return this.userName != null && this.userName.equalsIgnoreCase(((UserUpdateDTO)other).userName);
		
	}
//
//	public Object transform1() {
//		// TODO Auto-generated method stub
//		return null;
//	}

	
	
}
