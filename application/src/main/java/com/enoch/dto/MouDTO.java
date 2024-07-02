/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.dto;

import java.util.Date;

import org.springframework.beans.BeanUtils;

import com.enoch.model.AuditTrail;
import com.enoch.model.Mou;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author user
 */
@NoArgsConstructor
@Data
@AllArgsConstructor
public class MouDTO implements Transform<Mou> {

	private Long id;
	private String code;
	private String desc;
	private Long cicid;
	private Date toDate;
	private Date fromDate;
	private AuditTrail auditTrail;

	@Override
	public Mou transform() {
		Mou mou = new Mou();
		BeanUtils.copyProperties(this, mou);
		return mou;
	}

}
