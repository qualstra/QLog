package com.enoch.events.listner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import com.enoch.ApplicationContext;
import com.enoch.common.exception.ServiceException;
import com.enoch.config.ApplicationProperties;
import com.enoch.dto.VoyageDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.events.VoyageEvent;
import com.enoch.model.checklist.inst.Association;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.VoyageService;

@Component
public class VoyageEventListener implements ApplicationListener<VoyageEvent> {
	@Autowired
	ApplicationProperties prop;
	@Autowired
	CheckListService chkServ; 
	@Autowired
	CheckListInstService chkInstServ; 
	@Autowired 
	VoyageService voyageServ;
    @Override
    public void onApplicationEvent(VoyageEvent event) {
    	ApplicationContext.initContext("EventPublisher");
    	VoyageDTO voyage = voyageServ.load(event.getVoyage().getVoyageNo());
    	String countryCode = voyage.getDestination().getCountry().getCode();
    	@SuppressWarnings("unchecked")
		List<String> checklists = (List<String>) prop.getVoyageChecklists().get(countryCode);
    	if(checklists != null) {
    		checklists.forEach(strChkList -> {
    			cloneChecklist(strChkList, voyage );
    		});
    		
    		
    	}
    }
	private void cloneChecklist(String strChkList, VoyageDTO voyage ) {
		CheckListSearchDTO chkSearch = new CheckListSearchDTO();
		chkSearch.setCompany(ApplicationContext.getContext().getCompany());
		chkSearch.setCheckListCode(strChkList);
		List<CheckListDTO> checklists = chkServ.search(chkSearch);
		if(checklists == null || checklists.size() == 0) {
			throw new ServiceException("No Checklist found for the given code " + strChkList );
		}
		if(checklists.size() > 1) {
			throw new ServiceException("Multiple Checklist found for the given code " + strChkList );
		}
	
		CheckListInstCreateDTO checkListInstCreateDTO = new CheckListInstCreateDTO();
		CheckListDTO mChecklist = checklists.get(0);
		checkListInstCreateDTO.setVessel(voyage.getShip());
		checkListInstCreateDTO.setVoyage(voyage);
		checkListInstCreateDTO.setCompany(voyage.getCompany());
		checkListInstCreateDTO.setMasterChkUUID(mChecklist.getCheckId());
		checkListInstCreateDTO.setChecklistName(mChecklist.getName()+"-"+voyage.getVoyageNo());
		checkListInstCreateDTO.setStartDate(voyage.getAtd());
		checkListInstCreateDTO.setEndDate(voyage.getEta());
		checkListInstCreateDTO.setAssocType(Association.RANK);
		chkInstServ.clone(checkListInstCreateDTO);
	}
}