package com.enoch.dto.checklist.inst;

import java.util.Date;
import java.util.UUID;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.VoyageDTO;
import com.enoch.model.checklist.inst.Association;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CheckListInstCreateDTO {
	private Association assocType;
	private String assocId;
	
	private String checklistName;
	private String documentNo;
	private UUID masterChkUUID;
	private Date startDate;
	private Date endDate;
	
	private CompanyDTO company;
	private ShipDTO vessel;
	
	private VoyageDTO voyage;
	
	
	
	
}
