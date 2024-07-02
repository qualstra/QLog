package com.enoch.events;

import org.springframework.context.ApplicationEvent;

import com.enoch.dto.VoyageDTO;

import lombok.Data;

@Data
public class VoyageEvent extends ApplicationEvent {

	private VoyageDTO voyage;
	
	public VoyageEvent(VoyageDTO source) {
		super(source);
		this.voyage = source;
	}

}
