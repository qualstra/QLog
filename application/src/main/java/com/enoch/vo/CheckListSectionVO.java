/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.vo;

import java.util.Collections;
import java.util.List;

import com.enoch.model.AuditTrail;
import com.enoch.model.checklist.State;

import lombok.Data;

/**
 *
 * @author Qualstra
 */

@Data
public class CheckListSectionVO{
	
    private Long id;
    private Long checkListId;
    
	private Integer version;
	private String sectionName;
	private String desc;
	private String longDescription;
	private Integer slno;
    
	private State state;
	private Boolean active;
	private String preProcessor;
	private String postProcessor;
	private AuditTrail auditTrail;
	
	private List<MasterChecklistItemVO> masterChecklistItemVOs = Collections.emptyList();


}
