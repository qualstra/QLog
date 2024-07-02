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

import com.enoch.dto.UserDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Entity
@Table(name = "TBL_USER")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Transform<UserDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "uuid", updatable = false, nullable = false)
	private UUID UUID;
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String userName;

	@Column(name = "FNAME", nullable = false)
	private String firstName;
	@Column(name = "LName", nullable = true)
	private String lastName;

	@Column(name = "PASSWORD", nullable = false)
	private String password;

	@ManyToOne
	@JoinColumn(name = "rank_code", nullable = false)
	private Rank rank;

	@Column(name = "DOB")
	private Date dob;

	@Column(name = "CDC")
	private String cdc;

	@Column(name = "PASSPORT", unique = true)
	private String passport;

	@Column(name = "NAME")
	private String name;

	@Column(name = "REPORTING_TO")
	private Integer reportingTo;

	@Column(name = "DATA_EXPORT_DATE")
	private Date dataExportDate;

	@Column(name = "ISACTIVE", nullable = true)
	private Boolean isActive;
	@Embedded
	private AuditTrail auditTrial;

	@Override

	public UserDTO transform() {
		UserDTO dto = new UserDTO();
		BeanUtils.copyProperties(this, dto);
		if (rank != null)
			dto.setRank(rank.transform());
		return dto;
	}
	


	
	

	
	

}
