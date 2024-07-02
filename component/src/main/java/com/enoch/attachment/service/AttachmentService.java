package com.enoch.attachment.service;

import java.util.List;
import java.util.Optional;

import com.enoch.Attachable;
import com.enoch.attachment.dto.AttachmentDTO;


public interface AttachmentService {
	public AttachmentDTO create(AttachmentDTO attachment);

	public List<AttachmentDTO> getAll(Attachable attachable);

	public List<AttachmentDTO> getAll(Attachable attachable, String secId);
	
	public AttachmentDTO update(AttachmentDTO attachDTO);
	
	public void delete(Long id);
	public long deleteAll(Attachable attachable);
	public long deleteAll(Attachable attachable, String secId);

	public Optional<AttachmentDTO> load(Long id);
}
