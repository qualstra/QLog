package com.enoch.attachment.dto;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.attachment.model.Attachment;
import com.enoch.common.exception.AuditInfoMissingException;
import com.enoch.model.AuditTrail;
import com.enoch.utils.Transform;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentDTO implements Transform<Attachment> {

	private Long id;
	private UUID UUID;
	private String secondaryId;
	private String location;
	private String locationNoExt;
	private String ext;
	private String type;
	private String header;
	private String desc;
	private boolean multi = true;
	public AuditTrail auditTrail;

	public AttachmentDTO() {
		auditTrail = new AuditTrail();
	}

	@Override
	public Attachment transform() {
		Attachment attachmentDTO = new Attachment();
		BeanUtils.copyProperties(this, attachmentDTO);
		if (auditTrail == null)
			throw new AuditInfoMissingException();
		return attachmentDTO;
	}

}
