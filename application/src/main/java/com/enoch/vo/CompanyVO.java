package com.enoch.vo;

import java.util.UUID;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.model.AuditTrail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CompanyVO {
	private Long id;
	private String name;
	private UUID UUID;
	private String address1;
	private String address2;
	private String city;
	private String country;
	private Boolean isActive;
	private String itAdminUser;
	private String nctype;
	private AuditTrail auditTrail;
	private AttachmentDTO prolileAttachment;
	
}
