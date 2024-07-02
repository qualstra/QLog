package com.enoch.model;

import java.util.Date;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.utils.DateUtils;

@DataJpaTest
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
public class DateUtilsTest {
	
	private DateUtils utils;
	
	private Date date;
	
	@Test
	public void testisBlank() throws DataException {	
		utils.isBlank(date);
		assert testisBlank(date) : "error in date testisBlank";
	}
	
	@Test
	public void testisNotBlank() throws DataException {	
		utils.isNotBlank(date);
		assert testisNotBlank(date) : "error in date testisNotBlank";
	}
	
	private boolean testisBlank(Date date) {	
		assert date == null :"error in date";
		return true;
	}
	
	private boolean testisNotBlank(Date date) {
		assert testisBlank(date) : "error in date";
		return true;
	}

}
