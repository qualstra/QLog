package com.enoch.model;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import com.querydsl.core.annotations.QueryEntity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@QueryEntity
@Document
@CompoundIndexes({ @CompoundIndex(name = "ind", def = "{'uid' : 1, 'entityId': 1}") })
public class Attribute {
	@Id
	private String id;
	@Indexed(direction = IndexDirection.ASCENDING)
	private UUID uid;
	@Field
	private String entity;
	@Indexed(direction = IndexDirection.ASCENDING)
	private UUID entityId;
	@Indexed(direction = IndexDirection.ASCENDING)
	private UUID secId;
	@Field
	private String name;
	@Field
	private String code;
	@Field
	private String displayText;
	@Field
	private String type;
	@Field
	private String value;
	@Field
	private String defVal;
	@Field
	private String grp;
	@Field
	private int order;
	@Field
	private int grpOrder;
}
