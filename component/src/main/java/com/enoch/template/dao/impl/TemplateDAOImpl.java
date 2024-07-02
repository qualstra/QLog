package com.enoch.template.dao.impl;

import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.enoch.template.Templatable;
import com.enoch.template.dao.TemplateDAO;
import com.enoch.template.dto.TemplateDTO;
import com.enoch.template.model.Template;
@Service
public class TemplateDAOImpl implements TemplateDAO {
	protected final Log logger = LogFactory.getLog(TemplateDAOImpl.class);
	@Autowired
	TemplateRepo repo;

	public TemplateDTO load(final Templatable templatable) {
		return repo.findTemplate(templatable.getUUID(), templatable.getIdentifier())
				.orElseGet(() -> repo.findTemplate(templatable.getUUID())).transform();
	}

	public TemplateDTO save(TemplateDTO templatable) {
		return repo.save(templatable.transform()).transform();
	}

}

interface TemplateRepo extends JpaRepository<Template, Long> {
	@Query("select template from com.enoch.template.model.Template template where template.UUID = ?1 and template.qualifier=?2")
	Optional<Template> findTemplate(UUID uuid, UUID qualifier);

	@Query("select template from com.enoch.template.model.Template template where template.UUID = ?1 ")
	Template findTemplate(UUID uuid);

}