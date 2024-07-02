package com.enoch.model;

import java.util.Date;
import java.util.UUID;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.attachment.dto.AttachmentDTO;
import com.enoch.controller.Helper;
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
import com.enoch.master.dto.CountryDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.master.model.Country;
import com.enoch.master.model.Port;
import com.enoch.model.checklist.ChecklistType;
import com.enoch.model.checklist.State;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.vo.AttachmentVO;
import com.enoch.vo.CheckListInstVO;
import com.enoch.vo.CheckListSectionVO;
import com.enoch.vo.CheckListVO;
import com.enoch.vo.CompanyVO;
import com.enoch.vo.ContextVO;
import com.enoch.vo.MasterChecklistItemVO;
import com.enoch.vo.MouVO;
import com.enoch.vo.PortVO;
import com.enoch.vo.QuestionInstVO;
import com.enoch.vo.SectionInstVO;
import com.enoch.vo.ShipVO;
import com.enoch.vo.UserVO;
import com.enoch.vo.VoyageSearchVO;
import com.enoch.vo.VoyageVO;
import com.enoch.vo.entitlement.RankVO;
import com.enoch.vo.entitlement.RoleVO;
import com.fasterxml.jackson.databind.ObjectMapper;

@DataJpaTest
@RunWith(SpringRunner.class)
public class HelperTest {
	
	@Autowired
	private TestEntityManager entityManager;
	@Autowired
	private Helper helper;
	
	@Test
	public void testShiptoDTO() throws DataException {
		
		ShipVO vo = new ShipVO();
		
		ShipDTO dto = helper.toDTO(vo);
		
		assert testShiptoDTO(dto, vo) : "error in testShiptoDTO";
		
	}
	
	@Test
	public void testShiptoVO() throws DataException {
		
		ShipDTO dto = getShipDTO();
	
		ShipVO vo = helper.toVO(dto);
		
		assert testShiptoVO(vo, dto) : "error in testShiptoVO";
		
	}
	
	@Test
	public void testShiptoSearchDTO() throws DataException {
		
		ShipSearchDTO sdto = new ShipSearchDTO();
		ShipVO vo = new ShipVO();
		
		helper.toSearchDTO(vo);
		
		assert testShiptoSearchDTO(sdto, vo) : "error in testShiptoSearchDTO";
	
	}
	
	@Test
	public void testVoyagetoDTO() throws DataException {
		
		VoyageVO vo = getVoyageVO();
		
		VoyageDTO dto = helper.toDTO(vo);		
		
		assert testVoyagetoDTO(dto, vo) : "error in testVoyagetoDTO";
		
	}
	
	@Test
	public void testVoyagetoVO() throws DataException {
		
		VoyageDTO dto = getVoyageDTO();
		
		VoyageVO vo = helper.toVO(dto);
		
		assert testVoyagetoVO(vo, dto) : "error in testVoyagetoVO";
		
	}
	
	@Test
	public void testVoyagetoSearchDTO() throws DataException {
		
		VoyageSearchVO svo = getVoyageSearchVO();
		
		VoyageSearchDTO sdto = helper.toSearchDTO(svo);

		assert testVoyagetoSearchDTO(sdto, svo) : "error in testVoyagetoSearchDTO";
	}
	
	@Test
	public void testUsertoDTO() throws DataException {
		
		UserVO vo = getUserVO();
		
		UserDTO dto = helper.toDTO(vo);
		
		assert testUsertoDTO(dto,vo) : "testUsertoDTO";
				
	}
	
	@Test
	public void testUsertoVO() throws DataException {
		
		UserDTO dto = getUserDTO();
		
		UserVO vo = helper.toVO(dto);
		
		assert testUsertoVO(vo, dto) : "error in testUsertoVO";
		
	}
	
	@Test
	public void testUsertoSearchDTO() throws DataException {
		
		UserVO vo = getUserVO();
		
		UserSearchDTO sdto = helper.toSearchDTO(vo);
		
		assert testUsertoSearchDTO(sdto, vo);
		
	}
	
	@Test
	public void testCompanytoDTO() throws DataException {
		
		CompanyVO vo = getCompanyVO();
		
		CompanyDTO dto = helper.toDTO(vo);
		
		assert testCompanytoDTO(dto, vo) : "error in company";
		
	}
	
	@Test
	public void testCompanytoVO() throws DataException {
			
		CompanyDTO dto = getCompanyDTO();
		
		CompanyVO vo = helper.toVO(dto);
		
		assert testCompanytoVO(vo, dto) : "error in company";
		
		
	}
	
	@Test
	public void testCompanytoSearchDTO() throws DataException {
		
		CompanyVO vo = getCompanyVO();
		
		CompanySearchDTO dto = helper.toSearchDTO(vo);
		
		assert testCompanytoSearchDTO(dto, vo) : "error in testCompanytoSearchDTO";
		
	}
	
	@Test
	public void testAttachmenttoDTO() throws DataException {
		
		AttachmentVO vo = getAttachmentVO();
		
		AttachmentDTO dto = helper.toDTO(vo);
		
		assert testAttachmenttoDTO(dto, vo) : "error in testAttachmenttoDTO";
		
	}

	@Test
	public void testAttachmenttoVO() throws DataException {
		
		AttachmentDTO dto = getAttachmentDTO();
		
		AttachmentVO vo = helper.toVO(dto);
		
		assert testAttachmenttoVO(vo, dto) : "error in testAttachmenttoVO";
		
	}
	
	@Test
	public void testPorttoDTO() throws DataException {
		
		PortVO vo = getPortVO();
		
		PortDTO dto = helper.toDTO(vo);
		
		assert testPorttoDTO(dto, vo) : "error in testPorttoDTO";
		
	}
	
	@Test
	public void testPorttoVO() throws DataException {
		
		PortDTO dto = getPortDTO();
		
		PortVO vo = helper.toVO(dto);
		
		assert testPorttoVO(vo, dto) : "error in testPorttoVO";
	}
	
	@Test
	public void testSectiontoDTO() throws DataException {
		
		CheckListSectionVO vo = getCheckListSectionVO();
		
		SectionDTO dto = helper.toDTO(vo);
		
		assert testSectiontoDTO(dto, vo) : "error in testSectiontoDTO";
		
	}
	
	@Test
	public void testMasterChecklistItemtoVO() throws DataException {
		
		QuestionDTO dto = getQuestionDTO();
		
		MasterChecklistItemVO vo = helper.toVO(dto);
		
		assert testMasterChecklistItemtoVO(vo, dto) : "error in testMasterChecklistItemtoVO";
		
	}
	
	@Test
	public void testMoutoDTO() throws DataException {
		
		MouVO vo = getMouVO();
		
		MouDTO dto = helper.toDTO(vo);
		
		assert testMoutoDTO(dto, vo) : "error in testMoutoDTO";
		
	}
	
	@Test
	public void testCheckListInsttoVO() throws DataException {
		
		CheckListInstDTO dto = getCheckListInstDTO();
		
		CheckListInstVO vo = helper.toVO(dto);
		
		assert testCheckListInsttoVO(vo, dto): "error in testCheckListInsttoVO";
		
	}
	
	@Test
	public void testSectionInsttoVO() throws DataException {
		
		SectionInstDTO dto = getSectionInstDTO();
		
		SectionInstVO vo = helper.toVO(dto);
		
		assert testSectionInsttoVO(vo, dto) : "error in testSectionInsttoVO";
		
	}
	
	@Test
	public void testQuestionInsttoVO() throws DataException {
		
		QuestionInstDTO dto = getQuestionInstDTO();
		
		QuestionInstVO vo = helper.toVO(dto);
		
		assert testQuestionInsttoVO(vo, dto) : "error in testQuestionInsttoVO";
		
	}
	
	@Test
	public void testRoletoDTO() throws DataException {
		
		RoleVO vo = getRoleVO();
		
		RoleDTO dto = helper.toDTO(vo);
		
		assert testRoletoDTO(dto, vo) : "error in testRoletoDTO";
		
	}
	
	@Test
	public void testRoletoVO() throws DataException {
		
		RoleDTO dto = getRoleDTO();
		
		RoleVO vo = helper.toVO(dto);
		
		assert testRoletoVO(vo, dto) : "error in testRoletoVO";
		
	}
	
	@Test
	public void testRanktoDTO() throws DataException {
		
		RankVO vo = getRankVO();
		
		RankDTO dto = helper.toDTO(vo);
		
		assert testRanktoDTO(dto, vo) : "error in testRanktoDTO";
		
	}
	
	@Test
	public void testRanktoVO() throws DataException {
						
		RankDTO dto = getRankDTO();
		
		RankVO vo = helper.toVO(dto);
		
		assert testRanktoVO(vo,dto) : "error in testRanktoVO"; 
		
	}
	
	@Test 
	public void testCheckListtoVO() throws DataException {
		
		CheckListDTO dto = getCheckListDTO();
		
		CheckListVO vo = helper.toVO(dto);
		
		assert testCheckListtoVO(vo, dto): "error in testCheckListtoVO";

	}
	
	@Test
	public void testCheckListSectiontoVO() throws DataException {
		
		SectionDTO dto = getSectionDTO();
		
		CheckListSectionVO vo = helper.toVO(dto);
		
		assert testCheckListSectiontoVO(vo, dto) : "error in testCheckListSectiontoVO";
		
	}
	
	@Test
	public void testPopulateContext() throws DataException {
		
		ContextDTO cdto = getContextDTO();
		UserDTO udto = getUserDTO();
		
		helper.populateContext(cdto, udto);
		
		assert testPopulateContext(cdto, udto) : "error in testPopulateContext";
	}
	
	private CompanyDTO getCompanyDTO() {
		
		CompanyDTO dto = new CompanyDTO();
		
//		dto.setId(1l);
//		dto.setName("name");
//		dto.setUUID(UUID.randomUUID());
//		dto.setAddress1("address1");
//		dto.setAddress2("address2");
//		dto.setCountry("country");
//		dto.setCity("city");
//		dto.setItAdminUser("itAdminUser");
//		dto.setIsActive(true);
//		dto.setAuditTrail(null);
		
		return dto;
	}
	
	private CompanyVO getCompanyVO() {
		
		CompanyVO vo = new CompanyVO();
		
//		vo.setId(1l);
//		vo.setName("name");
//		vo.setUUID(UUID.randomUUID());
//		vo.setAddress1("address1");
//		vo.setAddress2("address2");
//		vo.setCountry("country");
//		vo.setCity("city");
//		vo.setItAdminUser("itAdminUser");
//		vo.setIsActive(true);
//		vo.setAuditTrail(null);
		
		return vo;
		
	}
	
	private CompanySearchDTO getCompanySearchDTO() {
		
		CompanySearchDTO sdto = new CompanySearchDTO();
		
		return sdto;
	}
	
	private CompanyUserDTO getCompanyUserDTO() {
		
		CompanyUserDTO dto = new CompanyUserDTO();
		
		return dto;
	}
	
	private ShipDTO getShipDTO() {
		
		ShipDTO dto = new ShipDTO();
		
		dto.setId(1l);
		dto.setName("name");
		dto.setUUID(UUID.randomUUID());
		dto.setImo("imo");
		dto.setVesselType("VesselType");
		dto.setCalss("calss");
		dto.setFlag("flag");
		dto.setGrt("grt");
		dto.setCallSign("callSign");
		dto.setCompany(getCompanyDTO());
		dto.setStatus("status");
		dto.setEmail("email");
		dto.setActivesince(new Date());
		
		return dto;
		
	}
	
	private ShipVO getShipVO() {
		
		ShipVO vo = new ShipVO();
		
		vo.setId(1l);
		vo.setName("name");
		vo.setUUID(UUID.randomUUID());
		vo.setImo("imo");
		vo.setVesselType("VesselType");
		vo.setCalss("calss");
		vo.setFlag("flag");
		vo.setGrt("grt");
		vo.setCallSign("callSign");
		vo.setCompany(getCompanyVO());
		vo.setStatus("status");
		vo.setEmail("email");
		vo.setActivesince(new Date());
		
		return vo;
	}
	
	private ShipSearchDTO getShipSearchDTO() {
		
		ShipSearchDTO sdto = new ShipSearchDTO();
		
		return sdto;
		
	}
	
	private UserDTO getUserDTO() {
		
		UserDTO dto = new UserDTO();
		
		dto.setId(1l);
		dto.setName("name");
		dto.setUserName("userName");
		dto.setLastName("lastName");
		dto.setFirstName("firstName");
		dto.setPassword("password");
		dto.setRank(getRankDTO());
		dto.setCdc("cdc");
		dto.setPassport("passport");
		dto.setDob(new Date());
		
		return dto;
	}
	
	private UserVO getUserVO() {
		
		UserVO vo = new UserVO();
		
		vo.setId(1l);
		vo.setName("name");
		vo.setUserName("userName");
		vo.setLastName("lastName");
		vo.setFirstName("firstName");
		vo.setPassword("password");
		vo.setRank(getRankDTO().getCode());
		vo.setCdc("cdc");
		vo.setPassport("passport");
		vo.setDob(new Date());
		
		return vo;
	}
	
	private UserSearchDTO getUserSearchDTO() {
		
		UserSearchDTO sdto = new UserSearchDTO();
		
		return sdto;
	}
	
	private VoyageDTO getVoyageDTO() {
		
		VoyageDTO dto = new VoyageDTO();
		
		dto.setId(1l);
		dto.setShip(getShipDTO());
		dto.setCompany(getCompanyDTO());
		dto.setVoyageNo("1");
		dto.setOrgin(getPort());
		dto.setDestination(getPort());
		dto.setAtd(new Date());
		dto.setEta(new Date());
		dto.setChecklist_etc(new Date());
		dto.setStatus(null);
		
		return dto;
	}
	
	private VoyageVO getVoyageVO() {
		
		VoyageVO vo = new VoyageVO();
		
		vo.setId(1l);
		vo.setMasterVesselName(getShipDTO().getName());
		vo.setVoyageNo("1");
		vo.setAtd(new Date());
		vo.setEta(new Date());
		vo.setChecklist_etc(new Date());
		
		return vo;
	}
	
	private VoyageSearchDTO getVoyageSearchDTO() {
		
		VoyageSearchDTO sdto = new VoyageSearchDTO();
		
		return sdto;
	}
	
	private VoyageSearchVO getVoyageSearchVO() {
		
		VoyageSearchVO svo = new VoyageSearchVO();
		
		return svo;
	}
	
	private RankDTO getRankDTO(){
		
		RankDTO dto = new RankDTO();
		
		dto.setCode("code");
		dto.setDescription("description");
		dto.setName("name");
		dto.setAuditTrail(null);
		dto.setIsAvailable(true);
		dto.setMultiUser(true);
		
		return dto;
	}
	
	private RankVO getRankVO() {
		
		RankVO vo = new RankVO();
		
		vo.setCode("code");
		vo.setDescription("description");
		vo.setName("name");
		vo.setIsAvailable(true);
		vo.setMultiUser(true);
		
		return vo;
	}
	
	private PortDTO getPortDTO() {
		
		PortDTO dto = new PortDTO();
		
		dto.setCity("city");
		dto.setCode("code");
		dto.setCountry(getCountryDTO());
		dto.setDescription("description");
		
		return dto;
	}
	
	private PortVO getPortVO() {
		
		PortVO vo = new PortVO();
		
		vo.setCity("city");
		vo.setCode("code");
		vo.setCountry("country");
		vo.setDescription("description");
		
		return vo;
	}
	
	private Port getPort() {
		
		Port port = new Port();
		
		port.setCity("city");
		port.setCode("code");
		port.setCountry(getCountry());
		port.setDescription("description");
		
		return port;
	}
	
	private RoleDTO getRoleDTO() {
		
		RoleDTO dto = new RoleDTO();
		
		dto.setId(1l);
		dto.setName("name");
		dto.setQualifier("qualifier");
		dto.setCode("code");
		dto.setDescription("description");
		dto.setDeflt(true);
		dto.setStatus(true);
		
		return dto;
	}
	
	private RoleVO getRoleVO() {
		
		RoleVO vo = new RoleVO();
		
		vo.setId(1l);
		vo.setName("name");
		vo.setQualifier("qualifier");
		vo.setCode("code");
		vo.setDescription("description");
		vo.setDeflt(true);
		vo.setStatus(true);
		
		return vo;
	}
	
	private SectionDTO getSectionDTO() {
		
		SectionDTO dto = new SectionDTO();
		
		dto.setId(1l);
		dto.setCompany(getCompanyDTO());
		dto.setActive(true);
		dto.setName("name");
		dto.setData("data");
		dto.setAuditTrail(null);
		dto.setLongDescription("longDescription");
		dto.setShortDesc("shortDesc");
		dto.setPreProcessor("preProcessor");
		dto.setPostProcessor("postProcessor");
		dto.setState(State.DRAFT);
		
		return dto;
	}
	
	private AttachmentDTO getAttachmentDTO() {
		
		AttachmentDTO dto = new AttachmentDTO();
		
//		dto.setUUID(UUID.randomUUID());
//		dto.setAuditTrail(null);
//		dto.setDesc("desc");
//		dto.setLocation("location");
//		dto.setHeader("header");
//		dto.setType("type");
//		dto.setId(1l);
//		dto.setMulti(true);
//		dto.setSecondaryId("1");
		
		return dto;
	}
	
	private AttachmentVO getAttachmentVO() {
		
		AttachmentVO vo = new AttachmentVO();
		
		return vo;
	}
	
	private CheckListDTO getCheckListDTO() {
		
		CheckListDTO dto = new CheckListDTO();
		
		dto.setId(1l);
		dto.setLongDesc("longDesc");
		dto.setCompany(getCompanyDTO());
		dto.setActive(true);
		dto.setName("name");
		dto.setData("data");
		dto.setType(ChecklistType.SIMPLE);
		dto.setState(State.DRAFT);
		dto.setShortDesc("shortDesc");
		dto.setRemarks("remarks");
		dto.setLongDesc("longDesc");
		dto.setCheckId(UUID.randomUUID());
		dto.setPreProcessor("preProcessor");
		dto.setPostProcessor("postProcessor");
		//dto.setAuditTrail(null);
		
		return dto;
	}
	
	private QuestionDTO getQuestionDTO() {
		
		QuestionDTO dto = new QuestionDTO();
		
		dto.setId(1l);
		dto.setActive(true);
//		dto.setAuditTrail(null);
	    dto.setCompany(getCompanyDTO());
		dto.setPreProcessor("preProcessor");
		dto.setPostProcessor("postProcessor");
		dto.setParentUUId(UUID.randomUUID());
		
		return dto;
	}
	
	private SectionInstDTO getSectionInstDTO() {
		
		SectionInstDTO dto = new SectionInstDTO();
		
		return dto;
	}
	
	private SectionInstVO getSectionInstVO() {
		
		SectionInstVO vo = new SectionInstVO();
		
		return vo;
	}
	
	private CheckListInstDTO getCheckListInstDTO() {
		
		CheckListInstDTO dto = new CheckListInstDTO();
		
		return dto;
	}
	
	private CheckListInstVO getCheckListInstVO() {
		
		CheckListInstVO vo = new CheckListInstVO();
		
		return vo;
	}
	
	private MouDTO getMouDTO() {
		
		MouDTO dto = new MouDTO();
		
		return dto;
	}
	
	private MouVO getMouVO() {
		
		MouVO vo = new MouVO();
		
		return vo;
	}
	
	private QuestionInstDTO getQuestionInstDTO() {
		
		QuestionInstDTO dto = new QuestionInstDTO();
		
		return dto;
	}
	
	private QuestionInstVO getQuestionInstVO() {
		
		QuestionInstVO vo = new QuestionInstVO();
		
		return vo;
	}
	
	private ContextDTO getContextDTO() {
		
		ContextDTO dto = new ContextDTO();
		
		return dto;
	}
	
	private ContextVO getContextVO() {
		
		ContextVO vo = new ContextVO();
		
		return vo;
	}
	
	private CheckListSectionVO getCheckListSectionVO() {
		
		CheckListSectionVO vo = new CheckListSectionVO();
		
		return vo;
	}
	
	private CountryDTO getCountryDTO() {
		
		CountryDTO dto = new CountryDTO();
		
		dto.setName("name");
		dto.setCode("code");
		dto.setDescription("description");
		dto.setDefaultcurrency("defaultcurrency");
		dto.setDefaultlanguage("defaultlanguage");
		
		return dto;
	}
	
	private Country getCountry() {
		
		Country country = new Country();
		
		return country;
		
	}
	
	private PrivillegeDTO getPrivillegeDTO() {
		
		PrivillegeDTO dto = new PrivillegeDTO();
		
		 return dto;
	}
	
	private ObjectMapper getObjectMapper() {
	
	ObjectMapper mapper = new ObjectMapper();
	
	return mapper;
	
	}
	
	private boolean testShiptoDTO(ShipDTO dto, ShipVO vo) {
		
		assert dto.getId() == vo.getId() : "error in ship id";
		
		assert dto.getUUID() == vo.getUUID() : "error in ship uuid";
		
		assert dto.getName() == vo.getName() : "error in ship name";
		
		assert dto.getImo() == vo.getImo() : "error in ship imo";
		
		assert dto.getVesselType() == vo.getVesselType() : "error in ship vesseltype";
		
		assert dto.getCalss() == vo.getCalss() : "error in ship calss";
		
		assert dto.getFlag() == vo.getFlag() : "error in ship flag";
		
		assert dto.getGrt() == vo.getGrt() : "error in ship grt";
		
		assert dto.getCallSign() == vo.getCallSign() : "error in ship callsign";
		
		assert testCompanytoDTO(getCompanyDTO(), getCompanyVO()) : "error in ship company";
		
		assert dto.getStatus() == vo.getStatus() : "error in ship status";
		
		assert dto.getEmail() == vo.getEmail() : "error in ship email";
		
		assert dto.getActivesince() == vo.getActivesince() : "error in ship date";
		
		assert dto.getAuditTrial() == vo.getAuditTrial() : "error in ship audittrial";
		
		return true;
		
	}
	
	private boolean testShiptoVO(ShipVO vo, ShipDTO dto) {
		
		assert vo.getId() == dto.getId() : "error in ship id";
		
		assert vo.getName() == dto.getName() : "error in ship name";
		
		assert vo.getUUID() == dto.getUUID() : "error in ship uuid";
		
		assert vo.getImo() == dto.getImo() : "error in ship imo";
		
		assert vo.getVesselType() == dto.getVesselType() : "error in ship vesseltype";
		
		assert vo.getCalss() == dto.getCalss() : "error in ship calss";
		
		assert vo.getFlag() == dto.getFlag() : "error in ship flag";
		
		assert vo.getGrt() == dto.getGrt() : "error in ship grt";
		
		assert vo.getCallSign() == dto.getCallSign() : "error in ship callsign";
		
		assert testCompanytoDTO(dto.getCompany(), vo.getCompany()) : "error in company";
		
		assert vo.getStatus() == dto.getStatus() : "error in ship status";
		
		assert vo.getEmail() == dto.getEmail() : "error in ship email";
		
		assert vo.getActivesince() == dto.getActivesince() : "error in ship date";
		
		return true;
	}
	
	private boolean testShiptoSearchDTO(ShipSearchDTO sdto, ShipVO vo) {
		
		assert sdto.getId() == vo.getId() : "error in ship id";
		
		assert sdto.getName() == vo.getName() : "error in ship name";
		
		assert sdto.getImo() == vo.getImo() : "error in ship imo";
		
		assert sdto.getVesselType() == vo.getVesselType() : "error in ship vesseltype";
		
		assert sdto.getCalss() == vo.getCalss() : "error in ship calss";
		
		assert sdto.getFlag() == vo.getFlag() : "error in ship flag";
		
		assert sdto.getGrt() == vo.getGrt() : "error in ship grt";
		
		assert sdto.getCallSign() == vo.getCallSign() : "error in ship callsign";
		
		assert testCompanytoDTO(getCompanyDTO(), getCompanyVO()) : "error in company";
			
		assert sdto.getStatus() == vo.getStatus() : "error in ship status";
		
		assert sdto.getEmail() == vo.getEmail() : "error in ship email";
		
		assert sdto.getActivesince() == vo.getActivesince() : "error in ship date";
		
		return true;
		
	}
	
	private boolean testCompanytoDTO(CompanyDTO dto, CompanyVO vo) {
		
		assert dto.getId() == vo.getId() : "error in company id";
		
		assert dto.getName() == vo.getName() : "error in company name";
		
		assert dto.getUUID() == vo.getUUID() : "error in company uuid";
		
		assert dto.getIsActive() == vo.getIsActive() : "error in company isActive";
		
		assert dto.getAddress1() == vo.getAddress1() : "error in company address 1";
		
		assert dto.getAddress2() == vo.getAddress2() : "error in company address 2";
		
		assert dto.getCity() == vo.getCity() : "error in company city";
		
		assert dto.getCountry() == vo.getCountry() : "error in company country";
		
		assert dto.getItAdminUser() == vo.getItAdminUser() : "error in company ItAdminUser";
		
		return true;
		
	} 
	
	private boolean testCompanytoVO(CompanyVO vo, CompanyDTO dto) {
		
        assert vo.getId() == dto.getId() : "error in company id";
		
		assert vo.getName() == dto.getName() : "error in company name";
		
		assert vo.getUUID() == dto.getUUID() : "error in company uuid";
		
		assert vo.getIsActive() == dto.getIsActive() : "error in company isActive";
		
		assert vo.getAddress1() == dto.getAddress1() : "error in company address 1";
		
		assert vo.getAddress2() == dto.getAddress2() : "error in company address 2";
		
		assert vo.getCity() == dto.getCity() : "error in company city";
		
		assert vo.getCountry() == dto.getCountry() : "error in company country";
		
		assert vo.getItAdminUser() == dto.getItAdminUser() : "error in company ItAdminUser";
		
		return true;
		
	}
	
	private boolean testCompanytoSearchDTO(CompanySearchDTO dto, CompanyVO vo) {
		
		assert dto.getName() == vo.getName() : "error in company name";
		
		return true;
		
	}
	
	private boolean testUsertoDTO(UserDTO dto,UserVO vo) {
		
		assert dto.getId() == vo.getId() : "error in user id";
		
        assert dto.getUUID() == vo.getUUID() : "error in user uuid";
		
		assert dto.getUserName() == vo.getUserName() : "error in username";
		
		assert dto.getFirstName() == vo.getFirstName() : "error in user firstname";
		
		assert dto.getLastName() == vo.getLastName() : "error in user last name";
		
		assert dto.getPassword() == vo.getPassword() :"error in user password";
		
		assert dto.getCdc() == vo.getCdc() : "error in user cdc";
		
		assert dto.getPassport() == vo.getPassport() : "error in user passport";
		
		assert dto.getDob() == vo.getDob() : "error in user DOB";
		
		assert testRanktoDTO(getRankDTO(), getRankVO()) : "error in user rank";
		
		assert getCompanyDTO().getId() == vo.getCompanyId() : "error in user company";
		
		assert getCompanyDTO().getName() == vo.getShipcompany() : "error in user company name";
		
		return true;
		
	}
	
	private boolean testUsertoVO(UserVO vo, UserDTO dto) {

		assert vo.getId() == dto.getId() : "error in user id";
		
		assert vo.getUUID() == dto.getUUID() : "error in user uuid";
		
        assert vo.getUserName() == dto.getUserName() : "error in username";
		
		assert vo.getFirstName() == dto.getFirstName() : "error in user firstname";
		
		assert vo.getLastName() == dto.getLastName() : "error in user last name";
		
		assert vo.getPassword() == dto.getPassword() :"error in user password";
		
		assert vo.getRank() == dto.getRank().getCode() : "error in user rank";
		
		assert vo.getCdc() == dto.getCdc() : "error in user cdc";
		
		assert vo.getPassport() == dto.getPassport() : "error in user passport";
		
		assert vo.getDob() == dto.getDob() : "error in user DOB";
		
		assert vo.getVesselId() == 0l : "error in user vesselId";
		
		assert vo.getVesselName() == "HELPER" : "error in user vesselName";
		
		return true;
	}
	
	private boolean testUsertoSearchDTO(UserSearchDTO sdto, UserVO vo) {
		
		assert sdto.getId() == vo.getId() : "error in user id";
		
        assert sdto.getUserName() == vo.getUserName() : "error in username";
		
		assert sdto.getFirstName() == sdto.getFirstName() : "error in user firstname";
		
		assert sdto.getLastName() == vo.getLastName() : "error in user last name";
		
        assert sdto.getRank() == sdto.getRank() : "error in user rank";
		
		assert sdto.getPassport() == sdto.getPassport() : "error in user passport";
		
		return true;
	}
	
	private boolean testVoyagetoDTO(VoyageDTO dto, VoyageVO vo) {
		
		assert dto.getId() == vo.getId() : "error in voyage id";
		
		assert testCompanytoDTO(getCompanyDTO(), getCompanyVO()) : "error in company";
		
		assert dto.getVoyageNo() == vo.getVoyageNo(): "error in voyageNo.";
		
		assert dto.getOrgin() == getPort() : "error in voyage orgin";
		
		assert dto.getDestination() == getPort() : "error in voyage destination";
		
		assert dto.getAtd() == vo.getAtd() : "error in voyage atd";
		
		assert dto.getEta() == vo.getEta() : "error in voyage eta";
		
		assert dto.getChecklist_etc() == vo.getChecklist_etc() : "error in voyage checklist_etc";
		
		if(vo.getVesseilId() != null)
		assert testShiptoDTO(getShipDTO(), getShipVO()) : "error in voyage ship";
		
		assert dto.getStatus() == vo.getStatus() : "error in voyage status";
		
		return true;
		
	}
	
	private boolean testVoyagetoVO(VoyageVO vo, VoyageDTO dto) {
		
		assert vo.getId() == dto.getId() : "error in voyage id";
		
		assert vo.getMasterVesselName() == dto.getShip().getName() : "error in voyage ship";
		
		assert vo.getVoyageNo() == dto.getVoyageNo() : "error in voyageNo.";
		
		assert vo.getOrgin() == dto.getOrgin().getCode() : "error in voyage orgin";
		
		assert vo.getOrginDesc() == dto.getOrgin().getDescription() : "error in voyage orginDesc";
		
		assert vo.getDestination() == dto.getDestination().getCode() : "error in voyage destination";
		
		assert vo.getDestDesc() == dto.getDestination().getDescription() : "error in voyage destDesc";
		
		assert vo.getAtd() == dto.getAtd() : "error in voyage atd";
		
		assert vo.getEta() == dto.getEta() : "error in voyage eta";
		
		assert vo.getChecklist_etc() == dto.getChecklist_etc() : "error in voyage checklist_etc";
		
		assert vo.getStatus() == dto.getStatus() : "error in voyage status";
		
		return true;
		
	}
	
	private boolean testVoyagetoSearchDTO(VoyageSearchDTO sdto, VoyageSearchVO svo) {
		
		//assert sdto.getShip() == new ShipDTO(svo.getVesseilId()) : "error in voyage ship";
		if (svo.getVesseilId() != null && svo.getVesseilId() > 0)
		assert testShiptoDTO(getShipDTO(), getShipVO()) : "error in ship";
				
		assert sdto.getVoyageNo() == svo.getVoyageNo() : "error in voyageNo.";
				
		assert sdto.getOrgin() == svo.getOrgin() : "error in voyage orgin";
				
		assert sdto.getDestination() == svo.getDestination() : "error in voyage destination";
				
		assert sdto.getStart() == svo.getStart() : "error in voyage startDate";
				
		assert sdto.getEnd() == svo.getEnd() : "error in voyage endDate";
				
		assert testCompanytoDTO(getCompanyDTO(), getCompanyVO()) : "error in voyage company";
				
		assert sdto.getStatus() == svo.getStatus() : "error in voyage status";
		
		return true;
		
	}
	
	private boolean testAttachmenttoDTO(AttachmentDTO dto, AttachmentVO vo) {
		
		assert dto.getUUID() == vo.getUuid() : "error in attachment uuid";
		
		assert dto.getHeader() == vo.getHeader() : "error in attachment header";
		
		assert dto.getDesc() == vo.getDesc() : "error in attachment desc";
		
		assert dto.getLocation() == vo.getFile().getOriginalFilename() : "error in location";
		
		assert dto.getType() == vo.getFile().getContentType() : "error in attachment type";
		
		assert dto.getSecondaryId() == vo.getSecondaryId() : "error in attachemnt secondaryId";
		
		return true;
	
	}
	
	private boolean testAttachmenttoVO(AttachmentVO vo, AttachmentDTO dto) {
		
		assert vo.getUuid() == dto.getUUID() : "error in attachment uuid";
		
		return true;
		
	}
	
	private boolean testPorttoDTO(PortDTO dto, PortVO vo) {
		
		assert dto.getCountry() == getCountryDTO() : "error in port country";
		
		return true;
	}
	
	private boolean testPorttoVO(PortVO vo, PortDTO dto) {
		
		assert vo.getCountry() == dto.getCountry().getCode() : "error in port country";
		
		return true;
	}
	
	private boolean testSectiontoDTO(SectionDTO dto, CheckListSectionVO vo) {
		
		assert dto.getId() == vo.getId() : "error in section id";
		
		assert testCompanytoDTO(getCompanyDTO(), getCompanyVO()) : "error in company";
		
		assert dto.getName() == vo.getSectionName() : "error in sectionName";
		
		assert dto.getShortDesc() == vo.getDesc() : "error in sectionDesc";
		
		assert dto.getLongDescription() == vo.getLongDescription() : "error in section description";
		
		assert dto.getPreProcessor() == vo.getPreProcessor() : "error in section perProcessor";
		
		assert dto.getPostProcessor() == vo.getPostProcessor() : "error in section perProcessor";
		
		assert dto.getSlNo() == vo.getSlno() : "error in section slno.";
		
		assert dto.getActive() == vo.getActive() : "error in section active";
		
		assert dto.getState() == vo.getState() : "error in section state";
		
		return true;
		
	}
	
	private boolean testMasterChecklistItemtoVO(MasterChecklistItemVO vo, QuestionDTO dto) {
		
		assert vo.getId() == dto.getUuid() : "error in MasterChecklistItem id";
		
		assert vo.getResponsibility() == dto.getResponsibility() : "error in MasterChecklistItem responsibility";
		
		assert vo.getQuestionDesc() == dto.getQuestionText() : "error in MasterChecklistItem question";
		
		assert vo.getType() == dto.getType() : "error in MasterChecklistItem type";
		
		assert vo.getParentId() == dto.getParentUUId() : "error in MasterChecklistItem parentId";
		
		assert vo.getSlno() == dto.getSerNo() : "error in MasterChecklistItem Slno.";
		
		assert vo.getData() == null : "error in MasterChecklistItem data";
		
		return true;
		
	}
	
	private boolean testMoutoDTO(MouDTO dto, MouVO vo) {
		
		assert dto.getId() == null : "error in mou id";
		
		assert dto.getCode() == vo.getCode() : "error in mou code";
		
		assert dto.getCicid() == vo.getCicid() : "error in mou cicid";
		
		assert dto.getFromDate() == vo.getFromDate() : "error in mou fromDate";
		
		assert dto.getToDate() == vo.getToDate() : "error in mou toDate";
		
		//assert dto.getAuditTrail() == null : "error in mou auditTrail";
		
		return true;
		
	}
	
	private boolean testCheckListInsttoVO(CheckListInstVO vo, CheckListInstDTO dto) {
		
		if(dto.getCompany()!=null)
		assert testCompanytoVO(getCompanyVO(), getCompanyDTO()) : "error in checklistInst company";
		
		if(dto.getVessel()!=null)
		assert testShiptoVO(getShipVO(), getShipDTO()) : "error in checklistInst ship";
		
		return true;
		
	}
	
	private boolean testSectionInsttoVO(SectionInstVO vo, SectionInstDTO dto) {
		
		if(dto.getCompany()!=null)
		assert testCompanytoVO(getCompanyVO(), getCompanyDTO()) : "error in sectionInst company";
		
		if(dto.getVessel()!=null)
		assert testShiptoVO(getShipVO(), getShipDTO()) : "error in sectionInst ship";
		
		return true;
		
	}
	
	private boolean testQuestionInsttoVO(QuestionInstVO vo, QuestionInstDTO dto) {
		
		if(dto.getCompany()!=null)
		assert testCompanytoVO(getCompanyVO(), getCompanyDTO()) : "error in questionInst company";
		
		if(dto.getVessel()!=null)
		assert testShiptoVO(getShipVO(), getShipDTO()) : "error in questionInst ship"; 
		
		return true;
		
	}
	
	private boolean testRoletoDTO(RoleDTO dto, RoleVO vo) {
		
		assert dto.getId() == vo.getId() : "error in role id";
		
		assert dto.getQualifier() == vo.getQualifier() : "error in role name";
		
		assert dto.getName() == vo.getName() : "error in role name";
		
		assert dto.getCode() == vo.getCode() : "error in role code";
		
		assert dto.getDescription() == vo.getDescription() : "error in role description";
		
		assert dto.getStatus() == vo.getStatus() : "error in role status";
		
		assert dto.getDeflt() == vo.getDeflt() : "error in role deflt";
		
		return true;
		
	}
	
	private boolean testRoletoVO(RoleVO vo, RoleDTO dto) {
		
		assert vo.getId() == dto.getId() : "error in role id";
		
		assert vo.getQualifier() == dto.getQualifier() : "error in role name";
		
		assert vo.getName() == dto.getName() : "error in role name";
		
		assert vo.getCode() == dto.getCode() : "error in role code";
		
		assert vo.getDescription() == dto.getDescription() : "error in role description";
		
		assert vo.getStatus() == dto.getStatus() : "error in role status";
		
		assert vo.getDeflt() == dto.getDeflt() : "error in role deflt";
		
		return true;
		
	}
	
	private boolean testRanktoDTO(RankDTO dto, RankVO vo) {
		
		assert dto.getName() == vo.getName() : "error in rank name";
		
		assert dto.getCode() == vo.getCode() : "error in rank code";
		
		assert dto.getDescription() == vo.getDescription() : "error in rank description";
		
		assert dto.getIsAvailable() == vo.getIsAvailable() : "error in rank isAvalible";
		
		assert dto.getMultiUser() == vo.getMultiUser() : "error in rank multiUser";
		
		return true;
		
	}
	
	private boolean testRanktoVO(RankVO vo, RankDTO dto) {
		
		assert vo.getName() == dto.getName() : "error in rank name";
		
		assert vo.getCode() == dto.getCode() : "error in rank code";
		
		assert vo.getDescription() == dto.getDescription() : "error in rank description";
		
		assert vo.getIsAvailable() == dto.getIsAvailable() : "error in rank isAvalible";
		
		assert vo.getMultiUser() == dto.getMultiUser() : "error in rank multiUser";
		
		return true;
		
	}
	
	private boolean testCheckListtoVO(CheckListVO vo, CheckListDTO dto) {
		
		assert vo.getId() == dto.getId() : "error in checklist id";
		
		assert vo.getCheckId() == dto.getCheckId() : "error in checklist uuid";
		
		assert vo.getType() == dto.getType() : "error in checklist type";
		
		assert vo.getName() == dto.getName() : "error in checklist name";
		
		assert vo.getShortDesc() == dto.getShortDesc() : "error in checklist shortDesc";
		
		assert vo.getLongDesc() == dto.getLongDesc() : "error in checklist longDesc";
		
		assert vo.getRemarks() == dto.getRemarks() : "error in checklist remarks";
		
		assert vo.getState() == dto.getState() : "error in checklist state";
		
		assert vo.getActive() == dto.getActive() : "error in checklist active";
		
		assert vo.getPreProcessor() == dto.getPreProcessor() : "error in checklist preProcessor";
		
		assert vo.getPostProcessor() == dto.getPostProcessor() : "error in checklist postProcessor";
		
		assert vo.getAuditTrail() == dto.getAuditTrail() : "error in checklist auditTrial";
		
		return true;
		
	}
	
	private boolean testCheckListSectiontoVO(CheckListSectionVO vo, SectionDTO dto) {
		
		assert vo.getId() == dto.getId() : "error in checklistSection id";
		
		assert vo.getSectionName() == dto.getName() : "error in checklistSection name";
		
		assert vo.getDesc() == dto.getShortDesc() : "error in checklistSection desc";
		
		assert vo.getLongDescription() == dto.getLongDescription() : "error in checklistSection longDescription";
		
		assert vo.getPreProcessor() == dto.getPreProcessor() : "error in checklistSection preProcessor";
		
		assert vo.getPostProcessor() == dto.getPostProcessor() : "error in checklistSection postProcessor";
		
		assert vo.getSlno() == dto.getSlNo() : "error in checklistSection SLno.";
		
		assert vo.getActive() == dto.getActive() : "error in checklistSection active";
		
		assert vo.getState() == dto.getState() : "error in checklistSection state";
		
		return true;
		
	}
	
	private boolean testPopulateContext(ContextDTO cdto, UserDTO udto) {
		
		assert cdto.getCompUsers() == getCompanyUserDTO() : "error in testPopulateContext CompanyUser";
		
		assert cdto.getPrivs() == getPrivillegeDTO() : "error in testPopulateContext Privillege";
		
		return true;
	}
}
