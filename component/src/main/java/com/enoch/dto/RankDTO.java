package com.enoch.dto;

import org.springframework.beans.BeanUtils;

import com.enoch.model.AuditTrail;
import com.enoch.model.Rank;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RankDTO implements Transform<Rank> {
	 @EqualsAndHashCode.Exclude
	private String name;
	private String code;
	 @EqualsAndHashCode.Exclude
	private String description;
	 @EqualsAndHashCode.Exclude
	private Boolean isAvailable;
	 @EqualsAndHashCode.Exclude
	private Boolean multiUser = false;
	 @EqualsAndHashCode.Exclude
	private AuditTrail auditTrail;

	public Rank transform() {
		Rank rank = new Rank();
		BeanUtils.copyProperties(this, rank);
		return rank;
	}
}
