package com.enoch.dto.fleet;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.UserDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.fleet.Fleet;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor @NoArgsConstructor
public class FleetDTO implements Transform<Fleet>,Serializable {
	private Long id;
	private String name;
	private UUID uuid;
	private CompanyDTO company;
	private UserDTO fleetManager;
	private AuditTrail auditTrail;
	@Override
	public Fleet transform() {
		Fleet target = new Fleet();
		BeanUtils.copyProperties(this, target);
		if(company != null) {
			target.setCompany(company.transform());
		}
		if(fleetManager != null) {
			target.setFleetManager(fleetManager.transform());
		}
		return target;
	}
}
