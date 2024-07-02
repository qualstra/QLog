package com.enoch.events.publisher;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.enoch.events.CheckListEvent;

@Component
public class CheckListInstEventPublisher {
	
	protected final Log logger = LogFactory.getLog(CheckListInstEventPublisher.class); 
	
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final CheckListEvent message) {
    	logger.info("Create Checklist Event.");
        applicationEventPublisher.publishEvent(message);
    }
}