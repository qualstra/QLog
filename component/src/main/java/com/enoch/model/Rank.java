package com.enoch.model;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.RankDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TBL_RANK")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Rank  implements Transform<RankDTO>{
    @Id
    @GenericGenerator(name = "gen", strategy = "assigned")
    @GeneratedValue(generator = "gen")
    @Column(name = "code",length = 10)
	private String code;
    
    @Column(name = "name" , nullable = false)
	private String name;
    
    @Column(name = "descr")
	private String description;
    // Indicates multiple users can be associate with the role
    @Column(name = "multi_user",nullable = false)
    private Boolean multiUser = false;
    @Column(name = "active",nullable = false)
	private Boolean isAvailable;
    @Embedded
	private AuditTrail auditTrail;
	@Override
	public RankDTO transform() {
		RankDTO rank = new RankDTO();
		BeanUtils.copyProperties(this, rank);
		return rank;	}
    
}
