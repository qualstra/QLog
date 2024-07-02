package com.enoch.dao.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.model.User;
import com.enoch.model.checklist.CheckList;
import com.enoch.model.checklist.CheckListSection;
import com.enoch.model.checklist.QCheckList;
import com.enoch.model.checklist.Question;
import com.enoch.model.checklist.Section;
import com.enoch.model.checklist.SectionQuestion;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;



interface ChecklistRepo extends JpaRepository<CheckList, Long>, QuerydslPredicateExecutor<CheckList>, QuerydslBinderCustomizer<QCheckList> { 

	@Query("select chksec from com.enoch.model.checklist.CheckListSection chksec where chksec.checklist = ?1")
	List<CheckListSection> loadAllSections(CheckList transform);
	@Query("select chklst from com.enoch.model.checklist.CheckList chklst where chklst.checkId = ?1")
	List<CheckList> loadByCheckListId(UUID checkId);
	
	@Override
	default public void customize(QuerydslBindings bindings, QCheckList root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.preProcessor);
	}

}
interface SectionRepo extends JpaRepository<Section, Long> {}
interface ChecklistSectionRepo extends JpaRepository<CheckListSection, Long> {}
interface SectionQuestionRepo extends JpaRepository<SectionQuestion, Long> {
	@Query("select secQue from com.enoch.model.checklist.SectionQuestion secQue where secQue.section = ?2 and secQue.question = ?1")
	Optional<SectionQuestion> load(Question question, Section section);
	@Query("select secQue.question from com.enoch.model.checklist.SectionQuestion secQue where secQue.section = ?1")
	List<Question> loadAllQuestions(Section transform);
}




