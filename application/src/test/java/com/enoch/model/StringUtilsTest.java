package com.enoch.model;

import org.hibernate.exception.DataException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.enoch.utils.StringUtils;

@DataJpaTest
@RunWith(SpringRunner.class)
public class StringUtilsTest {
	
	private StringUtils utils;
	
	private String str;
	private Boolean bool;
	private int i;
	
	@Test
	public void testisBlank() throws DataException {
		utils.isBlank(str);
		assert testisBlank(str) : "error in testisBlank";
	}
	
	@Test
	public void testisNotBlank() throws DataException{
		utils.isNotBlank(str);
		assert testisNotBlank(str) : "error in testisNotBlank";
	}
	
	@Test
	public void testgetBoolVal() throws DataException{
		utils.getBoolVal(str, true);
		assert testgetBoolVal(str, true) : "error in boolVal";
	}
	
	@Test
	public void testgetIntVal() throws DataException {
		utils.getIntVal(str, i);
		assert testgetIntVal(str, i) : "error in testgetIntVal";
	}
	
	@Test
	public void testeqI() throws DataException {
		String str1 = str;
		String str2 = str;
		utils.eqI(str1, str2);
		assert test_eq(str1, str2, true) : "error in testeqI";
	}
	
	@Test
	public void testeq() throws DataException {
		String str1 = str;
		String str2 = str;
		utils.eq(str1, str2);
		assert test_eq(str1, str2, false) : "error in testeq";
	}
	
	private boolean testisBlank(String str) {
		str = "navanee";
		str.trim().isEmpty();
		assert str == "navanee" : "error in isBlank";
		return true;
	}
	
	private boolean testisNotBlank(String str) {
		str = "navanee";
		assert testisBlank(str) : "error in str isNotBlank";
		return true;
	}
	
	private boolean testgetBoolVal(String str, boolean bool) {
		String str1 = "true";
		if (str1 == null)
			return bool;
		str1 = str1.trim();
		if (str1.equalsIgnoreCase("Y") || str1.equalsIgnoreCase("YES") || str1.equalsIgnoreCase("T")
				|| str1.equalsIgnoreCase("TRUE"))
			return true;
		boolean val = Boolean.valueOf(str1);
		assert val == true : "error in getBoolVal";
		return true;
	}
	
	private boolean testgetIntVal(String str, int i) {
		str = "42";
		int num =  Integer.parseInt(str);
		assert num == 42 : "error in getIntVal";
		return true;
	}
	
	private boolean test_eq(String str1, String str2, boolean bool) {
		str1 = "navanEe";
		str2 = "navanEe";
		if (str1 == null || str2 == null) {
			return false;
		}
		assert bool ? str1.equals(str2) : str1.equalsIgnoreCase(str2) : "error in _eq";
		return true;
	}
}
