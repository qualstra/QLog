package com.enoch.vo;

import java.util.Date;

import com.enoch.constant.VoyageStatus;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoyageSearchVO {
	

	private Long id;
	private Long vesseilId;
	private String masterVesselName;
	private String voyageNo;
	private String orgin;
	private String orginDesc;
	private String destination;
	private String destDesc;
	private VoyageStatus status;
	private Date start;
	private Date end;
	private Long tenantid;
	private CompanyVO company;

}
