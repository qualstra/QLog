package com.enoch;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.test.context.ContextConfiguration;

import com.enoch.test.role.PrivillegeTest;
import com.enoch.test.role.RoleTest;



@RunWith(Suite.class)
@SuiteClasses({ PrivillegeTest.class,RoleTest.class })
@ContextConfiguration(classes = RoleApplication.class)
public class AllTests {

}
