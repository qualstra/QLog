package com.enoch.model;

import org.json.JSONObject;

import com.enoch.ApplicationContext;

import lombok.val;
public class Helper{

	public static AuditTrail getAuditTrail() {
		val userId = ApplicationContext.getContext().getLoggedInUser();
		return new AuditTrail(userId);
	}

	public static String getSafeKey(String data, String string) {
		try {
			return (new JSONObject(data)).optString(string);
		} catch (Exception e) {
			return "";
		}
	}
	
}
