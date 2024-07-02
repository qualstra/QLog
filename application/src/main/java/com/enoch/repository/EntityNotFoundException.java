package com.enoch.repository;

public class EntityNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9182122468228846285L;

	EntityNotFoundException(String entityType, Integer id){
		super(String.format("Could not find %s : %d " , entityType,id));
	}
	EntityNotFoundException(Integer id) {
		this("Entity",id);
	}
}