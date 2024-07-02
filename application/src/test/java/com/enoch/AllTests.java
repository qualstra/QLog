package com.enoch;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;

import com.enoch.dto.CompanyDTO;
import com.enoch.dto.ShipDTO;
import com.enoch.model.FleetTests;

@RunWith(Suite.class)
@SuiteClasses({ 
		//NCTests.class,UserQuerydslTest.class, UserTest.class, ShipTest.class,
		//CheckListInstTests.class, CheckListTests.class, QuestionTests.class, 
		//QuestionRepositoryIntegrationTest.class//, HelperTest.class
	FleetTests.class
})
@ContextConfiguration(classes = EnochApplication.class)
@ActiveProfiles("test")
@SpringBootTest()
public class AllTests {
	@BeforeClass
	public static void init() {
		try {
			ApplicationContext.initContext("VJ1");
			ApplicationContext cpmt = ApplicationContext.getContext().getContext();
			CompanyDTO v = new CompanyDTO();
			v.setId(1L);
			cpmt.setCompany(v);
			ShipDTO ship = new ShipDTO();
			ship.setCompany(v);
			cpmt.setShip(ship);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

}
