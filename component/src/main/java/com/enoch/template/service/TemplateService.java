package com.enoch.template.service;

import com.enoch.template.Templatable;
import com.enoch.template.dto.TemplateDTO;

public interface TemplateService {
	public TemplateDTO load(Templatable templatable);
	public TemplateDTO save(TemplateDTO template);
}
