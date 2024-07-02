package com.enoch.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.enoch.Permission;
import com.enoch.dao.QuestionDAO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;
import com.enoch.exception.EntityException;
import com.enoch.model.Helper;
import com.enoch.model.checklist.Question;
import com.enoch.model.checklist.Section;
import com.enoch.model.checklist.SectionQuestion;
import com.enoch.model.checklist.State;
import com.enoch.utils.CopyUtil;

@Repository("QuestionDAO")
public class QuestionDAOImpl implements QuestionDAO {

	@Autowired
	private QueRepository repository;
	@Permission({"que.VIEW"})
	public List<QuestionDTO> findAll() {
		List<QuestionDTO> result = repository.findAll().stream().map(some -> {
			QuestionDTO dto = new QuestionDTO();
			return dto;
		}).collect(Collectors.toList());
		return result;
	}
	//@Permission({"que.CREATE"})
	public QuestionDTO save(QuestionDTO srcQue) {
		srcQue.setAuditTrail(Helper.getAuditTrail());
		Question question = srcQue.transform();
		
		if (srcQue.getId() != null) {
			Optional<QuestionDTO> queFromDB = findById(srcQue.getId());
			if (queFromDB.isPresent()) {
				if (queFromDB.get().getState() == State.PUBLISHED)
					throw new EntityException(Question.class, "Cannot modify a published Entity");
			}
		} else {
			Integer maxId = Optional.ofNullable(repository.getMaxQueId()).orElse(0);
			question.setQueId(maxId.intValue() + 1);
			question.setVersion(1);
		}
		question = repository.saveAndFlush(question);
		return question.transform();
	}
	@Autowired
	SectionQuestionRepo secQueRepo;
	public void attach(QuestionDTO srcQue,SectionDTO sectionIn) {
		Question question = srcQue.transform();
		Section section = sectionIn.transform();
		Optional<SectionQuestion> secQue = secQueRepo.load(question,section);
		if(secQue.isPresent())
			throw new DataException("Question already attached");
		SectionQuestion secQues = new SectionQuestion(null, section, question, true);
		secQueRepo.save(secQues);
		
	}
	
	@Permission({"que.EDIT"})
	public QuestionDTO makeEditable(QuestionDTO srcQue) {
		Question question = srcQue.transform();
		Integer maxVerId = Optional.ofNullable(repository.getMaxQueVer(srcQue.getQueId())).orElse(0);
		question.setVersion(maxVerId + 1);
		question.setState(State.DRAFT);
		question.setId(null);
		question.setAuditTrail(Helper.getAuditTrail());
		return repository.save(question).transform();

	}
	@Permission({"que.VIEW"})
	public Optional<QuestionDTO> findByQueId(long l) {
		return repository.getByQueId((int) l).stream().max((a, b) -> a.getVersion().compareTo(b.getVersion()))
				.map(mapper -> mapper.transform());
	}
	@Permission({"que.VIEW"})
	public List<QuestionDTO> findAllByQueId(long l) {
		return repository.getByQueId((int) l).stream().map(mapper -> mapper.transform())
				.collect(Collectors.toList());
	}
	@Permission({"que.VIEW"})
	public Optional<QuestionDTO> findById(long l) {
		return repository.findById(l).map(mapper -> mapper.transform());
	}
	@Permission({"que.DELETE"})
	public void deleteById(long l) {
		Optional<Question> que = repository.findById(l);
		que.ifPresent(question -> {
			question.setActive(false);
			repository.save(question);
		});
	}
	@Override
	public List<QuestionDTO> loadAllQuestions(SectionDTO a) {
		return 	CopyUtil.transform(secQueRepo.loadAllQuestions(a.transform()));
	}

}