/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.enoch.vo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.enoch.model.checklist.QuestionType;

import lombok.Data;

/**
 *
 * @author Qualstra
 */
@Data
public class MasterChecklistItemVO {

    
	private UUID id;
    private Long sectionId;
    private String responsibility;
    private String questionDesc;
    private String attachment;
    private String remark;
    private QuestionType type;
    private UUID parentId;
    private Integer slno;
	private Integer order;
    private Map<?,?> data;

    private List<MasterChecklistItemVO> checkListItemChildVOs = new ArrayList<MasterChecklistItemVO>(0);

}
