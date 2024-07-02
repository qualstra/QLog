package com.enoch.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.enoch.dto.ShipDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.nc.Status;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class NCVO {
	private Long id;
	private UUID UUID;
	private UUID checkId;
	private QuestionInstVO question;
	private ShipDTO ship;
	private String remarks;
	private String responsible;
	private Date creationTime;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
	private Date closureTime;
	private Status status;
	private String level;
	private AuditTrail auditTrail;
	private List<AttachmentVO> ncAttachments = new ArrayList<AttachmentVO>();
	private List<AttachmentVO> ncClosureAttachments = new ArrayList<AttachmentVO>();
	
	
}
