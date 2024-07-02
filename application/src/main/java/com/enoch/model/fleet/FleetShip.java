package com.enoch.model.fleet;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

import com.enoch.dto.fleet.FleetShipDTO;
import com.enoch.model.Ship;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Entity(name = "FLEET_SHIP")
@AllArgsConstructor @NoArgsConstructor
public class FleetShip implements Transform<FleetShipDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	@ManyToOne
	@JoinColumn(name = "fleet_id", nullable = false)
	private Fleet fleet;
	@ManyToOne
	@JoinColumn(name = "vessel_id", nullable = false,unique = true)
	private Ship ship;
	
	@Override
	public FleetShipDTO transform() {
		FleetShipDTO target = new FleetShipDTO();
		if(fleet != null) {
			target.setFleet(fleet.transform());
		}
		if(ship != null) {
			target.setShip(ship.transform());
		}return target;
	}
}
