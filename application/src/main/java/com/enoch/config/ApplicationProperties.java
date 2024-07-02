package com.enoch.config;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ImportResource;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties("app")
@ImportResource("classpath:com/enoch/config/dflt/default-config.xml")
@Data
public class ApplicationProperties {

	private SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
	private String resourceFolder;

	private Integer min_len;
	private Integer max_len;
	
	private Integer min_attachments;
	private Integer max_attachments;
	
	private Integer prev_summary_count_chart;
	@Autowired
	private List defaultChecklists;
	@Autowired
	private Map voyageChecklists;
	public DateFormat getDateDormat() {
		return format;
	}
}