package com.enoch.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.enoch.ApplicationContext;
import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.dao.UserDAO;
import com.enoch.dto.CompanyDTO;
import com.enoch.dto.CompanySearchDTO;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.ContextDTO;
import com.enoch.dto.MouDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.ShipSearchDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.UserSearchDTO;
import com.enoch.dto.VoyageDTO;
import com.enoch.dto.VoyageSearchDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.SectionDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.dto.checklist.inst.QuestionInstDTO;
import com.enoch.dto.checklist.inst.SectionInstDTO;
import com.enoch.dto.fleet.FleetDTO;
import com.enoch.dto.fleet.FleetShipDTO;
import com.enoch.dto.nc.NCDTO;
import com.enoch.master.dto.CountryDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Port;
import com.enoch.model.checklist.State;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.EntitlementService;
import com.enoch.service.ShipService;
import com.enoch.utils.CopyUtil;
import com.enoch.vo.AttachmentVO;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.CheckListSectionVO;
import com.enoch.vo.CheckListVO;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.MasterChecklistItemVO;
import com.enoch.vo.MouVO;
import com.enoch.vo.NCVO;
import com.enoch.vo.PortVO;
import com.enoch.vo.QuestionInstVO;
import com.enoch.vo.SectionInstVO;
import com.enoch.vo.ShipVO;
import com.enoch.vo.UserVO;
import com.enoch.vo.VoyageSearchVO;
import com.enoch.vo.VoyageVO;
import com.enoch.vo.entitlement.RankVO;
import com.enoch.vo.entitlement.RoleVO;
import com.enoch.vo.fleet.FleetShipVO;
import com.enoch.vo.fleet.FleetVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Helper {
	public static MouDTO toDTO(MouVO vo) {
//		MouDTO dto = new MouDTO(null, vo.getCode(), vo.getDesc(), vo.getCicid(), vo.getFromDate(), vo.getToDate(),
//				com.enoch.model.Helper.getAuditTrail());
		MouDTO dto = new MouDTO();
		dto.setId(vo.getId());
		dto.setCicid(vo.getCicid());
		dto.setFromDate(vo.getToDate());
		dto.setToDate(vo.getToDate());
		dto.setDesc(vo.getDesc());
		dto.setCode(vo.getCode());
		return dto;
	}

	public static CheckListVO toVO(CheckListDTO dto) {
		CheckListVO checkListVO = new CheckListVO();

		BeanUtils.copyProperties(dto, checkListVO);
		return checkListVO;
	}

//	public static CompanyVO toVO(CompanyDTO company) {
//		CompanyVO companyVO = new CompanyVO();
//		BeanUtils.copyProperties(company, companyVO);
//		return companyVO;
//		}

	public static CheckListSectionVO toVO(SectionDTO chkSecVo) {
		CheckListSectionVO section = new CheckListSectionVO();
		section.setId(chkSecVo.getId());
		section.setSectionName(chkSecVo.getName());
		section.setDesc(chkSecVo.getShortDesc());
		section.setLongDescription(chkSecVo.getLongDescription());
		section.setPreProcessor(chkSecVo.getPreProcessor());
		section.setPostProcessor(chkSecVo.getPostProcessor());
		section.setSlno(chkSecVo.getSlNo());
		section.setActive(true);
		section.setState(State.DRAFT);
		return section;
	}

	public static SectionDTO toDTO(CheckListSectionVO chkSecVo) {
		SectionDTO section = new SectionDTO();
		section.setId(chkSecVo.getId());
		section.setCompany(ApplicationContext.getContext().getCompany());
		section.setName(chkSecVo.getSectionName());
		section.setShortDesc(chkSecVo.getDesc());
		section.setLongDescription(chkSecVo.getLongDescription());
		section.setPreProcessor(chkSecVo.getPreProcessor());
		section.setPostProcessor(chkSecVo.getPostProcessor());
		section.setSlNo(chkSecVo.getSlno());
		section.setActive(chkSecVo.getActive());
		section.setState(chkSecVo.getState());
		return section;
	}

	public static PortDTO toDTO(PortVO portVO) {
		PortDTO portDTO = new PortDTO();
		BeanUtils.copyProperties(portVO, portDTO);
		CountryDTO country = new CountryDTO();
		country.setCode(portVO.getCountry());
		portDTO.setCountry(country);
		return portDTO;
	}

	public static PortVO toVO(PortDTO portDto) {
		PortVO portVO = new PortVO();
		BeanUtils.copyProperties(portDto, portVO);
		portVO.setCountry(portDto.getCountry().getCode());
		return portVO;
	}

	public static UserVO toVO(UserDTO dto) {
		UserVO vo = new UserVO();
		vo.setId(dto.getId());
		vo.setUUID(dto.getUUID());
		vo.setUserName(dto.getUserName());
		vo.setFirstName(dto.getFirstName());
		vo.setLastName(dto.getLastName());
		vo.setPassword(dto.getPassword());
		vo.setRank(dto.getRank().getCode());
		vo.setCdc(dto.getCdc());
		vo.setPassport(dto.getPassport());
		vo.setDob(dto.getDob());

		vo.setVesselId(0L);
		vo.setVesselName("HELPER");
		vo.setIsActive(dto.getIsActive());

		return vo;
	}

	public static UserDTO toDTO(UserVO userVO) {
		UserDTO dto = new UserDTO();
		dto.setId(userVO.getId());
		dto.setUUID(userVO.getUUID());

		dto.setUserName(userVO.getUserName());
		dto.setFirstName(userVO.getFirstName());
		dto.setLastName(userVO.getLastName());
		dto.setPassword(userVO.getPassword());
		dto.setCdc(userVO.getCdc());
		dto.setPassport(userVO.getPassport());
		dto.setDob(userVO.getDob());
		RankDTO rank = new RankDTO();
		rank.setName(userVO.getRank());
		rank.setCode(userVO.getRank());
		dto.setRank(rank);
		dto.setId(userVO.getId());
		CompanyDTO compDTO = new CompanyDTO();
		compDTO.setId(userVO.getCompanyId());
		compDTO.setName(userVO.getShipcompany());
		dto.setIsActive(userVO.getIsActive());
		return dto;
	}
	

	public static UserDTO toUpdateDTO(UserVO userVO) {
		UserDTO dto = new UserDTO();
		dto.setId(userVO.getId());
		dto.setUUID(userVO.getUUID());

		dto.setUserName(userVO.getUserName());
		dto.setFirstName(userVO.getFirstName());
		dto.setLastName(userVO.getLastName());
		dto.setCdc(userVO.getCdc());
		dto.setPassport(userVO.getPassport());
		dto.setDob(userVO.getDob());
		RankDTO rank = new RankDTO();
		rank.setName(userVO.getRank());
		rank.setCode(userVO.getRank());
		dto.setRank(rank);
		dto.setId(userVO.getId());
		CompanyDTO compDTO = new CompanyDTO();
		compDTO.setId(userVO.getCompanyId());
		compDTO.setName(userVO.getShipcompany());
		dto.setIsActive(userVO.getIsActive());
		return dto;
	}

	public static UserSearchDTO toSearchDTO(UserVO userVo) {
		UserSearchDTO searchDTO = new UserSearchDTO();
		searchDTO.setId(userVo.getId());
		// searchDTO.setCompany(company);;
		searchDTO.setUserName(userVo.getUserName());
		searchDTO.setFirstName(userVo.getFirstName());
		searchDTO.setLastName(userVo.getLastName());
		searchDTO.setRank(userVo.getRank());
		searchDTO.setPassport(userVo.getPassport());
		return searchDTO;
	}

	public static MasterChecklistItemVO toVO(QuestionDTO que) {
		try {
			MasterChecklistItemVO vo = new MasterChecklistItemVO();
			vo.setId(que.getUuid());
			vo.setResponsibility(que.getResponsibility());
			vo.setQuestionDesc(que.getQuestionText());
			vo.setType(que.getType());
			vo.setParentId(que.getParentUUId());
			vo.setSlno(que.getSerNo());
			vo.setData(Helper.getAsMap(que.getData()));
			que.getChildQuestions().forEach(queDTO -> vo.getCheckListItemChildVOs().add(Helper.toVO(queDTO)));
			return vo;
		} catch (Exception e) {
			throw new ServiceException("Error Converting Question");
		}
	}

	public static ShipDTO toDTO(ShipVO vo) {
		ShipDTO ship = new ShipDTO();
		ship.setId(vo.getId());
		ship.setUUID(vo.getUUID());
		ship.setName(vo.getName());
		ship.setImo(vo.getImo());
		ship.setVesselType(vo.getVesselType());
		ship.setCalss(vo.getCalss());
		ship.setFlag(vo.getFlag());
		ship.setGrt(vo.getGrt());
		ship.setCallSign(vo.getCallSign());
		ship.setCompany(ApplicationContext.getContext().getCompany());
		ship.setStatus(vo.getStatus());
		ship.setEmail(vo.getEmail());
		ship.setActivesince(vo.getActivesince());
		return ship;
	}

	public static ShipVO toVO(ShipDTO dto) {
		ShipVO vo = new ShipVO();
		vo.setId(dto.getId());
		vo.setName(dto.getName());
		vo.setUUID(dto.getUUID());
		vo.setImo(dto.getImo());
		vo.setVesselType(dto.getVesselType());
		vo.setCalss(dto.getCalss());
		vo.setFlag(dto.getFlag());
		vo.setGrt(dto.getGrt());
		vo.setCallSign(dto.getCallSign());
		vo.setCompany(toVO(dto.getCompany()));
		vo.setStatus(dto.getStatus());
		vo.setEmail(dto.getEmail());
		vo.setActivesince(dto.getActivesince());
		return vo;
	}

	public static ShipSearchDTO toSearchDTO(ShipVO vo) {
		ShipSearchDTO ship = new ShipSearchDTO();
		ship.setId(vo.getId());
		ship.setName(vo.getName());
		ship.setImo(vo.getImo());
		ship.setVesselType(vo.getVesselType());
		ship.setCalss(vo.getCalss());
		ship.setFlag(vo.getFlag());
		ship.setGrt(vo.getGrt());
		ship.setCallSign(vo.getCallSign());
		ship.setCompany(ApplicationContext.getContext().getCompany());
		ship.setStatus(vo.getStatus());
		ship.setEmail(vo.getEmail());
		ship.setActivesince(vo.getActivesince());
		return ship;
	}

	public static VoyageDTO toDTO(VoyageVO vo) {
		VoyageDTO voyage = new VoyageDTO();
		voyage.setId(vo.getId());
		// voyage.setShip(ApplicationContext.getContext().getShip());
		voyage.setCompany(ApplicationContext.getContext().getCompany());
		voyage.setVoyageNo(vo.getVoyageNo());

		Port oPort = new Port();
		oPort.setCode(vo.getOrgin());
		voyage.setOrgin(oPort);

		Port dPort = new Port();
		dPort.setCode(vo.getDestination());
		voyage.setDestination(dPort);

		// voyage.setEtd(vo.getEtd());
		voyage.setAtd(vo.getAtd());
		voyage.setEta(vo.getEta());
		voyage.setChecklist_etc(vo.getChecklist_etc());

		voyage.setShip(new ShipDTO(vo.getVesseilId()));
		voyage.setStatus(vo.getStatus());
		voyage.setCompany(ApplicationContext.getContext().getCompany());

		return voyage;
	}

	public static VoyageVO toVO(VoyageDTO dto) {
		VoyageVO vo = new VoyageVO();
		vo.setId(dto.getId());
		vo.setMasterVesselName(dto.getShip().getName());
		vo.setVoyageNo(dto.getVoyageNo());
		vo.setOrgin(dto.getOrgin().getCode());
		vo.setOrginDesc(dto.getOrgin().getDescription());
		vo.setDestination(dto.getDestination().getCode());
		vo.setDestDesc(dto.getDestination().getDescription());
		// vo.setEtd(dto.getEtd());
		vo.setAtd(dto.getAtd());
		vo.setEta(dto.getEta());
		vo.setChecklist_etc(dto.getChecklist_etc());
		vo.setStatus(vo.getStatus());
		vo.setCompany(toVO(dto.getCompany()));
		vo.setVesseilId(dto.getShip().getId());
		vo.setImo(dto.getShip().getImo());
		return vo;
	}

	public static VoyageSearchDTO toSearchDTO(VoyageSearchVO vo) {
		VoyageSearchDTO search = new VoyageSearchDTO();
		if (vo.getVesseilId() != null && vo.getVesseilId() > 0) {
			ShipDTO shipDTO = new ShipDTO();
			shipDTO.setId(vo.getVesseilId());
			search.setShip(shipDTO);
		}

		search.setVoyageNo(vo.getVoyageNo());

		if (vo.getOrgin() != null) {
			search.setOrgin(vo.getOrgin());
		}

		if (vo.getDestination() != null) {
			search.setDestination(vo.getDestination());
		}
//		if(vo.getOrgin() != null)
//			search.setOrgin(new PortDTO(vo.getOrgin()));
//		if(vo.getDestination() != null)
//			search.setDestination(new PortDTO(vo.getDestination()));

		search.setStart(vo.getStart());
		search.setEnd(vo.getEnd());

		if (vo.getTenantid() != null)
			search.setCompany(new CompanyDTO(vo.getTenantid()));

		search.setCompany(ApplicationContext.getContext().getCompany());
		search.setStatus(vo.getStatus());
		return search;
	}

	private static Map<?, ?> getAsMap(String data) throws JsonMappingException, JsonProcessingException {
		if (data == null)
			Collections.emptyMap();
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(data, Map.class);
	}

	public static RoleDTO toDTO(RoleVO roleVO) {
		RoleDTO dto = new RoleDTO();
		BeanUtils.copyProperties(roleVO, dto);
		return dto;
	}

	public static RoleVO toVO(RoleDTO role) {
		RoleVO dto = new RoleVO();
		BeanUtils.copyProperties(role, dto);
		return dto;
	}

	public static RankDTO toDTO(RankVO roleVO) {
		RankDTO dto = new RankDTO();
		BeanUtils.copyProperties(roleVO, dto);
		return dto;
	}

	public static RankVO toVO(RankDTO role) {
		RankVO dto = new RankVO();
		BeanUtils.copyProperties(role, dto);
		return dto;
	}

	public static CompanyDTO toDTO(CompanyVO compVo) {
		CompanyDTO companyDTO = new CompanyDTO();
		companyDTO.setId(compVo.getId());
		companyDTO.setName(compVo.getName());
		companyDTO.setUUID(compVo.getUUID());
		companyDTO.setIsActive(compVo.getIsActive());
		companyDTO.setAddress1(compVo.getAddress1());
		companyDTO.setAddress2(compVo.getAddress2());
		companyDTO.setCity(compVo.getCity());
		companyDTO.setCountry(compVo.getCountry());
		companyDTO.setItAdminUser(compVo.getItAdminUser());
		companyDTO.setNctype(compVo.getNctype());
		// companyDTO.setAuditTrail(Helper.getAuditTrail());
		return companyDTO;
	}

	public static CompanyVO toVO(CompanyDTO dto) {
		CompanyVO vo = new CompanyVO();
		vo.setId(dto.getId());
		vo.setName(dto.getName());
		vo.setIsActive(dto.getIsActive());
		vo.setUUID(dto.getUUID());
		vo.setAddress1(dto.getAddress1());
		vo.setAddress2(dto.getAddress2());
		vo.setCity(dto.getCity());
		vo.setCountry(dto.getCountry());
		vo.setItAdminUser(dto.getItAdminUser());
		vo.setNctype(dto.getNctype());
		// vo.setAuditTrail(Helper.getAuditTrail());
		return vo;
	}

	public static CompanySearchDTO toSearchDTO(CompanyVO compvo) {
		CompanySearchDTO dto = new CompanySearchDTO();

		dto.setName(compvo.getName());

		// dto.setCompany(ApplicationContext.getContext().getCompany());

		return dto;
	}

	@Autowired
	private UserDAO dao;
	@Autowired
	private EntitlementService serv;
	@Autowired
	private ShipService shipServ;

	public void populateContext(ContextDTO contextDTO, UserDTO userdto) {
		
		List<PrivillegeDTO> res = new ArrayList<PrivillegeDTO>();
		Map<PrivillegeDTO, Boolean> allUserPriv = serv.getAllPrivileges(userdto.getId().toString());

		for (Entry<PrivillegeDTO, Boolean> entry : allUserPriv.entrySet()) {
			if (!entry.getValue())
				continue;
			res.add(entry.getKey());
		}
		List<CompanyUserDTO> compUserDto = dao.loadCompUser(userdto.getUserName());
		contextDTO.setCompUsers(compUserDto);
		compUserDto.forEach(compUserRole -> {
			if (compUserRole.getRank() != null) {
				Optional<RoleDTO> optRole = serv.loadRoleQualifier("RANK_" + compUserRole.getRank().getName(),
						compUserRole.getCompany().getId() + "");
				optRole.ifPresent(role -> {
					serv.getPrivileges(role).stream().forEach(privilege -> res.add(privilege));
				});
			}
		});
		contextDTO.setPrivs(res);
		ApplicationContext.getContext().setShip(contextDTO.getShip());
		ApplicationContext.getContext().setCompany(contextDTO.getCompany());
		
		if (contextDTO.getShips() == null || contextDTO.getShips().isEmpty()) {
			contextDTO.setShips(shipServ.search(new ShipSearchDTO()));
		}

	}

	public static void main(String[] args) {
		String some = "vijay.xml";
		System.out.println(getExtention(some));

	}

	private static String getExtention(String fileName) {
		int index = fileName.lastIndexOf(".") + 1;
		if (index != 1) {
			return fileName.substring(index);
		}
		return "def";
	}

	public static AttachmentDTO toDTO(AttachmentVO aVO) {
		AttachmentDTO dto = new AttachmentDTO();
		dto.setUUID(aVO.getUuid());
		dto.setHeader(aVO.getHeader());
		dto.setDesc(aVO.getDesc());
		String fileName = aVO.getFile().getOriginalFilename();
//		dto.setLocation(aVO.getUuid()+"\\"+aVO.getSecondaryId()+"\\"+UUID.randomUUID() +"."+getExtention(fileName));
		dto.setLocationNoExt(aVO.getUuid().toString() + File.separatorChar + aVO.getSecondaryId() + File.separatorChar
				+ UUID.randomUUID());
		dto.setExt(getExtention(fileName));
		dto.setLocation(dto.getLocationNoExt() + "." + dto.getExt());
		dto.setType(aVO.getFile().getContentType());
		dto.setSecondaryId(aVO.getSecondaryId());
		return dto;
	}

	public static AttachmentVO toVO(AttachmentDTO dto) {
		AttachmentVO vo = new AttachmentVO();
		BeanUtils.copyProperties(dto, vo);
		vo.setUuid(dto.getUUID());
		return vo;
	}

	public static CheckListInstVO toVO(CheckListInstDTO src) {
		CheckListInstVO target = new CheckListInstVO();
		BeanUtils.copyProperties(src, target);
		if (src.getCompany() != null)
			target.setCompany(Helper.toVO(src.getCompany()));
		if (src.getVessel() != null)
			target.setVessel(Helper.toVO(src.getVessel()));
		return target;
	}

	public static SectionInstVO toVO(SectionInstDTO src) {
		SectionInstVO target = new SectionInstVO();
		BeanUtils.copyProperties(src, target);
		if (src.getCompany() != null)
			target.setCompany(Helper.toVO(src.getCompany()));
		if (src.getVessel() != null)
			target.setVessel(Helper.toVO(src.getVessel()));
		return target;
	}

	public static QuestionInstVO toVO(QuestionInstDTO src) {
		QuestionInstVO target = new QuestionInstVO();
		BeanUtils.copyProperties(src, target);
		if (src.getCompany() != null)
			target.setCompany(Helper.toVO(src.getCompany()));
		if (src.getVessel() != null)
			target.setVessel(Helper.toVO(src.getVessel()));
		return target;
	}

	public static QuestionInstDTO toDTO(QuestionInstVO src) {
		QuestionInstDTO target = new QuestionInstDTO();
		BeanUtils.copyProperties(src, target);
		if (src.getCompany() != null)
			target.setCompany(Helper.toDTO(src.getCompany()));
		if (src.getVessel() != null)
			target.setVessel(Helper.toDTO(src.getVessel()));
		return target;
	}

	public static NCDTO toDTO(NCVO ncVO) {
		NCDTO dto = new NCDTO();
		BeanUtils.copyProperties(ncVO, dto);
		if (ncVO.getQuestion() != null) {
			dto.setQuestion(Helper.toDTO(ncVO.getQuestion()));
		}
		return dto;
	}

	public static NCVO toVO(NCDTO dto) {
		NCVO ncVO = new NCVO();
		BeanUtils.copyProperties(dto, ncVO);
		if (dto.getQuestion() != null) {
			ncVO.setQuestion(Helper.toVO(dto.getQuestion()));
		}
		return ncVO;
	}

	public static Function<FleetShipVO, FleetShipDTO> helperMethod(final FleetDTO fleetDTO) {
		return new Function<FleetShipVO, FleetShipDTO>() {
			@Override
			public FleetShipDTO apply(FleetShipVO t) {
				FleetShipDTO dto = new FleetShipDTO();
				BeanUtils.copyProperties(t, dto);
				if (t.getShip() != null) {
					dto.setShip(Helper.toDTO(t.getShip()));
				}
				dto.setFleet(fleetDTO);
				return dto;
			}
		};

	}

	public static FleetDTO toDTO(FleetVO vo) {
		FleetDTO dto = new FleetDTO();
		dto.setFleetManager(Helper.toDTO(vo.getFleetManager()));
		if (vo.getCompany() != null)
			dto.setCompany(Helper.toDTO(vo.getCompany()));
		else
			dto.setCompany(ApplicationContext.getContext().getCompany());
		dto.setUuid(vo.getUUID());
		BeanUtils.copyProperties(vo, dto);
		return dto;
	}

	public static Function<FleetShipDTO, FleetShipVO> helperMethod(final FleetVO fleetVO) {
		return new Function<FleetShipDTO, FleetShipVO>() {
			@Override
			public FleetShipVO apply(FleetShipDTO src) {
				FleetShipVO tgt = new FleetShipVO();
				BeanUtils.copyProperties(src, tgt);
				if (src.getShip() != null) {
					tgt.setShip(Helper.toVO(src.getShip()));
				}
				tgt.setFleet(fleetVO);
				return tgt;
			}
		};

	}

	public static FleetVO toVO(FleetDTO dto) {
		FleetVO vo = new FleetVO();
		vo.setFleetManager(Helper.toVO(dto.getFleetManager()));
		vo.setCompany(Helper.toVO(dto.getCompany()));
		vo.setUUID(dto.getUuid());
		BeanUtils.copyProperties(dto, vo);
		return vo;
	}

}