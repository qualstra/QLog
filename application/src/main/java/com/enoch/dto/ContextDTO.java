package com.enoch.dto;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.enoch.role.dto.PrivillegeDTO;

import lombok.Data;

@Data
public class ContextDTO {
	
	private CompanyUserDTO compUser = new CompanyUserDTO();
	private List<CompanyUserDTO> compUsers = Collections.emptyList();
    private List<PrivillegeDTO> privs;
    List<ShipDTO> ships = Collections.emptyList();
    public void setCompUsers(List<CompanyUserDTO> compUsers ) {
    	
    	if(compUsers!= null && compUsers.size()>0) {
    		Set<ShipDTO> ships = compUsers.stream().filter(a-> a.getShip() != null).
    				map(a->a.getShip()).collect(Collectors.toSet());
    		if(ships != null) this.ships = new ArrayList(ships);
    		compUser = compUsers.get(0);
    	}
    	
    	this.compUsers = compUsers;
    }
    
    public UserDTO getUser() {
		return compUser.getUser();
	}
	public ShipDTO getShip() {
		return compUser.getShip();
	}
	public CompanyDTO getCompany() {
		return compUser.getCompany();
	}
}
