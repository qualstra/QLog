package com.enoch.template.dto;

import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.enoch.model.AuditTrail;
import com.enoch.template.model.Template;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
public class TemplateDTO implements Transform<Template> {
	private Long id;
	private UUID UUID;
	private UUID qualifier;
	private String type;
	private String template;
	private AuditTrail auditTrail;

	@Override
	public Template transform() {
		Template template = new Template();
		BeanUtils.copyProperties(this, template);
		return template;
	}
}
