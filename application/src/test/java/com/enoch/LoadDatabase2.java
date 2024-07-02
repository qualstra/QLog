package com.enoch;

import java.util.Date;
import java.util.UUID;

import org.json.JSONObject;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.enoch.builder.QuestionBuilder;
import com.enoch.dao.RoleDAO;
import com.enoch.dao.ShipDAO;
import com.enoch.dao.UserDAO;
import com.enoch.dao.impl.QuestionDAOImpl;
import com.enoch.dto.RankDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.dto.UserDTO;
import com.enoch.dto.checklist.QuestionDTO;
import com.enoch.model.Company;
import com.enoch.model.Helper;
import com.enoch.model.checklist.QuestionType;
import com.enoch.repository.CompanyRepository;
import com.enoch.role.dto.PrivillegeDTO;
import com.enoch.role.dto.RoleDTO;
import com.enoch.service.CompanyService;
import com.enoch.service.RankService;
import com.enoch.service.ShipService;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
@Profile("test")
public class LoadDatabase2 {
	CommandLineRunner initDatabase() {
		return args -> {
			ApplicationContext.initContext("VJ1");
		};
	}

	@Bean
	CommandLineRunner initCompany(CompanyService repository, UserDAO dao, RankService rankService,
			ShipService sServ) {
		return args -> {
			Company comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
					"it@enoch.in","", Helper.getAuditTrail());
			Company comp2 = new Company(null, "GreatShip", UUID.randomUUID(), "Prachi", "ADD1", "ADD2", "CITY", true,
					"it@enoch2.in","", Helper.getAuditTrail());
			
			comp = repository.create(comp.transform()).transform();
			comp2 = repository.create(comp2.transform()).transform();
			log.info("Preloading " + comp);
			log.info("Preloading " + comp2);
			log.info("Preloading " + rankService.addRanks(comp.transform(),
					new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, Helper.getAuditTrail())));
			log.info("Preloading " + rankService.addRanks(comp2.transform(),
					new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, Helper.getAuditTrail())));
			ShipDTO ship = new ShipDTO(1L, UUID.randomUUID(), "Test", "Test", "Test", "Test", "Test", "Test", "Test",
					comp.transform(), "Test", "Test@Test.com", new Date(), Helper.getAuditTrail()

			);
			sServ.create(ship);
		};
	}

	private UserDTO user;

	private Company comp;

	@Bean
	CommandLineRunner initUser(UserDAO repository, ShipDAO vesselrepo) {
		return args -> {

			log.info("Preloading ");
			comp = new Company(1L, "Enoch Company", UUID.randomUUID(), "Enoch", "ADD1", "ADD2", "CITY", true,
					"it@enoch.com","", Helper.getAuditTrail());
			user = repository.create(new UserDTO(null, UUID.randomUUID(), "VJ1", "VJ1", "FN", "LN",
					new RankDTO("CAP", "CAP", "DESCRIPTION", true, false, null), new Date(), "CDC", "PASS", "NAME",
					null, null, true, Helper.getAuditTrail()));

		};
	}

	@Bean
	CommandLineRunner initPriv(RoleDAO repository) {
		return args -> {

			log.info("Preloading ");
			RoleDTO role = repository.createRole(new RoleDTO(null, "1", "ADMIN", "ADMIN", "ADMIN Role", true, false));
			PrivillegeDTO privllege;
			privllege = repository.createPrivillege(new PrivillegeDTO(null, "que", "CREATE", true, false));
			repository.addPrivToRole(role, privllege);

			privllege = repository.createPrivillege(new PrivillegeDTO(null, "que", "DELETE", true, false));
			repository.addPrivToRole(role, privllege);

			privllege = repository.createPrivillege(new PrivillegeDTO(null, "que", "VIEW", true, false));
			repository.addPrivToRole(role, privllege);

			privllege = repository.createPrivillege(new PrivillegeDTO(null, "que", "EDIT", true, false));
			repository.addPrivToRole(role, privllege);

			repository.addRoleToUser("VJ1", role);

		};
	}

	@Bean
	CommandLineRunner initQuestion(QuestionDAOImpl repository) {
		return args -> {
			QuestionDTO question = new QuestionBuilder("THis is a sample question").setType(QuestionType.String)
					.setAttrib("Option", new JSONObject("{\"min-len\":\"100\"}")).build();
			question.setCompany(comp.transform());
			question.setUuid(UUID.randomUUID());
			question = repository.save(question);
			log.info("Preloading " + repository.findById(question.getId()));

		};
	}
}