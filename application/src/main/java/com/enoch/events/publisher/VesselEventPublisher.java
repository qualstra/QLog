package com.enoch.events.publisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.enoch.events.VesselEvent;

@Component
public class VesselEventPublisher {
	
	protected final Log logger = LogFactory.getLog(VesselEventPublisher.class); 
	
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final VesselEvent message) {
    	logger.info("Create Vessel Event.");
        applicationEventPublisher.publishEvent(message);
    }
}