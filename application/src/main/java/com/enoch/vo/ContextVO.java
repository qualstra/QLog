/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.vo;

import java.util.List;

import com.enoch.controller.Helper;
import com.enoch.dto.UserDTO;
import com.enoch.role.dto.PrivillegeDTO;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author Qualstra
 */
@Data
@NoArgsConstructor
public class ContextVO {

	public ContextVO(UserDTO dto) {
		this.user = Helper.toVO(dto);
	}
	
    private UserVO user;
    private CompanyVO company;
    private ShipVO vessel;
    private List<PrivillegeDTO> privs;
	public List<ShipVO> ships;

}
