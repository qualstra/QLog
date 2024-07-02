package com.enoch.events;

import org.springframework.context.ApplicationEvent;

import com.enoch.dto.ShipDTO;

import lombok.Data;

@Data
public class VesselEvent extends ApplicationEvent {

	private ShipDTO ship;
	
	public VesselEvent(ShipDTO ship) {
		super(ship);
		this.ship= ship;
	}

}
