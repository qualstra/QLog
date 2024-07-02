package com.enoch.attachment.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.model.AuditTrail;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
@Entity(name = "ATTACHMENT")
public class Attachment implements Transform<AttachmentDTO> {

	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	public Long id;
	@Column(name = "UID", nullable = false, updatable = false)
	private UUID UUID;
	
	@Column(name = "SCNDRY_ID", nullable = false, updatable = false)
	public String secondaryId;
	
	@Column(name = "LOCATION", nullable = false, updatable = false)
	private String location;
	
	
	@Column(name = "TYP")
	private String type;
	@Column(name = "HEADER")
	private String header;
	@Column(name = "DESCR")
	private String desc;
	@Embedded
	public AuditTrail auditTrail;

	@Override
	public AttachmentDTO transform() {
		AttachmentDTO attachmentDTO = new AttachmentDTO();
		BeanUtils.copyProperties(this, attachmentDTO);
		return attachmentDTO;
	}

}
