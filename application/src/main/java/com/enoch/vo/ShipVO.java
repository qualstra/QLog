package com.enoch.vo;

import java.util.Date;
import java.util.UUID;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.model.AuditTrail;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ShipVO {
	
 	private Long id;
 	private UUID UUID;
 	private String name;
 	private String imo;
 	private String vesselType;
 	private String calss;
 	private String flag;
 	private String grt;
 	private String callSign;
 	private CompanyVO company;
 	private String status;
 	private String email;
 	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "IST")
 	private Date activesince;
 	private AuditTrail auditTrial;
 	private AttachmentDTO prolileAttachment;
}
