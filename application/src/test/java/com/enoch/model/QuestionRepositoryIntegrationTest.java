package com.enoch.model;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.builder.QuestionBuilder;
import com.enoch.dao.QuestionDAO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;

import lombok.val;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class QuestionRepositoryIntegrationTest {
	
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private QuestionDAO questionRepository;
	Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,"it@enoch.com","",
			Helper.getAuditTrail());

	// write test cases here
	@Test
	public void test() {
		val questions = questionRepository.findAll();
		assert questions != null && questions.size() == 1 : "Only one Question initilized";
	}

	@Test
	public void testSimpleQue() {
		val question = new QuestionBuilder("This is for test").build();
		question.setCompany(comp.transform());
		question.setUuid(UUID.randomUUID());
		
		val persistedQue = questionRepository.save(question);
		assert persistedQue != null : "Not able to create Question";
	}

	@Test
	public void testSimpleQueLoad() {
		val persistedQue = questionRepository.findById(1L);
		assert persistedQue.isPresent() : "Not able to load Question";
	}

	@Test
	public void testSimpleQueDelete() {
		Optional<QuestionDTO> persistedQue = questionRepository.findById(1L);
		assert persistedQue.isPresent() : "Question not found";
		questionRepository.deleteById(1L);
		 persistedQue = questionRepository.findById(1L);
		assert persistedQue.isPresent() : "Question should not be deleted it should be deactivated";
		assert persistedQue.isPresent() && !persistedQue.get().getActive() : "when deleted Question should be inactive";
	}

	@Test
	public void testSimpleQueUpdate() {
		Optional<QuestionDTO> persistedQue = questionRepository.findById(1L);
		assert persistedQue.isPresent() : "Question should be there";
		QuestionDTO que = persistedQue.get();
		Integer queId = que.getQueId();
		String queText = que.getQuestionText();
		que.setQuestionText("Changing Text");
		que.setCompany(comp.transform());
		que.setUuid(UUID.randomUUID());

		questionRepository.save(que);
		persistedQue = questionRepository.findById(1L);
		que = persistedQue.get();
		assert !que.getQuestionText().equalsIgnoreCase(queText) : "Same question not updated";
		assert questionRepository.findAll().size() == 1 : "Question  inserted";
		
		persistedQue = questionRepository.findByQueId(queId);
		que = persistedQue.get();
		que.setState(State.PUBLISHED);
		questionRepository.makeEditable(questionRepository.save(que));
		assert questionRepository.findAll().size() == 2 : "Question not inserted";
		
	}

	@Test
	public void testBooleanQue() {
		val question = new QuestionBuilder("This is for Boolean Question").setType(QuestionType.Boolean).build();
		question.setCompany(comp.transform());
		question.setUuid(UUID.randomUUID());

		val persistedQue = questionRepository.save(question);
		Long id = persistedQue.getId();

		assert persistedQue != null : "Not able to create Question";
	}

	@Test
	public void testQueById() {
		List<QuestionDTO> questions = questionRepository.findAllByQueId(1);
		assert questions != null && questions.size() == 1 : "Only one Question initilized";
		val question = questionRepository.findById(1).get();
		question.setQuestionHelp("Help");
		questionRepository.save(question);
		questions = questionRepository.findAllByQueId(1);
		assert questions != null && questions.size() == 1 : "one Question should have 1 version";
//		assert questions.stream().filter(s -> s.getActive()).count() ==0 : "Only one question should be active";
		
	}
}