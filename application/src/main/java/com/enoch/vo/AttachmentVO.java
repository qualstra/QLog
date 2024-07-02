package com.enoch.vo;

import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.enoch.model.AuditTrail;

import lombok.Data;

@Data
public class AttachmentVO {
	private Long id;
	private UUID uuid;
	private String secondaryId;
	private String  header;
	private String desc;
	private String type;
	private String location;
	private MultipartFile file;
	private AuditTrail auditTrail;
	

	
}
