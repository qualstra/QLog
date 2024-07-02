package com.enoch.dto.checklist;

import com.enoch.dto.CompanyDTO;

import lombok.Data;

@Data
public class CheckListSearchDTO {
	private CompanyDTO company;
	private String checkListCode;
}
