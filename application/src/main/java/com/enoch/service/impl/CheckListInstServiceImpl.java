package com.enoch.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.builder.QuestionConstants;
import com.enoch.common.exception.ServiceException;
import com.enoch.common.exception.checklist.WeightageNotSupportedException;
import com.enoch.dao.CheckListInstDAO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.checklist.inst.SectionQuestionInstDTO;
import com.enoch.events.CheckListEvent;
import com.enoch.events.publisher.CheckListInstEventPublisher;
import com.enoch.exception.DataException;
import com.enoch.model.Helper;
import com.enoch.model.checklist.inst.State;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.CompanyService;
import com.enoch.service.QuestionService;
import com.enoch.service.processor.Processor;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;
import com.enoch.validator.QuestionValidatonHelper;

@Service
public class CheckListInstServiceImpl implements CheckListInstService {

	protected final Log logger = LogFactory.getLog(CheckListInstServiceImpl.class);
	@Autowired
	CheckListService mChkservice;
	@Autowired
	CompanyService compService;
	@Autowired
	QuestionService qService;
	@Autowired
	CheckListInstDAO checkInstDAO;
	@Autowired
	QuestionValidatonHelper qValidatorHelper;
	@Autowired
	CheckListInstEventPublisher checklistPub;
	private void checklistValidator(CheckListInstDTO chklist) {
		if(chklist.getState() == State.COMPLETED || chklist.getState() == State.CLOSED) {
			return;
		}
		if (chklist.getAnsQuesCount() != null && chklist.getQuesCount() != null) {
			if (chklist.getAnsQuesCount().compareTo(chklist.getQuesCount()) == 0 && (chklist.getState() != State.CLOSED
					&& chklist.getState() != State.COMPLETED && chklist.getState() != State.PENDING_SUBMISSION)) {
				chklist.setState(State.PENDING_SUBMISSION);
				BeanUtils.copyProperties(save(chklist), chklist);
			} else if (chklist.getAnsQuesCount() == 0 && chklist.getState() != State.NOTSTARTED) {
				chklist.setState(State.NOTSTARTED);
				BeanUtils.copyProperties(save(chklist), chklist);
				
			} else if (chklist.getAnsQuesCount() == 1 && chklist.getState() != State.STARTED) {
				chklist.setState(State.STARTED);
				BeanUtils.copyProperties(save(chklist), chklist);
			} else if (chklist.getAnsQuesCount() > 1  && chklist.getAnsQuesCount() < chklist.getQuesCount() && chklist.getState() != State.INPROGRESS) {
				chklist.setState(State.INPROGRESS);
				BeanUtils.copyProperties(save(chklist), chklist);
			}
		}
	}


	@Override
	public Optional<CheckListInstDTO> loadCheckListInst(UUID checkId) {
		try {
			Optional<CheckListInstDTO> chk = checkInstDAO.load(checkId);
			chk.ifPresent(chklist -> {
				checklistValidator(chklist);
			});
			return chk;
		} catch (DataException e) {
			String info = String.format("Error loading the checklist %s", checkId);
			logger.info(info);
			throw new ServiceException(info, e);
		}
	}

	@Override
	public QuestionInstDTO answer(UUID uuid, final String answer, final boolean submit) {
		try {
			return checkInstDAO.loadQuestion(uuid).map(question -> {
				question.setAnswer(answer);
				if (submit) {
					question.setAnswerDate(new Date());
					question.setAnswerBy(ApplicationContext.getContext().getLoggedInUser());
					qValidatorHelper.validateAnswer(question);
				} else {
					question.setAnswerDate(null);
				}
				QuestionInstDTO ret = checkInstDAO.answer(question);
				if (question.getParentUUID() != null) {
					Integer unansweredQueCount = checkInstDAO.getUnAsweredCount(question.getParentUUID());
					if (unansweredQueCount != null && unansweredQueCount.intValue() == 0) {
						checkInstDAO.loadQuestion(question.getParentUUID()).map(pQue -> {
							if (queUtils.checkForAutoSave(pQue)) {
								pQue.setAnswerDate(new Date());
								pQue.setAnswerBy(ApplicationContext.getContext().getLoggedInUser());
								checkInstDAO.answer(pQue);
							}
							return pQue;
						});
					}
				}
				return ret;
			}).orElseThrow(() -> new ServiceException("unable to find question." + uuid));
		} catch (DataException e) {
			String info = String.format("Error saving answer for Question %s", uuid);
			logger.info(info);
			throw new ServiceException(info, e);
		}
	}

	QuestionUtils queUtils = new QuestionUtils();

	@Override
	public List<SectionInstDTO> loadSections(CheckListInstDTO chkInstDB) {
		try {
			List<SectionInstDTO> res = checkInstDAO.loadSections(chkInstDB);
			res.sort((tg, th) -> tg.getSlNo().compareTo(th.getSlNo()));
			return res;
		} catch (DataException e) {
			String info = String.format("Error loading Sections for checklist %s[UUID-%s]", chkInstDB.getName(),
					chkInstDB.getCheckId());
			logger.info(info);
			throw new ServiceException(info, e);
		}
	}

	@Override
	public Collection<QuestionInstDTO> loadQuestions(SectionInstDTO secInst, Function<QuestionInstDTO,Boolean> allow) {
		try {
			List<QuestionInstDTO> allQue = checkInstDAO.loadQuestions(secInst);
			Map<UUID, QuestionInstDTO> ques = allQue.stream()
					.collect(Collectors.toMap(questionDTO -> questionDTO.getUUID(), q -> q));
			Set<UUID> keys = new HashSet<UUID>(ques.keySet());
			keys.forEach(key -> {
				QuestionInstDTO que = ques.get(key);
				
				if ((que.getParentUUID() != null)) {
					// THis is child Question
					QuestionInstDTO parentQue = ques.get(que.getParentUUID());
					parentQue.addChildQuestion(que);
					ques.remove(key);
				}
			});
			List<QuestionInstDTO> res = new ArrayList<QuestionInstDTO>(ques.values());
			res.sort((tg, th) -> tg.getOrder().compareTo(th.getOrder()));
			res = res.stream().filter(que ->{
				boolean canfilter = allow.apply(que);
				if(canfilter && que.getChildQuestions() != null) {
					que.setChildQuestions(que.getChildQuestions().stream().filter(allow::apply).collect(Collectors.toList()));
				}
				return canfilter;
			}).collect(Collectors.toList());
			res.forEach(que -> que.getChildQuestions().sort((tg, th) -> tg.getOrder().compareTo(th.getOrder())));
			
			return res;
		} catch (DataException e) {
			String info = String.format("Error loading the Questions for section %s", secInst.getName());
			logger.info(info);
			throw new ServiceException(info, e);
		}
	}

	
	/**
	 * Creates an instance for the checklist
	 * 
	 * @param checklistDTO
	 */
	public CheckListInstDTO clone(CheckListInstCreateDTO chkCrtDTO) {
		validate(chkCrtDTO);
		CheckListDTO checklistDTO = mChkservice.findCheckListByUID(chkCrtDTO.getMasterChkUUID()).stream()
				.max((a, b) -> a.getVersion().compareTo(b.getVersion())).get();
		Optional<CheckListInstDTO> latestChecklist = this.checkInstDAO
				.loadLatestChkForMasterUUID(chkCrtDTO.getMasterChkUUID(), chkCrtDTO.getVessel());

		latestChecklist.ifPresent(chkInst -> {
			switch (chkInst.getState()) {
			case INPROGRESS:
			case NOTSTARTED:
			case STARTED:
			case PENDING_SUBMISSION:
				throw new ServiceException(" Previous checklist is open please close the checklist.");
			default:
			}
		});
		final CheckListInstDTO inst = clone(chkCrtDTO, checklistDTO, latestChecklist);
		Optional<Map<UUID, QuestionInstDTO>> prevCheckQMap = QuestionUtils.isHintEnabled(checklistDTO.getData())
				? latestChecklist.map(chkInst -> {
					Map<UUID, QuestionInstDTO> map = new HashMap<UUID, QuestionInstDTO>();
					checkInstDAO.loadSections(chkInst).forEach(secInst -> {
						checkInstDAO.loadQuestions(secInst)
								.forEach(queInst -> map.put(queInst.getMasterUUID(), queInst));
					});
					return map;
				})
				: Optional.empty();
		List<SectionQuestionInstDTO> secQuestions = new ArrayList();
		List<SectionDTO> res = mChkservice.loadAllSections(checklistDTO);
		res.forEach(section -> {
			SectionInstDTO sectDTO = clone(chkCrtDTO, section);
			associate(inst, sectDTO);
			Collection<QuestionDTO> questions = qService.loadAllQuestios(section);
			questions.forEach(question -> {
				if(isApplicable(question,chkCrtDTO)) {
					QuestionInstDTO pInst_ = clone(chkCrtDTO, question);
					updateIfNeeded(prevCheckQMap, pInst_);
					final QuestionInstDTO pInst = this.checkInstDAO.create(pInst_);
					question.getChildQuestions().forEach(cQue -> {
						if(isApplicable(cQue,chkCrtDTO)) {
							QuestionInstDTO chInst = clone(chkCrtDTO, cQue);
							chInst.setParentUUID(pInst.getUUID());
							updateIfNeeded(prevCheckQMap, chInst);
							chInst = this.checkInstDAO.create(chInst);
							secQuestions.add(associate(sectDTO, chInst));
						}
					});
					secQuestions.add(associate(sectDTO, pInst));
				}
			});
		});
		checklistPub.publishEvent(new CheckListEvent(inst,secQuestions));
		return inst;

	}

	private boolean isApplicable(QuestionDTO question,CheckListInstCreateDTO chkCrtDTO) {
		String respo = question.getResponsibility();
		boolean notFiltered = true;
		if(respo != null) {
			String vesseType = new JSONObject(respo).optString("vessel-type",null); 
			notFiltered = vesseType == null ? true : chkCrtDTO.getVessel().getVesselType().equalsIgnoreCase(vesseType);
		}
		return notFiltered;
	}
	
	private void updateChkData(CheckListInstDTO newInst, CheckListInstDTO oldInst) {
		JSONObject oldData = new JSONObject(oldInst.getData());
		JSONObject jchkSum = new JSONObject(newInst.getData());
		try {
			jchkSum.put(QuestionConstants.WEIGHTAGE_CALC, 0);
			jchkSum.put(QuestionConstants.WEIGHTAGE_APPLIED, 0);
			jchkSum.put(QuestionConstants.CHECK_SENTIMENT, oldData.opt(QuestionConstants.CHECK_SENTIMENT_APPLIED));
			jchkSum.put(QuestionConstants.CHECK_SENTIMENT_APPLIED, jchkSum.opt(QuestionConstants.CHECK_SENTIMENT));
		} catch (Exception e) {
		}
		newInst.setData(jchkSum.toString());
	}

	private void updateIfNeeded(Optional<Map<UUID, QuestionInstDTO>> prevCheckQMap, QuestionInstDTO pInst_) {
		prevCheckQMap.ifPresent(mapper -> {
			QuestionInstDTO prevQue = mapper.get(pInst_.getMasterUUID());
			if (prevQue != null && prevQue.getAnswer()!= null) {
				JSONObject object = new JSONObject(prevQue.getAnswer());
				JSONObject objectNew = new JSONObject();
				objectNew.put(QuestionConstants.ANSWER, object.optString(QuestionConstants.ANSWER, ""));
				objectNew.put(QuestionConstants.REMARK, object.optString(QuestionConstants.REMARK, ""));
				pInst_.setAnswer(objectNew.toString());
			}
		});
	}

	private SectionQuestionInstDTO associate(SectionInstDTO sectDTO, QuestionInstDTO qInst) {
		return this.checkInstDAO.associate(sectDTO, qInst);

	}

	private void associate(CheckListInstDTO inst, SectionInstDTO sectDTO) {
		this.checkInstDAO.associate(inst, sectDTO);

	}

	private QuestionInstDTO clone(CheckListInstCreateDTO chkCrtDTO, QuestionDTO question) {
		QuestionInstDTO inst = new QuestionInstDTO();

		inst.setUUID(UUID.randomUUID());

		inst.setMasterParentUUID(question.getParentUUId());
		inst.setMasterUUID(question.getUuid());

		inst.setCompany(chkCrtDTO.getCompany());
		inst.setVessel(chkCrtDTO.getVessel());

		inst.setSerNo(question.getSerNo());
		inst.setOrder(question.getOrder());
		inst.setQuestionText(question.getQuestionText());
		inst.setQuestionHelp(question.getQuestionHelp());

		inst.setType(question.getType());
		inst.setData(question.getData());

		inst.setPreProcessor(question.getPreProcessor());
		inst.setPostProcessor(question.getPostProcessor());

		inst.setAssocType(chkCrtDTO.getAssocType());
		switch (chkCrtDTO.getAssocType()) {
		case RANK:
			inst.setAssocTo(question.getResponsibility());
			break;
		case USER:
			inst.setAssocTo(chkCrtDTO.getAssocId());
		}
		inst.setFilter(question.getResponsibility());
		inst.setAuditTrail(Helper.getAuditTrail());
		return inst;
	}

	private SectionInstDTO clone(CheckListInstCreateDTO chkCrtDTO, SectionDTO section) {
		SectionInstDTO inst = new SectionInstDTO();
		inst.setUUID(UUID.randomUUID());
		inst.setMasterUUID(section.getUUID());
		inst.setSlNo(section.getSlNo());
		inst.setName(section.getName());
		inst.setCompany(chkCrtDTO.getCompany());
		inst.setVessel(chkCrtDTO.getVessel());
		inst.setAuditTrail(Helper.getAuditTrail());
		inst.setData(section.getData());
		inst = this.checkInstDAO.create(inst);

		return inst;
	}

	private CheckListInstDTO clone(CheckListInstCreateDTO chkCrtDTO, CheckListDTO checklistDTO,
			Optional<CheckListInstDTO> checklist) {

		final CheckListInstDTO inst = new CheckListInstDTO();

		ChkLstInstHelper.populate(checklistDTO, inst);
		inst.setName(chkCrtDTO.getChecklistName());
		inst.setStartDate(chkCrtDTO.getStartDate());
		inst.setEndDate(chkCrtDTO.getEndDate());
		inst.setState(State.NOTSTARTED);
		inst.setCompany(chkCrtDTO.getCompany());
		inst.setVessel(chkCrtDTO.getVessel());
		inst.setAssocType(chkCrtDTO.getAssocType());
		inst.setAssocTo(chkCrtDTO.getAssocId());
		inst.setCheckId(UUID.randomUUID());
		inst.setData(checklistDTO.getData());
		updateDockletNo(chkCrtDTO, inst);
		checklist.map(chk -> {
			updateChkData(inst, chk);
			return chk;
		});

		CheckListInstDTO retInst = checkInstDAO.create(inst);
		return retInst;
	}

	private void updateDockletNo(CheckListInstCreateDTO chkCrtDTO, CheckListInstDTO inst) {
		List<String> preProcessor = JSONUtils.getStrList(inst.getPreProcessor());
		logger.info("Not processing pre processors" + JSONUtils.getJSString(preProcessor));
		JSONObject data =  JSONUtils.getJSObject(inst.getData());
		String format = JSONUtils.getStringValue(data , QuestionConstants.CHECK_DOCKLET_FORMAT);
		if (format != null && !format.isEmpty()) {
			data.put(QuestionConstants.CHECK_DOCKLET_NO, chkCrtDTO.getDocumentNo());
			inst.setData(data.toString());
		}
	}

	private void validate(CheckListInstCreateDTO checklistDTO) {
		if (checklistDTO.getMasterChkUUID() == null) {
			throw new ServiceException("Master clone checklist is null");
		}
		if (checklistDTO.getVoyage() == null) {
			if (checklistDTO.getStartDate() == null || checklistDTO.getEndDate() == null) {
				throw new ServiceException("Start/End or Voyage date not specified");
			}
		}

		if (checklistDTO.getCompany() == null) {
			logger.info("Attaching the checklist to the logged in company");
			checklistDTO.setCompany(ApplicationContext.getContext().getCompany());
		}
		if (checklistDTO.getVessel() == null) {
			logger.info("Attaching the checklist to the logged in Ship");
			checklistDTO.setVessel(ApplicationContext.getContext().getShip());
		}
		if (!compService.checkAssociation(checklistDTO.getCompany(), checklistDTO.getVessel())) {
			logger.info(String.format("Falied Company-%s to Ship-%s association check in cloning checklist",
					checklistDTO.getCompany().getName(), checklistDTO.getVessel().getName()));
			throw new ServiceException(
					String.format("Falied Company-%s to Ship-%s association check in cloning checklist",
							checklistDTO.getCompany().getName(), checklistDTO.getVessel().getName()));
		}
		if (checklistDTO.getStartDate().after(checklistDTO.getEndDate())) {
			logger.info("Start date of checklist should be before enddate");
			throw new ServiceException("Start date of checklist should be before enddate");
		}
	}

	@Resource(name = "processor")
	Processor processot;
	boolean doSome = false;

	private boolean filterForLoggedInUser(CheckListInstDTO checklist) {
		JSONObject filterJson = new JSONObject(checklist.getData());
		UserDTO user = ApplicationContext.getContext().getUser();
		try {
			
			return filterJson.optJSONArray("ranks").toList().stream().filter(som -> som.toString().equalsIgnoreCase(user.getRank().getCode())).findAny().isPresent();
		} catch (Exception e) {
			
		}
		return filterJson.optString("ranks",user.getRank().getCode()).equalsIgnoreCase(user.getRank().getCode());
	}
	
	@Override
	public List<CheckListInstDTO> fetchAllChkIns() {
		List<CheckListInstDTO> filteredChecklist = this.checkInstDAO.loadAll().stream().
					filter(this::filterForLoggedInUser).collect(Collectors.toList());
		return CopyUtil.map(filteredChecklist , chklist -> {
			checklistValidator(chklist);
			if (doSome)
				processot.process(chklist.getCheckId());
			return chklist;
		});
	}

	@Override
	public SectionInstDTO save(SectionInstDTO sec) {
		return this.checkInstDAO.create(sec);
	}

	@Override
	public CheckListInstDTO save(CheckListInstDTO checkListInst) {
		return this.checkInstDAO.create(checkListInst);
	}

	private final static SimpleDateFormat format = new SimpleDateFormat("dd-M-yyyy hh:mm:ss");

	public JSONObject getSummaryData(UUID checkInstId, boolean includeAll) {
		JSONObject result = new JSONObject();

		Optional<CheckListInstDTO> optChk = this.loadCheckListInst(checkInstId);
		optChk.ifPresent(chkInst -> {

			JSONObject jchkSum = new JSONObject();
			jchkSum.put("id", chkInst.getCheckId());
			jchkSum.put("name", chkInst.getName());
			jchkSum.put("time", format.format(chkInst.getStartDate()));
			JSONObject data = new JSONObject(chkInst.getData());
			Float weightageFlt = data.optFloat(QuestionConstants.WEIGHTAGE, -1);
			String weightage = data.optString(QuestionConstants.WEIGHTAGE);
			if (!StringUtils.getBoolVal(weightage, false) && weightageFlt < 0) {
				throw new WeightageNotSupportedException("Checklist dosent support weightage");
			}

			try {
				jchkSum.put(QuestionConstants.WEIGHTAGE, data.optFloat(QuestionConstants.WEIGHTAGE));
				jchkSum.put(QuestionConstants.WEIGHTAGE_CALC, data.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0));
				jchkSum.put(QuestionConstants.WEIGHTAGE_APPLIED, data.optFloat(QuestionConstants.WEIGHTAGE_APPLIED,
						data.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0)));
				jchkSum.put(QuestionConstants.CHECK_SENTIMENT, data.optFloat(QuestionConstants.CHECK_SENTIMENT, 0));
				jchkSum.put(QuestionConstants.CHECK_SENTIMENT_APPLIED,
						data.optFloat(QuestionConstants.CHECK_SENTIMENT_APPLIED, 0));
			} catch (Exception e) {
				// Ignore error as te checkllist might not have weightage
			}
			result.put("checklist", jchkSum);
			JSONArray secData = new JSONArray();
			result.put("sections", secData);
			loadSections(chkInst).stream().forEach(sect -> {
				JSONObject secdataJs = new JSONObject(sect.getData());
				if (secdataJs.optDouble(QuestionConstants.WEIGHTAGE, 0) > 0 || includeAll) {
					JSONObject jsecSum = new JSONObject();
					jsecSum.put("id", sect.getUUID());
					jsecSum.put("name", sect.getName());
					try {
						jsecSum.put(QuestionConstants.WEIGHTAGE_CALC,
								secdataJs.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0));
						jsecSum.put(QuestionConstants.WEIGHTAGE_APPLIED,
								secdataJs.optFloat(QuestionConstants.WEIGHTAGE_APPLIED,
										secdataJs.optFloat(QuestionConstants.WEIGHTAGE_CALC, 0)));
						jsecSum.put(QuestionConstants.WEIGHTAGE, secdataJs.optFloat(QuestionConstants.WEIGHTAGE));
					} catch (Exception e) {
						// Ignore error as te checkllist might not have weightage
					}
					secData.put(jsecSum);
				}
			});
		});
		return result;
	}

	public JSONObject saveSummaryData(UUID checkInstId, JSONObject summaryData) {

		// JSONObject jChk = summaryData.getJSONObject("checklist");
		// .getString(QuestionConstants.WEIGHTAGE_APPLIED);
		JSONObject result = new JSONObject();

		this.loadCheckListInst(checkInstId).ifPresent(chkInst -> {
			JSONObject jChk = summaryData.getJSONObject("checklist");
			Float sentiVal = jChk.getFloat(QuestionConstants.CHECK_SENTIMENT_APPLIED);
			Float jsonVal = jChk.getFloat(QuestionConstants.WEIGHTAGE_APPLIED);
			JSONObject data = new JSONObject(chkInst.getData());
			Float dbVal = data.optFloat(QuestionConstants.WEIGHTAGE_APPLIED);
			Float dbSentiVal = data.optFloat(QuestionConstants.CHECK_SENTIMENT);

			if (jsonVal != dbVal || sentiVal != dbSentiVal) {
				data.put(QuestionConstants.WEIGHTAGE_APPLIED, jsonVal);
				data.put(QuestionConstants.CHECK_SENTIMENT_APPLIED, sentiVal);
				chkInst.setData(data.toString());
				this.save(chkInst);
			}
			JSONArray secData = summaryData.getJSONArray("sections");
			loadSections(chkInst).stream().forEach(sect -> {
				JSONObject selSec = null;
				for (int i = secData.length() - 1; i >= 0; i--) {
					JSONObject tmp = secData.getJSONObject(i);
					if (tmp.getString("id").equalsIgnoreCase(sect.getUUID().toString())) {
						selSec = tmp;
					}
				}
				if (selSec != null) {
					JSONObject secdataJs = new JSONObject(sect.getData());
					Float dbSecVal = secdataJs.optFloat(QuestionConstants.WEIGHTAGE_APPLIED, 0);
					Float jSecVAl = selSec.optFloat(QuestionConstants.WEIGHTAGE_APPLIED, 0);
					if (dbSecVal != jSecVAl) {
						secdataJs.put(QuestionConstants.WEIGHTAGE_APPLIED, jSecVAl);
						sect.setData(secdataJs.toString());
						this.save(sect);
					}
				}
			});
		});
		return summaryData;
	}

	public List<CheckListInstDTO> getPreviousChecklist(CheckListInstDTO checkInst, int number) {
		List<CheckListInstDTO> result = Collections.emptyList();
		try {
			result = checkInstDAO.getPreviousChecklist(checkInst, number);
			result.sort((tg, th) -> tg.getCompletedDate().compareTo(th.getCompletedDate()));
		} catch (DataException e) {
			String info = String.format("Error loading Previous checklist for checklist %s[UUID-%s]",
					checkInst.getCheckId());
			logger.info(info);
			throw new ServiceException(info, e);
		}
		return result;
	}

	@Override
	public void process(UUID uuid, String processor) {
		this.processot.process(uuid, processor);
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void SYSOUT() {
		try {
			Date date = new Date();
			checkInstDAO.updateCheckListStatusAsOf(date,State.CLOSED);
		} catch (DataException e) {
			logger.error("Error colsing checklist ",e);
			throw new ServiceException("Error colsing checklist ", e);
		}

	}
}
