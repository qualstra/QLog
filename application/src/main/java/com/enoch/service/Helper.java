package com.enoch.service;

import com.enoch.ApplicationContext;
import com.enoch.dto.RankDTO;
import com.enoch.model.AuditTrail;

import lombok.val;

public class Helper {
	
	public static AuditTrail getAuditTrail() {
		val userId = ApplicationContext.getContext().getLoggedInUser();
		return new AuditTrail(userId);
	}

	public static RankDTO getITAdminRank() {
		return new RankDTO("ITADMIN","ITADMIN","DESCRIPTION",true,false,null);
	}
}
