package com.enoch.vo;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class ChecklistUploadVO extends CompanyVO{
	
	private MultipartFile file;

}
