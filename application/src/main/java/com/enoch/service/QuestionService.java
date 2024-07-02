package com.enoch.service;

import java.util.Collection;

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;

public interface QuestionService {

	QuestionDTO save(QuestionDTO q, SectionDTO secDto);

	Collection<QuestionDTO> loadAllQuestios(SectionDTO a);

}
