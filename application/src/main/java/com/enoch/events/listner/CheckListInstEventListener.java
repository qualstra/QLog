package com.enoch.events.listner;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.enoch.ApplicationContext;
import com.enoch.attr.model.Attribute;
import com.enoch.events.CheckListEvent;
import com.enoch.service.CheckListInstService;
import com.enoch.service.client.AttributeClient;

@Component
public class CheckListInstEventListener implements ApplicationListener<CheckListEvent> {
	protected final Log logger = LogFactory.getLog(CheckListInstEventListener.class);

	@Autowired
	AttributeClient attrClient;
	@Autowired
	CheckListInstService chkInstService;

	@Override
	public void onApplicationEvent(CheckListEvent event) {
		ApplicationContext.initContext("EventPublisher");
		UUID compId = event.getChecklist().getCompany().getUUID();
		UUID vId = event.getChecklist().getVessel().getUUID();
		UUID entId = event.getChecklist().getCheckId();
		Iterable<Attribute> vesselAttr = attrClient.getCompanyAttributesFor(compId, "vessel",vId);
		vesselAttr.forEach(attr -> {
			attr.setEntityId(entId);
			attr.setEntity("checklist");
			attr.setId(null);
			try{attrClient.insertAttrib(compId, attr);}catch(Exception e) {
				logger.error("Error creating attribute for Checklist ");
			}
		});
		
		initRolePrivs(event);
	}
	
	
	private void initRolePrivs(CheckListEvent event) {
		Stream<String> stream = event.getSecQuestions().stream().map(secQue -> secQue.getQuestion().getFilter());
		//stream .collect(Collectors.toList())stream.count()
		Set<String> ranks =  stream.flatMap(
				jsonStr -> {
					jsonStr = jsonStr == null ? new JSONObject().toString() : jsonStr;
					JSONObject filterJson = new JSONObject(jsonStr);
					try {
						return Stream.of(filterJson.optJSONArray("rank").toList().toArray(new String[] {}));
					} catch (Exception e) {}
					return Stream.of(filterJson.optString("rank",""));
				}
		).collect(Collectors.toSet());
		event.getChecklist().setData(new JSONObject(event.getChecklist().getData()).put("ranks",ranks).toString());
		
		chkInstService.save(event.getChecklist());
	}
}