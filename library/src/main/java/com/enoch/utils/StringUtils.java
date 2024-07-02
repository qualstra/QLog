package com.enoch.utils;

public class StringUtils {

	public static boolean eqI(String key, String key2) {
		return _eq(key, key2, true);
	}

	public static boolean eq(String key, String key2) {
		return _eq(key, key2, false);
	}

	private static boolean _eq(String key, String key2, Boolean ignore) {
		if (key == null || key2 == null) {
			return false;
		}

		return ignore ? key.equals(key2) : key.equalsIgnoreCase(key2);
	}

	public static boolean isBlank(String str) {
		return (str == null) || str.trim().isEmpty();
	}
	public static boolean isNotBlank(String str) {
		return !isBlank(str);
	}
	
	public static boolean getBoolVal(String value, boolean def) {
		if (value == null)
			return def;
		value = value.trim();
		if (value.equalsIgnoreCase("Y") || value.equalsIgnoreCase("YES") || value.equalsIgnoreCase("T")
				|| value.equalsIgnoreCase("TRUE"))
			return true;
		return Boolean.valueOf(value);
	}

	public static Integer getIntVal(String string, int i) {
		try {
			return Integer.parseInt(string);
		}catch (Exception e) {
			return i;	
		}
		
	}

	public static Double getDoubleVal(String string, double d) {
		try {
			return Double.parseDouble(string);
		}catch (Exception e) {
			return d;	
		}
	}
	
}
