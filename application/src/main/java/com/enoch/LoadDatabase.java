package com.enoch;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import com.enoch.builder.QuestionBuilder;
import com.enoch.controller.MasterCheckListController;
import com.enoch.dao.RoleDAO;
import com.enoch.dao.impl.QuestionDAOImpl;
import com.enoch.dto.CompanyUserDTO;
import com.enoch.dto.RankDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.checklist.CheckListDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.dto.checklist.inst.CheckListInstCreateDTO;
import com.enoch.dto.checklist.inst.CheckListInstDTO;
import com.enoch.master.dao.MasterDAO;
import com.enoch.master.dto.CountryDTO;
import com.enoch.master.dto.PortDTO;
import com.enoch.model.AuditTrail;
import com.enoch.model.Company;
import com.enoch.model.CompanyUser;
import com.enoch.model.Helper;
import com.enoch.model.checklist.QuestionType;
import com.enoch.model.checklist.inst.Association;
import com.enoch.repository.CompanyRepository;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.CheckListInstService;
import com.enoch.service.CheckListService;
import com.enoch.service.RankService;
import com.enoch.service.ShipService;
import com.enoch.service.UserService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Profile("LOADDB")
public class LoadDatabase {

	private UserDTO user;
	@Autowired
	private CheckListInstService checklistInstServ;

	@Autowired
	private CheckListService checklistServ;

	@Bean
	CommandLineRunner initDatabase(CompanyRepository repository, UserService userDAO, MasterDAO mastDAO,
			RankService rankService, ShipService service, RoleDAO rolerepo, QuestionDAOImpl queRepo) {
		return args -> {
			ApplicationContext.initContext("VJ1");

			log.info("Preloading " + repository.save(new Company(null, "Enoch Company", UUID.randomUUID(), "Enoch",
					"ADD1", "ADD2", "IN", true, "it@enoch.com","", Helper.getAuditTrail())));
			log.info("Preloading " + repository.save(new Company(null, "GreatShip", UUID.randomUUID(), "Prachi", "ADD1",
					"ADD2", "IN", true, "it@greatship.com","", Helper.getAuditTrail())));

			log.info("Preloading ");
			Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "IN", true,
					"it@greatship.com","", Helper.getAuditTrail());
			user = new UserDTO(null, null, "VJ1@test.com",
					"VJ1", "Vijay", "Jeyam",
					// "$2a$10$hb7ayBnde3rKqBf.j/jHrOFwxMF.hqh4DrXQXG4uoXtAYHWoR80By"
					new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS4", "NAME",
					null, null, true, Helper.getAuditTrail());

			ApplicationContext.getContext().setUser(user);
			ApplicationContext.getContext().setCompany(comp.transform());

			rankService.addRanks(comp.transform(),
					new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, Helper.getAuditTrail()));
			rankService.addRanks(comp.transform(),
					new RankDTO("ITADMIN", "ITADMIN", "DESCRIPTION", true, false, Helper.getAuditTrail()));
			CompanyUserDTO compUser = userDAO.create(user, comp.transform());
			user = compUser.getUser();
			Optional<RoleDTO> adminROle = rolerepo.loadRole(comp.getId()+"","RANK_ITADMIN");
			log.info("Preloading ");
			RoleDTO role = rolerepo.createRole(new RoleDTO(null, "1", "ADMIN", "ADMIN", "ADMIN", true, false));
			PrivillegeDTO privllege;
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "que", "CREATE", true, false));
			rolerepo.addPrivToRole(role, privllege);

			final PrivillegeDTO shipCreatePriv = rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "CREATE", true, false));
			rolerepo.addPrivToRole(role, shipCreatePriv);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "EDIT", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "SEARCH", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voyage", "CREATE", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voyage", "EDIT", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voyage", "SEARCH", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "comp", "CREATE", true, false));
			rolerepo.addPrivToRole(role, privllege);

			final PrivillegeDTO compViewPrivllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "comp", "VIEW", true, false));
			rolerepo.addPrivToRole(role, compViewPrivllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "comp", "EDIT", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "comp", "SEARCH", true, false));
			rolerepo.addPrivToRole(role, privllege);

			PrivillegeDTO usrCrtPriv = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "CREATE", true, false));
			rolerepo.addPrivToRole(role, usrCrtPriv);	
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "SEARCH", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "que", "DELETE", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "que", "VIEW", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "que", "EDIT", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "comp", "ALL_COMP", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "admin", "ALL_COMP", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "admin", "COMP", true, false));
			rolerepo.addPrivToRole(role, privllege);

			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "admin", "SHIP", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "nc", "SEARCH", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "fleet", "CREATE", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "doc", "CREATE", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "assign", "ALL_COMP", true, false));
			rolerepo.addPrivToRole(role, privllege);
			
			privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "de-assign", "ALL_COMP", true, false));
			rolerepo.addPrivToRole(role, privllege);

			rolerepo.addRoleToUser(user.getId().toString(), role);

			QuestionDTO question = new QuestionBuilder("THis is a sample question").setType(QuestionType.String)

					.setAttrib("Option", new JSONObject("{\"min-len\":\"100\"}")).build();

			question.setCompany(comp.transform());

			CountryDTO countryDTO = new CountryDTO("IN", "INDIA", "India", "10", "RS", "ENG", new BigDecimal(10.2),
					new AuditTrail("AUTO"));
			countryDTO = mastDAO.saveCountry(countryDTO);

			PortDTO dto = new PortDTO();
			dto.setCountry(countryDTO);
			dto.setCity("CHENNAI");
			dto.setCode("maa");
			dto.setDescription("Chennai Port");

			dto = mastDAO.savePort(dto);

			PortDTO dto2 = new PortDTO();
			dto2.setCountry(countryDTO);
			dto2.setCity("MUMBAI");
			dto2.setCode("mum");
			dto2.setDescription("Mumbai Port");

			dto2 = mastDAO.savePort(dto2);

			PortDTO dto3 = new PortDTO();
			dto3.setCountry(countryDTO);
			dto3.setCity("GOA");
			dto3.setCode("goa");
			dto3.setDescription("Goa Port");

			dto3 = mastDAO.savePort(dto3);

			PortDTO dto4 = new PortDTO();
			dto4.setCountry(countryDTO);
			dto4.setCity("KERALA");
			dto4.setCode("ker");
			dto4.setDescription("Kerala Port");

			dto4 = mastDAO.savePort(dto4);
			ShipDTO shipDTO = new ShipDTO(null, null, "GreatShip", "IMP", "SOME", "SOME", "NS", "wq", "wqwq",
					comp.transform(), "Active", "vj@v.co", new Date(), Helper.getAuditTrail());

			shipDTO = service.create(shipDTO);

			compUser.setShip(shipDTO);

			userDAO.associateUser(compUser);
			question.setUuid(UUID.randomUUID());
			log.info("Preloading " + queRepo.save(question));

privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "mchk", "VIEW", true, false));

privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "mchk", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);

privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "mchk", "COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "mchk", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "ALL_COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voy", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voy", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voy", "COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "voy", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "CREATE", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "SEARCH", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "chk", "EDIT", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "rep", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "rep", "COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "rep", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "doc", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "doc", "COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "doc", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "port", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "port", "ADMIN", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "EDIT", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "role", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "role", "ADMIN", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "role", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "fleet", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "fleet", "ADMIN", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "fleet", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "nc", "VIEW", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "nc", "COMP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "nc", "SHIP", true, false));
privllege = rolerepo.createPrivillege(new PrivillegeDTO(null, "nc", "ALL_COMP", true, false));
rolerepo.addPrivToRole(role, privllege);

adminROle.ifPresent(itm -> {
	rolerepo.addPrivToRole(itm, compViewPrivllege);
	rolerepo.addPrivToRole(itm, rolerepo.createPrivillege(new PrivillegeDTO(null, "ship", "VIEW", true, false)));
	rolerepo.addPrivToRole(itm, rolerepo.createPrivillege(new PrivillegeDTO(null, "user", "COMP", true, false)));
	rolerepo.addPrivToRole(itm, usrCrtPriv);

	rolerepo.addPrivToRole(itm, shipCreatePriv);
});



		};
	}

	private CheckListInstDTO cloneChecklist(ShipDTO shipDTO) throws Exception {
		List<CheckListDTO> checklists = checklistServ.getAllCheckLists();
		CheckListDTO chDto = checklists.get(0);

		CheckListInstCreateDTO checklistDTO = new CheckListInstCreateDTO();
		checklistDTO.setAssocType(Association.RANK);
		checklistDTO.setMasterChkUUID(chDto.getCheckId());
		checklistDTO.setCompany(shipDTO.getCompany());
		checklistDTO.setStartDate(new Date());
		checklistDTO.setEndDate(new Date());
		checklistDTO.setVessel(shipDTO);
		CheckListInstDTO chkInst = checklistInstServ.clone(checklistDTO);
		return chkInst;
	}

	@Autowired
	CompUserRepo comprepo;
}

interface CompUserRepo extends JpaRepository<CompanyUser, Long> {

}