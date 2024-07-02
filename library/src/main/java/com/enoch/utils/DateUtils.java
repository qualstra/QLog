package com.enoch.utils;

import java.util.Date;

public class DateUtils {
	
	public static boolean isBlank(Date date) {
		return (date == null);
	}
	
	public static boolean isNotBlank(Date date) {
		return !isBlank(date);
	}

}
