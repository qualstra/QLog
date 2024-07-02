package com.enoch.attachment.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.Attachable;
import com.enoch.attachment.dao.AttachmentDAO;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;

@Service
public class AttachmentServiceImpl implements AttachmentService {

	@Autowired
	AttachmentDAO attachmentDAO;
	
	@Override
	public AttachmentDTO create(AttachmentDTO attachment) {
		List<AttachmentDTO> attachmensts =  Collections.EMPTY_LIST;
		if(!attachment.isMulti()) {
			attachmensts =  attachmentDAO.getAll(() -> attachment.getUUID(),attachment.getSecondaryId());
		}
		AttachmentDTO result = attachmentDAO.create(attachment);
		
		attachmensts.forEach( a -> attachmentDAO.delete(a.getId()));
		return result;
	}

	@Override
	public List<AttachmentDTO> getAll(Attachable attachable) {
		return attachmentDAO.getAll(attachable);
	}

	@Override
	public List<AttachmentDTO> getAll(Attachable attachable, String secId) {
		return attachmentDAO.getAll(attachable,secId);
	}

	@Override
	public void delete(Long id) {
		attachmentDAO.delete(id);
	}

	@Override
	public long deleteAll(Attachable attachable) {
		return attachmentDAO.deleteAll(attachable);
	}

	@Override
	public long deleteAll(Attachable attachable, String secId) {
		return attachmentDAO.deleteAll(attachable,secId);
	}

	@Override
	public AttachmentDTO update(AttachmentDTO attachDTO) {
		return attachmentDAO.update(attachDTO);
	}

	@Override
	public Optional<AttachmentDTO> load(Long id) {
		return attachmentDAO.load(id);
	}

}
