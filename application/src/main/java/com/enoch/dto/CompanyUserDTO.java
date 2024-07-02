package com.enoch.dto;

import com.enoch.model.CompanyUser;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
public class CompanyUserDTO implements Transform<CompanyUser> {
	private Long id;
	private CompanyDTO company;
	private UserDTO user;
	private ShipDTO ship;
	private RankDTO rank;
	@Override
	public CompanyUser transform() {
		CompanyUser companyUser = new CompanyUser();
		companyUser.setId(id);
		if(company != null)
		companyUser.setCompany(company.transform());
		if(user != null)
			companyUser.setUser(user.transform());
		if(ship != null)
			companyUser.setShip(ship.transform());
		if(rank != null)
			companyUser.setRank(rank.transform());
		return companyUser;
	}
}
