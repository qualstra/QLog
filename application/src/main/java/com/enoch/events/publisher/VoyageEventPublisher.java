package com.enoch.events.publisher;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

import com.enoch.events.VoyageEvent;

@Component
public class VoyageEventPublisher {
    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishEvent(final VoyageEvent message) {
        System.out.println("Publishing custom event. ");
        applicationEventPublisher.publishEvent(message);
    }
}