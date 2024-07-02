package com.enoch.vo.fleet;

import java.util.UUID;

import com.enoch.model.AuditTrail;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.UserVO;

import lombok.Data;
@Data
public class FleetVO {
	private Long id;
	private String name;
	private UUID UUID;
	private CompanyVO company;
	private UserVO fleetManager;
	private AuditTrail auditTrail;
}
