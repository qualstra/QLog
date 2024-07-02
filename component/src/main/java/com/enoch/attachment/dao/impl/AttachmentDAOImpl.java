package com.enoch.attachment.dao.impl;


import static  com.enoch.utils.CopyUtil.transform;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;


import com.enoch.Attachable;
import com.enoch.attachment.dao.AttachmentDAO;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.model.Attachment;


@Service
public class AttachmentDAOImpl implements AttachmentDAO {
	protected final Log logger = LogFactory.getLog(AttachmentDAOImpl.class);

	@Autowired
	AttachmentRepo repo;

	@Override
	public AttachmentDTO create(AttachmentDTO attachment) {
		return repo.save(attachment.transform()).transform();
	}

	@Override
	public List<AttachmentDTO> getAll(Attachable attachable) {
		Attachment example = new Attachment();
		example.setUUID(attachable.getUUID());
		return transform(repo.findAll(Example.of(example)));
	}
	@Override
	public List<AttachmentDTO> getAll(Attachable attachable,String secondaryId) {
		Attachment example = new Attachment();
		example.setUUID(attachable.getUUID());
		example.setSecondaryId(secondaryId);
		return transform(repo.findAll(Example.of(example)));
	}


	@Override
	public int deleteAll(Attachable attachable) {
		return repo.deleteAll(attachable.getUUID());
	}

	@Override
	public int deleteAll(Attachable attachable, String secId) {
		return repo.deleteAll(attachable.getUUID(),secId);
	}

	@Override
	public void delete(Long id) {
		repo.deleteById(id);
	}

	@Override
	public AttachmentDTO update(AttachmentDTO attachDTO) {
		return repo.save(attachDTO.transform()).transform();
	}

	@Override
	public Optional<AttachmentDTO> load(Long id) {
		return repo.findById(id).map(a -> a.transform());
	}
	

}

interface AttachmentRepo extends JpaRepository<Attachment, Long> {
	@Modifying
	@Transactional(readOnly = false, isolation = Isolation.DEFAULT, propagation = Propagation.REQUIRED, rollbackFor = {Exception.class})
	@Query("delete from com.enoch.attachment.model.Attachment b where b.UUID = ?1")
	int deleteAll(UUID attachable);
	@Modifying
	@Query("delete from com.enoch.attachment.model.Attachment b where b.UUID = ?1 and b.secondaryId = ?2")
	int deleteAll(UUID attachable, String secId);

}