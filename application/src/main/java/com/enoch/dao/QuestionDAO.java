package com.enoch.dao;

import java.util.List;
import java.util.Optional;

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;

public interface QuestionDAO {
	public QuestionDTO save(QuestionDTO srcQue) ;
	public void attach(QuestionDTO srcQue,SectionDTO section) ;
	
	public Optional<QuestionDTO> findByQueId(long l);
	public List<QuestionDTO> findAllByQueId(long l);
	public Optional<QuestionDTO> findById(long l) ;
	

	public List<QuestionDTO> findAll() ;
	public QuestionDTO makeEditable(QuestionDTO srcQue) ;
	
	public void deleteById(long l) ;
	public List<QuestionDTO> loadAllQuestions(SectionDTO a);
}
