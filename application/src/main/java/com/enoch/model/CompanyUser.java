package com.enoch.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.enoch.dto.CompanyUserDTO;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "COMP_USER")
@AllArgsConstructor
@NoArgsConstructor
public class CompanyUser implements Transform<CompanyUserDTO> {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "COMPANY_ID", nullable = false)
	private Company company;
	@ManyToOne
	@JoinColumn(name = "USER_ID", nullable = false)
	private User user;

	@ManyToOne
	@JoinColumn(name = "SHIP_ID", nullable = true)
	private Ship ship;
	@ManyToOne
	@JoinColumn(name = "RANK_CODE", nullable = true)
	private Rank rank;

	@Override
	public CompanyUserDTO transform() {
		CompanyUserDTO dto = new CompanyUserDTO();
		dto.setId(id);
		if (company != null)
			dto.setCompany(company.transform());
		if (user != null)
			dto.setUser(user.transform());
		if (ship != null)
			dto.setShip(ship.transform());
		if (rank != null)
			dto.setRank(rank.transform());
		return dto;
	}

}
