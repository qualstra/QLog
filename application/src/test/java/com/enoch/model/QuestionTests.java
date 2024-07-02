package com.enoch.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
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
import com.enoch.exception.EntityException;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.State;

//@SpringBootTest
@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class QuestionTests {
	/*
	 * @BeforeClass public static void init() {
	 * ApplicationContext.initContext("TestUser"); }
	 */
	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private QuestionDAO questionRepository;
	Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,"it@enoch.com","",
			Helper.getAuditTrail());

	// write test cases here
	@Test
	public void testCreateQuestion() {
		QuestionDTO que = new QuestionBuilder(QuestionType.String, "This sample text").
				setAttrib("Option", new JSONObject("{\"min-len\":\"100\",\"max-len\":\"500\"}"))
				.build();
		que.setCompany(comp.transform());
		que.setState(State.DRAFT);
		QuestionDTO persistedQue = questionRepository.save(que);
		Integer queId = persistedQue.getQueId();
		Long id = persistedQue.getId();
		List<QuestionDTO> questions = questionRepository.findAllByQueId(queId);
		
		assert questions.size() == 1 : "More than one version present";

	}
	@Test
	public void testLoadQuestion() {
		QuestionDTO que = new QuestionBuilder(QuestionType.String, "This sample text").
				setAttrib("Option", new JSONObject("{\"min-len\":\"100\",\"max-len\":\"500\"}")).build();
		que.setCompany(comp.transform());
		que.setState(State.DRAFT);
		QuestionDTO persistedQue = questionRepository.save(que);
		Integer queId = persistedQue.getQueId();
		Long id = persistedQue.getId();
		List<QuestionDTO> questions = questionRepository.findAllByQueId(queId);
		assert questions.size() == 1 : "More than one version present";
		Optional<QuestionDTO> persistedOptQueV1 = questionRepository.findById(id);
		assert persistedOptQueV1.isPresent() : "Not able to load the questions";

	}
	@Test
	public void testUpdatedQuestion() {
		QuestionDTO que = new QuestionBuilder(QuestionType.String, "This sample text").
				setAttrib("Option", new JSONObject("{\"min-len\":\"100\",\"max-len\":\"500\"}")).build();
		que.setState(State.DRAFT);
		que.setCompany(comp.transform());
		QuestionDTO persistedQue = questionRepository.save(que);
		Integer queId = persistedQue.getQueId();
		Long id = persistedQue.getId();

		Optional<QuestionDTO> persistedOptQueV1 = questionRepository.findById(id);
		QuestionDTO persistedQueV1 = persistedOptQueV1.get();
		persistedQueV1.setPostProcessor("somupdate");

		questionRepository.save(persistedQueV1);

		List<QuestionDTO>  questions = questionRepository.findAllByQueId(queId);
		assert questions.size() == 1 : "Expected 1 versions";

		Optional<QuestionDTO> persistedOptQueV2 = questionRepository.findByQueId(queId);

		assert persistedOptQueV2.get().getPostProcessor().equalsIgnoreCase("somupdate") : "Update Failed";

	}
	@Test
	public void testCreateVersQuestion() {
		QuestionDTO que = new QuestionBuilder(QuestionType.String, "This sample text").
				setAttrib("Option", new JSONObject("{\"min-len\":\"100\",\"max-len\":\"500\"}")).build();
		que.setState(State.DRAFT);
		que.setCompany(comp.transform());
		QuestionDTO persistedQue = questionRepository.save(que);
		Integer queId = persistedQue.getQueId();
		Long id = persistedQue.getId();
		List<QuestionDTO> questions = questionRepository.findAllByQueId(queId);
		
		assert questions.size() == 1 : "More than one version present";

		Optional<QuestionDTO> persistedOptQueV2 = questionRepository.findByQueId(queId);
		QuestionDTO persistedQueV2 = persistedOptQueV2.get();
		persistedQueV2.setState(State.PUBLISHED);
		questionRepository.save(persistedQueV2);

		questions = questionRepository.findAllByQueId(queId);
		assert questions.size() == 1 : "Expected 1 versions";

		Optional<QuestionDTO> persistedOptQueV3 = questionRepository.findByQueId(queId);
		QuestionDTO persistedQueV3 = persistedOptQueV3.get();
		persistedQueV3.setQuestionText("Some def change");
		questionRepository.makeEditable(persistedQueV3);
		persistedOptQueV3 = questionRepository.findByQueId(queId);
		persistedQueV3 = persistedOptQueV3.get();
		persistedQueV3.setQuestionText("Some def change");
		questionRepository.save(persistedQueV3);

		assert questionRepository.findAllByQueId(queId).size() == 2 : "Expected 2 versions";

	}

	@Test
	public void testEditVersQuestion() {
		QuestionDTO que = new QuestionBuilder(QuestionType.String, "This sample text").
				setAttrib("Option", new JSONObject("{\"min-len\":\"100\",\"max-len\":\"500\"}")).build();
		que.setState(State.PUBLISHED);
		que.setCompany(comp.transform());
		QuestionDTO persistedQue = questionRepository.save(que);
		Integer queId = persistedQue.getQueId();
		Long id = persistedQue.getId();
		List<QuestionDTO> questions = questionRepository.findAllByQueId(queId);
		
		assert questions.size() == 1 : "Expected 1 versions";

		assertThrows(EntityException.class, () -> {
			Optional<QuestionDTO> persistedOptQueV3 = questionRepository.findByQueId(queId);
			QuestionDTO persistedQueV3 = persistedOptQueV3.get();
			persistedQueV3.setQuestionText("Some def change");
			questionRepository.save(persistedQueV3);
		});


		Optional<QuestionDTO> persistedOptQueV3 = questionRepository.findByQueId(queId);
		QuestionDTO persistedQueV3 = persistedOptQueV3.get();
		persistedQueV3.setQuestionText("Some def change");
		questionRepository.makeEditable(persistedQueV3);
		persistedOptQueV3 = questionRepository.findByQueId(queId);
		persistedQueV3 = persistedOptQueV3.get();
		persistedQueV3.setQuestionText("Some def change");
		questionRepository.save(persistedQueV3);

		assert questionRepository.findAllByQueId(queId).size() == 2 : "Expected 2 versions";

	}

}