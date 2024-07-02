package com.enoch;

import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;


import com.enoch.test.AttachmentTest;

@RunWith(Suite.class)
@SuiteClasses({ AttachmentTest.class })
@ContextConfiguration(classes = ComponentApplication.class)
public class AllTests {
	@BeforeClass
	public static void init() {
		try {
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}