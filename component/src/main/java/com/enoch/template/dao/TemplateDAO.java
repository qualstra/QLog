package com.enoch.template.dao;

import com.enoch.template.Templatable;
import com.enoch.template.dto.TemplateDTO;

public interface TemplateDAO {

	TemplateDTO load(Templatable templatable);

	TemplateDTO save(TemplateDTO templatable);

}
