package com.enoch.dao.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.dao.CheckListInstDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.ChecklistSectionInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.checklist.inst.SectionQuestionInstDTO;
import com.enoch.exception.DataException;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.Ship;
import com.enoch.model.checklist.inst.CheckListInst;
import com.enoch.model.checklist.inst.CheckListSectionInst;
import com.enoch.model.checklist.inst.QCheckListInst;
import com.enoch.model.checklist.inst.QCheckListSectionInst;
import com.enoch.model.checklist.inst.QQuestionInst;
import com.enoch.model.checklist.inst.QSectionInst;
import com.enoch.model.checklist.inst.QSectionQuestionInst;
import com.enoch.model.checklist.inst.QuestionInst;
import com.enoch.model.checklist.inst.SectionInst;
import com.enoch.model.checklist.inst.SectionQuestionInst;
import com.enoch.model.checklist.inst.State;
import com.enoch.service.EntitlementService;
import com.enoch.service.Helper;
import com.enoch.utils.CopyUtil;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;

@Service
public class CheckListInstDAOImpl implements CheckListInstDAO {
	protected final Log logger = LogFactory.getLog(CheckListInstDAOImpl.class);
	@Autowired
	CheckListInstRepo chkInstRepo;
	@Autowired
	SectionInstRepo secInstRepo;
	@Autowired
	QuestionInstRepo queInstRepo;
	@Autowired
	CheckListSecInstRepo chkSecRepo;
	@Autowired
	SecQuestionInstRepo secQueRepo;

	public SectionQuestionInstDTO associate(SectionInstDTO sectDTO, QuestionInstDTO qInst) {
		try {
			SectionQuestionInstDTO checkSecInst = new SectionQuestionInstDTO(null, sectDTO, qInst, true);
			return secQueRepo.save(checkSecInst.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error saving Checklist instance", e);
		}
	}

	public ChecklistSectionInstDTO associate(CheckListInstDTO inst, SectionInstDTO sectDTO) {
		try {
			CheckListSectionInst checkSecInst = new CheckListSectionInst(null, inst.transform(), sectDTO.transform(),
					true);
			return this.chkSecRepo.save(checkSecInst).transform();

		} catch (Exception e) {
			throw new DataException("Error saving Checklist instance", e);
		}
	}

	public QuestionInstDTO create(QuestionInstDTO inst) {
		try {
			inst.setAuditTrail(Helper.getAuditTrail());
			return queInstRepo.saveAndFlush(inst.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error saving Checklist instance", e);
		}
	}

	public SectionInstDTO create(SectionInstDTO inst) {
		try {
			inst.setAuditTrail(Helper.getAuditTrail());
			return secInstRepo.saveAndFlush(inst.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error saving Checklist instance", e);
		}
	}

	public CheckListInstDTO create(CheckListInstDTO inst) {
		try {
			inst.setAuditTrail(Helper.getAuditTrail());
			chkInstRepo.saveAndFlush(inst.transform());
			return chkInstRepo.load(inst.getCheckId()).get().transform();
		} catch (Exception e) {
			throw new DataException("Error saving Checklist instance", e);
		}

	}

	@Override
	public List<QuestionInstDTO> loadQuestions(SectionInstDTO secInst) {
		try {

			return CopyUtil.map(secQueRepo.loadQuestions(secInst.transform()), a -> a.transform());
		} catch (Exception e) {
			String info = String.format("Error loading Questions for section[%s] instance", secInst.getName());
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Override
	public List<SectionInstDTO> loadSections(CheckListInstDTO chkInstDB) {
		try {
			return CopyUtil.map(chkSecRepo.loadSections(chkInstDB.transform()), a -> a.transform());
		} catch (Exception e) {
			String info = String.format("Error loading Checklist[%s] instance", chkInstDB.getCheckId());
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Override
	public Optional<CheckListInstDTO> load(UUID checkId) {
		try {
			return chkInstRepo.load(checkId).map(a -> a.transform());
		} catch (Exception e) {
			String info = String.format("Error loading Checklist[%s] instance", checkId);
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Override
	public Integer getUnAsweredCount(UUID parentUUID) {
		try {
			return queInstRepo.getUnAsweredCount(parentUUID);
		} catch (Exception e) {
			String info = String.format("Error getting unanswered count [%s] instance", parentUUID);
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Override
	public QuestionInstDTO answer(final QuestionInstDTO question) {
		try {
			question.setAuditTrail(Helper.getAuditTrail());
			queInstRepo.save(question.transform());
		} catch (Exception e) {
			String info = String.format("Error saving  answer for question[%s] ", question.getUUID());
			logger.error(info, e);
			throw new DataException(info, e);
		}
		return question;
	}

	@Override
	public Optional<QuestionInstDTO> loadQuestion(UUID uuid) {
		try {
			return queInstRepo.load(uuid).map(a -> a.transform());
		} catch (Exception e) {
			String info = String.format("Error loading question[%s] ", uuid);
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Autowired
	EntitlementService entitlements;

	@Override
	public List<CheckListInstDTO> loadAll() {
		try {
			Boolean canViewAll = entitlements.hasPermission(ApplicationContext.getContext().getLoggedInUser(),
					"comp.VIEW_ALL");
			if (canViewAll) {
				return CopyUtil.map(chkInstRepo.findAll(), (a) -> a.transform());
			} else {
				return CopyUtil.map(chkInstRepo.findAll(ApplicationContext.getContext().getCompany().transform()),
						(a) -> a.transform());
			}

		} catch (Exception e) {
			String info = String.format("Error loading Checklists");
			logger.error(info, e);
			throw new DataException(info, e);
		}
	}

	@Override
	public Optional<CheckListInstDTO> loadLatestChkForMasterUUID(UUID masterChkUUID, ShipDTO shipDTO) {
		try {
			return chkInstRepo.loadLatestChkForMasterUUID(masterChkUUID, shipDTO.transform()).map(a -> a.transform());
		} catch (Exception e) {
			String info = String.format("Error loading Checklists");
			logger.error(info, e);
			return Optional.empty();
		}
	}

	@Override
	public List<CheckListInstDTO> getPreviousChecklist(CheckListInstDTO checkInst, int number) {
		try {
			return CopyUtil.transform(chkInstRepo.getPreviousChecklist(checkInst.getMChk(), checkInst.getVessel().transform(),checkInst.getStartDate(),
					PageRequest.of(0, number)));
		} catch (Exception e) {
			String info = String.format("Error loading Checklists");
			logger.error(info, e);
			throw new DataException(info,e);
		}
	}

	private void updateIfNeeded(CheckListInst checklist,State state) {
		switch(checklist.getState()) {
		case CLOSED:
		case COMPLETED:
			break;
		default:
			checklist.setState(state);
			try{
				chkInstRepo.save(checklist);
			}catch (Exception e) {
				logger.error(e);
			}
		}
	}
	
	@Override
	public void updateCheckListStatusAsOf(Date date, final State state) {
		try {
			QCheckListInst qChk = QCheckListInst.checkListInst;
			BooleanExpression criteria = qChk.completedDate.after(date);
			Iterable<CheckListInst> checklists = chkInstRepo.findAll(criteria);
			checklists.forEach(a -> updateIfNeeded(a, state));
		} catch (Exception e) {
			String info = String.format("Error closing Checklists");
			logger.error(info, e);
			throw new DataException(info,e);
		}
	}

}

interface CheckListInstRepo extends JpaRepository<CheckListInst, Long>, QuerydslPredicateExecutor<CheckListInst>,
		QuerydslBinderCustomizer<QCheckListInst> {

	@Override
	default public void customize(QuerydslBindings bindings, QCheckListInst root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}

	@Query("select q from CheckListInst q  where q.mChk = ?1 and q.vessel =?2 and q.startDate <= ?3 order by q.startDate")
	public List<CheckListInst> getPreviousChecklist(UUID masterChkUUID, Ship ship,Date stdate, PageRequest pageRequest);

	@Query("select q from CheckListInst q where q.company = ?1")
	public List<CheckListInst> findAll(Company company);

	@Query("select q from CheckListInst q  where q.mChk = ?1 and q.vessel =?2 and q.auditTrail.createdDate = "
			+ " (select Max(q2.auditTrail.createdDate )  from CheckListInst q2 where q2.mChk = ?1 and q2.vessel =?2 )")
	public Optional<CheckListInst> loadLatestChkForMasterUUID(UUID masterChkUUID, Ship ship);

	@Query("select q from CheckListInst q where q.checkId = ?1")
	public Optional<CheckListInst> load(UUID checkId);
}

interface SectionInstRepo extends JpaRepository<SectionInst, Long>, QuerydslPredicateExecutor<SectionInst>,
		QuerydslBinderCustomizer<QSectionInst> {

	@Override
	default public void customize(QuerydslBindings bindings, QSectionInst root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}
}

interface QuestionInstRepo extends JpaRepository<QuestionInst, Long>, QuerydslPredicateExecutor<QuestionInst>,
		QuerydslBinderCustomizer<QQuestionInst> {

	@Override
	default public void customize(QuerydslBindings bindings, QQuestionInst root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}

	@Query("select q from QuestionInst q where q.UUID = ?1")
	public Optional<QuestionInst> load(UUID uuid);

	@Query("select count(q) from QuestionInst q where q.parentUUID = ?1 and q.answerDate is null")
	public Integer getUnAsweredCount(UUID parentuuid);

	@Modifying(clearAutomatically = true, flushAutomatically = true)
	@Query("update QuestionInst q   set  q.answer = ?2 , q.answerBy = ?3,  q.answerDate = ?4 where q.UUID = ?1")
	public void update(UUID uuid, String answer, String loggedInUser, Date date);
}

interface CheckListSecInstRepo extends JpaRepository<CheckListSectionInst, Long>,
		QuerydslPredicateExecutor<CheckListSectionInst>, QuerydslBinderCustomizer<QCheckListSectionInst> {

	@Override
	default public void customize(QuerydslBindings bindings, QCheckListSectionInst root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}

	@Query("select q.section from CheckListSectionInst q where q.checklist = ?1")
	public List<SectionInst> loadSections(CheckListInst chkInstDB);
	
}

interface SecQuestionInstRepo extends JpaRepository<SectionQuestionInst, Long>,
		QuerydslPredicateExecutor<SectionQuestionInst>, QuerydslBinderCustomizer<QSectionQuestionInst> {

	@Override
	default public void customize(QuerydslBindings bindings, QSectionQuestionInst root) {
		bindings.bind(String.class)
				.first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
		bindings.excluding(root.id);
	}

	@Query("select q.question from SectionQuestionInst q where q.section = ?1")
	public List<QuestionInst> loadQuestions(SectionInst transform);
}
