package com.enoch.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.ApplicationContext;
import com.enoch.builder.QuestionHelper;
import com.enoch.common.exception.ServiceException;
import com.enoch.dao.CheckListDao;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;
import com.enoch.exception.ValidationException;
import com.enoch.misc.Tuple;
import com.enoch.model.Helper;
import com.enoch.model.QVoyage;
import com.enoch.model.checklist.QCheckList;
import com.enoch.model.checklist.State;
import com.enoch.service.CheckListService;
import com.enoch.service.QuestionService;
import com.enoch.utils.DateUtils;
import com.enoch.utils.JSONUtils;
import com.enoch.utils.StringUtils;
import com.enoch.validator.QuestionValidatonHelper;
import com.enoch.validator.ValidatorFactory;
import com.enoch.xl.Parser;
import com.querydsl.core.types.dsl.BooleanExpression;

@Service
public class CheckListServiceImpl implements CheckListService {

	@Autowired
	CheckListDao dao;
	@Autowired
	QuestionService qService;

	@Autowired
	ValidatorFactory factory;
	@Autowired
	QuestionValidatonHelper qValidatorHelper;

	@Override
	public List<CheckListDTO> getAllCheckLists() {
		return dao.getAllChecklist();
	}

	@Override
	public CheckListDTO create(CheckListDTO checklistDTO) {
		if(checklistDTO.getCompany() == null)
			checklistDTO.setCompany(ApplicationContext.getContext().getCompany());
		checklistDTO.setAuditTrail(ApplicationContext.getContext().getAuditTrail());
		return dao.createCheckList(checklistDTO);
	}

	@Override
	public List<CheckListDTO> search(CheckListSearchDTO chkVo) {
		return dao.search(chkVo);
	}

	@Override
	public List<CheckListDTO> findCheckListByUID(UUID checkId) {
		try {
			return dao.loadByCheckListId(checkId);
		} catch (DataException e) {
			throw new ServiceException("Error finding checklist - " + checkId, e);
		}

	}

	// To Create a Section
	@Override
	public ChecklistSectionDTO create(SectionDTO section, CheckListDTO checklist) {
		try {
			section.setAuditTrail(Helper.getAuditTrail());
			if(section.getUUID() == null) section.setUUID(UUID.randomUUID());
			if (section.getCompany() == null) {
				// No company selected so go with logged in company
				section.setCompany(ApplicationContext.getContext().getCompany());
			}
			SectionDTO sectionSaved = dao.createSection(section);
			List<ChecklistSectionDTO> res = dao.addSecToCL(checklist, sectionSaved);
			if (res == null || res.size() != 1) {
				throw new ServiceException("Error Adding Section ");
			}
			return res.get(0);

		} catch (ServiceException e) {
			throw e;
		} catch (DataException e) {
			throw new ServiceException("Error Creating Section for Checklist", e);
		}
	}

	@Override
	public List<SectionDTO> loadAllSections(CheckListDTO dto) throws ServiceException {
		try {
			return dao.loadAllSections(dto);
		} catch (DataException e) {
			throw new ServiceException("Error loading Sections for Checklist " + dto.getId(), e);
		}

	}

	@Override
	public SectionDTO loadSection(Long parseLong) {
		try {
			return dao.loadSection(parseLong);
		} catch (DataException e) {
			throw new ServiceException("Error loading Sections " + parseLong, e);
		}
	}

	private List<JSONObject> getDataFromFile(File file) {
		Map<Integer, List<String>> data = Parser.getData(file);
		List<String> header = data.remove(new Integer(0));
		// header.forEach(a -> System.out.println(a));
		int headerCount = header.size();
		final List<JSONObject> jsObjects = new ArrayList<>();
		data.forEach((Integer rowIndex, List<String> values) -> {
			JSONObject jsonObject = new JSONObject();
			for (int ind = 0; ind < headerCount; ind++) {
				Object valu = values.get(ind);
				try {
					valu = new JSONObject(values.get(ind));
				} catch (Exception e) {
					// e.printStackTrace();
				}
				jsonObject.put(header.get(ind), valu);
			}
			jsObjects.add(jsonObject);
		});
		return jsObjects;
	}

	private Map<CheckListDTO, Map<SectionDTO, List<QuestionDTO>>> buildChecklistFromJson(
			CompanyDTO comp, final List<JSONObject> jsObjects) {
		final List<String> exceptionsStr = new ArrayList<String>(); 
		final Map<CheckListDTO, Map<SectionDTO, List<QuestionDTO>>> result = new HashMap();
		final Tuple<String,Integer> secCounterMap = new Tuple<String, Integer>("", 0);
		jsObjects.stream().forEach(jsObject -> {
			String secName = jsObject.getString(QuestionHelper.HEADER_SECTION_NAME);
			if(!secCounterMap.getValue1().equalsIgnoreCase(secName)) {
				secCounterMap.setValue1(secName);
				secCounterMap.setValue2(secCounterMap.getValue2()+1);
			} 
			jsObject.put(QuestionHelper.HEADER_SECTION_SNO,secCounterMap.getValue2());
		});
		jsObjects.stream().collect(Collectors.groupingBy(a -> a.get(QuestionHelper.HEADER_CHK_NAME)))
				.forEach((chk, secs) -> {
					final CheckListDTO checklistDTO_tmp = new CheckListDTO();
					checklistDTO_tmp.setName(chk.toString());
					checklistDTO_tmp.setRemarks(chk.toString());
					checklistDTO_tmp.setData(secs.get(0).get(QuestionHelper.HEADER_CHK_OPT).toString());
					
					checklistDTO_tmp.setShortDesc(Helper.getSafeKey(checklistDTO_tmp.getData(),"description"));
					checklistDTO_tmp.setLongDesc(Helper.getSafeKey(checklistDTO_tmp.getData(),"longDescription"));
					
					checklistDTO_tmp.setActive(true);
					checklistDTO_tmp.setState(State.DRAFT);
					checklistDTO_tmp.setCompany(comp);
					result.put(checklistDTO_tmp, new HashMap<SectionDTO, List<QuestionDTO>>());
					
					secs.stream().collect(Collectors.groupingBy(a -> a.get(QuestionHelper.HEADER_SECTION_NAME)))
							.forEach((sec, ques) -> {
								Set<String> postProcessor = new HashSet<String>();
								Set<String> preProcessor = new HashSet<String>();
								SectionDTO section = new SectionDTO();
								section.setActive(true);
								section.setData(ques.get(0).get(QuestionHelper.HEADER_SECTION_OPT).toString());
								section.setState(State.DRAFT);
								section.setName(sec.toString());
								section.setSlNo(ques.get(0).getInt(QuestionHelper.HEADER_SECTION_SNO));
								section.setLongDescription(sec.toString());
								section.setShortDesc(sec.toString());
								section.setCompany(comp);
								// ChecklistSectionDTO chkSecDto = create(section, checklistDTO);
								List<QuestionDTO> questions = new ArrayList<>();
								ques.forEach(action -> {
									try{
										QuestionDTO question = QuestionHelper.buildQuestion(action);
										question.setCompany(comp);
										questions.add(question);
										postProcessor.addAll(JSONUtils.getStrList(question.getPostProcessor()));
										preProcessor.addAll(JSONUtils.getStrList(question.getPreProcessor()));
									}catch(ServiceException e) {
										exceptionsStr.add(e.getMessage());
									}
								});
								section.setPreProcessor(JSONUtils.getJSString(preProcessor));
								section.setPostProcessor(JSONUtils.getJSString(postProcessor));
								result.get(checklistDTO_tmp).put(section, questions);

							});
					Set<String> postProcessor = new HashSet<String>();
					Set<String> preProcessor = new HashSet<String>();
					// Default report
					postProcessor.add("REP");
					postProcessor.add("NCR");
					result.get(checklistDTO_tmp).keySet().forEach(section -> {
						postProcessor.addAll(JSONUtils.getStrList(section.getPostProcessor()));
						preProcessor.addAll(JSONUtils.getStrList(section.getPreProcessor()));
					});
					checklistDTO_tmp.setPreProcessor(JSONUtils.getJSString(preProcessor));
					checklistDTO_tmp.setPostProcessor(JSONUtils.getJSString(postProcessor));
				});
		if(!exceptionsStr.isEmpty()) throw new ValidationException("Validation : Structural Error", exceptionsStr);
		return result;
	}

	@Override
	public List<CheckListDTO> buildChecklist(CompanyDTO comp, File file, boolean validateOnly) {
		final List<JSONObject> jsObjects = getDataFromFile(file);
		final List<CheckListDTO> result = new ArrayList<CheckListDTO>();
		List<String> validationerrors = new ArrayList<String>();
		Map<CheckListDTO, Map<SectionDTO, List<QuestionDTO>>> mapCHk = buildChecklistFromJson(comp,jsObjects);
		
		validationerrors.addAll(validate(mapCHk));
		if (!validationerrors.isEmpty()) {
			throw new ValidationException("Validation Errors", validationerrors);
		}
		if (validateOnly) {
			return new ArrayList<CheckListDTO>(mapCHk.keySet());
		}
		mapCHk.entrySet().forEach(entry -> {
			CheckListDTO checklistDTO = create(entry.getKey());
			Map<SectionDTO, List<QuestionDTO>> secMap = entry.getValue();
			secMap.entrySet().forEach(secQueMap -> {
				ChecklistSectionDTO chkSecDto = create(secQueMap.getKey(), checklistDTO);
				List<QuestionDTO> questions = secQueMap.getValue();
				for (int i = 0; i < questions.size(); i++) {
					final QuestionDTO queTmp = questions.get(i);
					if (queTmp.getParentSerNo() != null && queTmp.getParentSerNo() > 0) {
						questions.stream().filter(
								q -> q.getSerNo().intValue() == Integer.valueOf(queTmp.getParentSerNo().toString()))
								.findFirst().ifPresent(found -> queTmp.setParentUUId(found.getUuid()));
					}
					QuestionDTO dto = qService.save(questions.get(i), chkSecDto.getSection());
					questions.set(i, dto);
				}
			});
		});
		return result;
	}

	private List<String> validate(Map<CheckListDTO, Map<SectionDTO, List<QuestionDTO>>> mapCHk) {

		List<String> errorList = new ArrayList<String>();
 
		mapCHk.entrySet().forEach(entry -> {
			CheckListDTO checklistDTO = entry.getKey();
			Map<SectionDTO, List<QuestionDTO>> secMap = entry.getValue();
			secMap.entrySet().forEach(secQueMap -> {
				ChecklistSectionDTO chkSecDto = new ChecklistSectionDTO();
				chkSecDto.setChecklist(checklistDTO);
				chkSecDto.setSection(secQueMap.getKey());
				List<QuestionDTO> questions = secQueMap.getValue();
				for (int i = 0; i < questions.size(); i++) {
					try {
						qValidatorHelper.validateQuestion(questions.get(i));
					} catch (ServiceException e) {
						errorList.add(String.format("checklist[%s] section[%s] Question[%s] : %s ",
								checklistDTO.getName(), chkSecDto.getSection().getName().toString(),
								questions.get(i).getSerNo(), e.getMessage()));
					}
				}
				try {
					factory.getCheckListQueValidator(checklistDTO, chkSecDto.getSection()).validate(questions);
				} catch (ServiceException e) {
					errorList.add(String.format("checklist[%s] section[%s] : %s  ", checklistDTO.getName(),
							chkSecDto.getSection().getName().toString(), e.getMessage()));
				}
			});
			try {
				factory.getCheckListSecValidator(checklistDTO).validate(new ArrayList<>(secMap.keySet()));
			} catch (ServiceException e) {
				errorList.add(String.format("checklist[%s] : %s  ", checklistDTO.getName(), e.getMessage()));
			}
		});

		return errorList;
	}

}
