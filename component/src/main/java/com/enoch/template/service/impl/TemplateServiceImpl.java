package com.enoch.template.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.enoch.template.Templatable;
import com.enoch.template.dao.TemplateDAO;
import com.enoch.template.dto.TemplateDTO;
import com.enoch.template.service.TemplateService;

@Component
public class TemplateServiceImpl implements TemplateService {
	
	@Autowired
	TemplateDAO templateDAO;
	public TemplateDTO load(Templatable templatable) {
		return templateDAO.load(templatable);
	}
	public TemplateDTO save(TemplateDTO templatable) {
		return templateDAO.save(templatable);
	}
}
