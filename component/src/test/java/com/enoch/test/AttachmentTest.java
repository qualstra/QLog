package com.enoch.test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.Attachable;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.attachment.service.AttachmentService;

@DataJpaTest
@RunWith(SpringRunner.class)
public class AttachmentTest {
	@Autowired
	private TestEntityManager entityManager;
	@Autowired 
	private AttachmentService service;
	@Test
	public void testAttachmentCreate() throws DataException {
		AttachmentDTO dto = new AttachmentDTO();
		final UUID uuid = UUID.randomUUID();
		dto.setUUID(uuid);
		dto.setLocation("some location");
		dto.setSecondaryId("PROFILE");
		service.create(dto);
		List<AttachmentDTO> dtos = service.getAll(new Attachable() {
			@Override
			public UUID getUUID() {
				return uuid;
			} 
		});
		assert dtos.size() == 1  : "Attachment Not Inserted";
		System.out.println(dtos.get(0));
	}
	@Test
	public void testAttachmentCreateNull() throws DataException {
		AttachmentDTO dto = new AttachmentDTO();
		final UUID uuid = UUID.randomUUID();
		dto.setUUID(uuid);
		dto.setLocation("some location");
		dto.setSecondaryId("PROFILE");
		assertThrows(NullPointerException.class, () -> {
			service.create(null);
		});
	}
	
	@Test
	public void testAttachmentCreateNulluid() throws DataException {
		AttachmentDTO dto = new AttachmentDTO();
		dto.setUUID(null);
		dto.setLocation("some location");
		dto.setSecondaryId("PROFILE");
		assertThrows(Exception.class, () -> {
			service.create(dto);
		});
	}

	public void testAttachmentDelete() throws DataException {
		AttachmentDTO dto = new AttachmentDTO();
		final UUID uuid = UUID.randomUUID();
		dto.setUUID(uuid);
		dto.setLocation("some location");
		dto.setSecondaryId("PROFILE");
		service.create(dto);
		List<AttachmentDTO> dtos = service.getAll(new Attachable() {
			@Override
			public UUID getUUID() {
				return uuid;
			} 
		});
		assert dtos.size() == 1  : "Attachment Not Inserted";
		service.delete(dto.getId());
		dtos = service.getAll(new Attachable() {
			@Override
			public UUID getUUID() {
				return uuid;
			} 
		});
		assert dtos.size() == 0  : "Attachment Not deleted";
	}

}

