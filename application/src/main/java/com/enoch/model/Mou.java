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
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.MouDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author user
 */
@Entity
@Table(name = "TBL_MOU")
@NoArgsConstructor
@Data
@AllArgsConstructor
public class Mou implements Transform<MouDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "CODE")
	private String code;
	@Column(name = "DESCR")
	private String desc;
	@Column(name = "ACTIVE_CIC_CHECKLIST")
	private Long cicid;
	@Column(name = "CIC_CHECKLIST_ENDDATE")
	private Date toDate;
	@Column(name = "CIC_CHECKLIST_FROMDATE")
	private Date fromDate;
	@Embedded
	private AuditTrail auditTrail;

	@Override
	public MouDTO transform() {
		MouDTO mou = new MouDTO();
		BeanUtils.copyProperties(this, mou);
		return mou;
	}

}
