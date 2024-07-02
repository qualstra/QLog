package com.enoch.dao.impl;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.enoch.model.checklist.Question;

interface QueRepository extends JpaRepository<Question, Long>{
	@Query("select max(q.queId) from Question q")
	public Integer getMaxQueId();
	@Query("select q from Question q where q.queId = ?1")
	public List<Question> getByQueId(Integer id);
	@Query("select max(q.version) from Question q where q.queId = ?1")
	public Integer getMaxQueVer(Integer id);
	
}