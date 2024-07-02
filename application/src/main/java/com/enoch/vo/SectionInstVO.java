package com.enoch.vo;

import java.util.List;
import java.util.UUID;

import com.enoch.model.AuditTrail;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SectionInstVO {
	private Long id;
	private Integer slNo;
	private UUID masterUUID;
	private String name;
	private CompanyVO company;
	private ShipVO vessel;
	private AuditTrail auditTrail;
	
	private List<QuestionInstVO> questions;
	
	private Integer ansQuesCount;
	private Integer quesCount;
}
