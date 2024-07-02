package com.enoch.attachment.dao;

import java.util.List;
import java.util.Optional;

import com.enoch.Attachable;
import com.enoch.attachment.dto.AttachmentDTO;


public interface AttachmentDAO {
	public AttachmentDTO create(AttachmentDTO attachment);
	public List<AttachmentDTO> getAll(Attachable attachable);
	public List<AttachmentDTO> getAll(Attachable attachable, String secondaryId);
	public int deleteAll(Attachable attachable);
	public int deleteAll(Attachable attachable, String secId);
	public void delete(Long id);
	public AttachmentDTO update(AttachmentDTO attachDTO);
	public Optional<AttachmentDTO> load(Long id);
}
