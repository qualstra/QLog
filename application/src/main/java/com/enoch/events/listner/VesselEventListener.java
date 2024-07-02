package com.enoch.events.listner;

import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.enoch.attr.model.Attribute;
import com.enoch.events.CheckListEvent;
import com.enoch.events.VesselEvent;
import com.enoch.service.client.AttributeClient;

@Component
public class VesselEventListener implements ApplicationListener<VesselEvent> {
	protected final Log logger = LogFactory.getLog(VesselEventListener.class);

	@Autowired
	AttributeClient attrClient;

	@Override
	public void onApplicationEvent(VesselEvent event) {
		UUID compId = event.getShip().getCompany().getUUID();
		UUID vId = event.getShip().getUUID();
		Iterable<Attribute> vesselAttr = attrClient.getCompanyAttributesFor(compId,"vessel");
		vesselAttr.forEach(attr -> {
			attr.setEntityId(vId);
			attr.setEntity("vessel");
			attr.setId(null);
			try{attrClient.insertAttrib(compId, attr);}catch(Exception e) {
				logger.error("Error creating attribute for Vessel");
			}
		});
	}
}