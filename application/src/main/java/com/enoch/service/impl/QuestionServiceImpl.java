package com.enoch.service.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.QuestionDAO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;
import com.enoch.service.QuestionService;
import com.enoch.validator.ValidatorFactory;

@Service
public class QuestionServiceImpl implements QuestionService {

	@Autowired
	QuestionDAO dao;
	@Autowired
	ValidatorFactory factory;
	public QuestionDTO save(QuestionDTO q, SectionDTO secDto) {
		try {
			if (q.getId() == null) {
				if(q.getCompany() == null)
					q.setCompany(ApplicationContext.getContext().getCompany());
				q.setUuid(UUID.randomUUID());
			}
			factory.getQueValidator(q.getType()).validate(q);
			QuestionDTO ques = dao.save(q);
			dao.attach(ques, secDto);
			return ques;
		} catch (DataException e) {
			throw new ServiceException("Error saving question", e);
		}
	}

	
	@Override
	public Collection<QuestionDTO> loadAllQuestios(SectionDTO a) {
		try {
			List<QuestionDTO> allQue = dao.loadAllQuestions(a);
			Map<UUID, QuestionDTO> ques = allQue.stream()
					.collect(Collectors.toMap(questionDTO -> questionDTO.getUuid(), q -> q));
			Set<UUID> keys = new HashSet<UUID>(ques.keySet());
			keys.forEach(key -> {
				QuestionDTO que = ques.get(key);
				if ((que.getParentUUId() != null)) {
					// THis is child Question
					QuestionDTO parentQue = ques.get(que.getParentUUId());
					parentQue.addChildQuestion(que);
					ques.remove(key);
				}
			});
			return ques.values();
		} catch (DataException e) {
			throw new ServiceException("Error loading questions for Section " + a.getId(), e);
		}
	}

}
