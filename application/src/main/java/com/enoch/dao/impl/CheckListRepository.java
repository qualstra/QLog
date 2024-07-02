package com.enoch.dao.impl;

import static com.enoch.utils.CopyUtil.map;
import static com.enoch.utils.CopyUtil.transform;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.web.server.ResponseStatusException;

import com.enoch.ApplicationContext;
import com.enoch.config.ApplicationProperties;
import com.enoch.dao.CheckListDao;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.CheckListSearchDTO;
import com.enoch.dto.checklist.ChecklistSectionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.exception.DataException;
import com.enoch.exception.EntityException;
import com.enoch.model.checklist.CheckList;
import com.enoch.model.checklist.CheckListSection;
import com.enoch.model.checklist.QCheckList;
import com.enoch.model.checklist.Section;
import com.enoch.model.checklist.State;
import com.enoch.service.EntitlementService;
import com.enoch.utils.CollectionUtil;
import com.enoch.utils.CopyUtil;
import com.enoch.utils.StringUtils;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.Ops;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;

@Repository("ChecklistDao")
public class CheckListRepository implements CheckListDao {

	@Autowired
	ChecklistRepo chkRep;
	@Autowired
	SectionRepo secRep;
	@Autowired
	ChecklistSectionRepo chkSecRep;
	@Autowired
	EntitlementService secRepo;

	@Override
	public Optional<CheckListDTO> loadCheckList(Long id) {
		return chkRep.findById(id).map(a -> a.transform());
	}

	@Override
	public CheckListDTO createCheckList(CheckListDTO dto) throws DataException {
		try {
			dto.setCheckId(UUID.randomUUID());
			dto.setVersion(1);
			return chkRep.saveAndFlush(dto.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error Saving checklist", e);
		}
	}

	@Override
	public List<SectionDTO> loadAllSections(CheckListDTO dto) throws DataException {
		try {
			return map(chkRep.loadAllSections(dto.transform()), a -> a.getSection().transform());
		} catch (Exception e) {
			throw new DataException("Error Saving checklist", e);
		}
	}

	@Override
	public SectionDTO createSection(SectionDTO secDTO) throws DataException {
		try {

			return secRep.saveAndFlush(secDTO.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error Saving checklist", e);
		}
	}

	private Section load(SectionDTO dtoTmp) {
		if (dtoTmp.getId() == null) {
			throw new EntityException(Section.class, "Cannot load null Id");
		}
		Optional<Section> chk = secRep.findById(dtoTmp.getId());
		if (!chk.isPresent())
			throw new EntityException(CheckList.class, String.format("Cannot load %d", dtoTmp.getId()));
		return chk.get();
	}

	private CheckList load(CheckListDTO dtoTmp) {
		if (dtoTmp.getId() == null) {
			throw new EntityException(CheckList.class, "Cannot load null Id");
		}
		Optional<CheckList> chk = chkRep.findById(dtoTmp.getId());
		if (!chk.isPresent())
			throw new EntityException(CheckList.class, String.format("Cannot load %d", dtoTmp.getId()));
		return chk.get();
	}

	@Override
	public CheckListDTO saveCheckList(CheckListDTO dtoTmp) throws DataException {

		CheckList dbChk = load(dtoTmp);

		if (dbChk.getState() != State.DRAFT) {
			throw new EntityException(CheckList.class, "Cannot save published Checklist");
		}
		try {
			return chkRep.saveAndFlush(dtoTmp.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error Saving checklist", e);
		}
	}

	@Override
	public CheckListDTO makeCheckListEditable(CheckListDTO dto) throws DataException {
		CheckListDTO dbChk = load(dto).transform();
		switch (dbChk.getState()) {
		case DRAFT:
			break;
		case PUBLISHED:
			dbChk.setId(null);
			dbChk.setState(State.DRAFT);
			dbChk.setVersion(dbChk.getVersion() + 1);
			dbChk = chkRep.saveAndFlush(dbChk.transform()).transform();
		}
		return dbChk;
	}

	@Override
	public List<CheckListDTO> loadByCheckListId(UUID checkId) {
		return transform(chkRep.loadByCheckListId(checkId));

	}

	@Override
	public SectionDTO saveSection(SectionDTO dto) throws DataException {
		Section dbChk = load(dto);
		if (dbChk.getState() != State.DRAFT) {
			throw new EntityException(CheckList.class, "Cannot save published Checklist");
		}
		try {
			return secRep.saveAndFlush(dto.transform()).transform();
		} catch (Exception e) {
			throw new DataException("Error Saving section", e);
		}
	}

	@Override
	public List<ChecklistSectionDTO> addSecToCL(CheckListDTO chkDTO, SectionDTO... secDTO) throws DataException {
		List<CheckListSection> sections = chkRep.loadAllSections(chkDTO.transform());
		List<CheckListSection> res = new ArrayList<CheckListSection>();
		for (SectionDTO dto : secDTO) {
			Optional<CheckListSection> some = CollectionUtil.find(sections, dto, (ths, tht) -> {
				return StringUtils.eqI(ths.getSection().getName(), tht.getName());
			});
			if (!some.isPresent()) {
				res.add(chkSecRep.saveAndFlush(new CheckListSection(null, chkDTO.transform(), dto.transform(), true)));
			}else {
				throw new DataException("Sectio with the given name already exists for the Checklist.");
			}
		}
		return transform(res);
	}

	@Override
	public void remSecToCL(CheckListDTO chkDTO, SectionDTO... secDTO) throws DataException {
		List<CheckListSection> sections = chkRep.loadAllSections(chkDTO.transform());
		for (SectionDTO dto : secDTO) {
			CollectionUtil.find(sections, dto, (ths, tht) -> {
				return StringUtils.eqI(ths.getSection().getName(), tht.getName());
			}).ifPresent(chkSec -> {
				chkSecRep.delete(chkSec);
			});
		}
	}
	
	@Autowired
	ApplicationProperties props;

	@Override
	public List<CheckListDTO> getAllChecklist() {
		Boolean canViewAll  = secRepo.hasPermission(ApplicationContext.getContext().getLoggedInUser(), "comp.VIEW_ALL");
		List<CheckListDTO> result = Collections.emptyList();
		if(canViewAll || ApplicationContext.getContext().getLoggedInUser().equals("admin@enochshipping.com")) {
			result = CopyUtil.transform(chkRep.findAll());
		} else {
			QCheckList checkList = QCheckList.checkList;
			BooleanExpression e = checkList.name.in(props.getDefaultChecklists()).or(checkList.company.id.eq(ApplicationContext.getContext().getCompany().getId()));
			 result = CopyUtil.transform(chkRep.findAll(e));
		}
		return result;
	}

	@Override
	public SectionDTO loadSection(Long parseLong) {
		Optional<Section> optSection = secRep.findById(parseLong);
		if(!optSection.isPresent()) {
			throw new DataException("Error loading Section with ID "+parseLong);
		}
		return optSection.get().transform();
	}

	@Override
	public List<CheckListDTO> search(CheckListSearchDTO chkVo) {
		try {
			QCheckList checkList = QCheckList.checkList;
			BooleanExpression builder = checkList.company.UUID.isNotNull();

			if (StringUtils.isNotBlank(chkVo.getCheckListCode()))
				builder = builder.and(checkList.name.equalsIgnoreCase(chkVo.getCheckListCode()));
			if (chkVo.getCompany()!= null)
				builder = builder.and(checkList.company.UUID.eq(chkVo.getCompany().getUUID()));
			return CopyUtil.transform(chkRep.findAll(builder));
		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "error while search voyage", e);
		}
	}

}
