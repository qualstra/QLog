package com.enoch.attr.model;

import java.util.UUID;

import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@QueryEntity
@NoArgsConstructor
public class Attribute {
	private String id;
	private UUID uid;
	private String entity;
	private UUID entityId;
	private UUID secId;
	private String name;
	private String code;
	private String displayText;
	private String type;
	private String value;
	private String defVal;
	private String grp;
	private int order;
	private int grpOrder;
}

