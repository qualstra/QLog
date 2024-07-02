package com.enoch.template.model;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.enoch.model.AuditTrail;
import com.enoch.template.dto.TemplateDTO;
import com.enoch.utils.Transform;

import lombok.Data;

@Data
@Entity(name = "TEMPLATE")
public class Template implements Transform<TemplateDTO>{
	@Id
	@GenericGenerator(name = "gen", strategy = "increment")
	@GeneratedValue(generator = "gen")
	public Long id;
	@Column(name = "UID", nullable = false, updatable = false)
	private UUID UUID;
	@Column(name = "QUAL", nullable = true, updatable = false)
	private UUID qualifier;
	@Column(name = "TYP", nullable = true)
	private String type;
	@Column(name = "TEMPLATE", nullable = false, updatable = true)
	private String template;
	@Embedded
	private AuditTrail auditTrail;
	
	@Override
	public TemplateDTO transform() {
		TemplateDTO template = new TemplateDTO();
		BeanUtils.copyProperties(this, template);
		return template;
	}
}
