package com.enoch.dto.fleet;

import com.enoch.dto.ShipDTO;
import com.enoch.model.fleet.FleetShip;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@AllArgsConstructor @NoArgsConstructor
@Data
public class FleetShipDTO implements Transform<FleetShip>{
	private FleetDTO fleet;
	private ShipDTO ship;
	@Override
	public FleetShip transform() {
		FleetShip target = new FleetShip();
		if(fleet != null) {
			target.setFleet(fleet.transform());
		}
		if(ship != null) {
			target.setShip(ship.transform());
		}return target;
	}
}
