package com.enoch.vo;

import java.util.Date;

import com.enoch.constant.VoyageStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class VoyageVO {

	private Long id;
	//private Date etd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
	private Date atd;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
	private Date eta;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
	private Date checklist_etc;
	private String destDesc;
	private String destination;
	private String masterVesselName;
	private String orgin;
	private String orginDesc;
	private VoyageStatus status;
	//Long tenantid;
	private Long vesseilId;
	private String imo;
	private String voyageNo;
	private CompanyVO company;
}
