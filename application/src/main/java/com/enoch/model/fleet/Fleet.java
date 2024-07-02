package com.enoch.model.fleet;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.dto.fleet.FleetDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.User;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity(name = "FLEET")
@AllArgsConstructor @NoArgsConstructor
public class Fleet implements Transform<FleetDTO>,Serializable {
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@Column(name = "name", nullable = false)
	private String name;
	@Column(name = "uuid", nullable = false, updatable = false)
	private UUID uuid;
	@ManyToOne
	@JoinColumn(name = "compid", nullable = false)
	private Company company;
	@ManyToOne
	@JoinColumn(name = "userid", nullable = false)
	private User fleetManager;
	
	@Embedded
	private AuditTrail auditTrail;
	
	@Override
	public FleetDTO transform() {
		FleetDTO target = new FleetDTO();
		target.setName(getName());
		target.setId(getId());
		target.setUuid(getUuid());
		target.setAuditTrail(getAuditTrail());
		if(company != null) {
			target.setCompany(company.transform());
		}
		if(fleetManager != null) {
			target.setFleetManager(fleetManager.transform());
		}
		return target;
	}
}
