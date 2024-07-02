package com.enoch.utils;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.enoch.common.exception.ServiceException;

public class JSONUtils {

	public static Float getFloat(JSONObject jObject, String key, Float def) {
		try {
			return jObject.getFloat(key);
		} catch (Exception e) {
			return def;
		}
	}

	public static JSONObject getJSObject(String data) {
		if(data == null) return new JSONObject();
		return new JSONObject(data);
	}

	public static Integer getInteger(JSONObject jObject, String key, Integer deflt) {
		try {
			return new Float(jObject.getFloat(key)).intValue();
		} catch (NumberFormatException e) {
			throw new ServiceException("Error getting integer value for - " + key, e);

		} catch (JSONException e) {
			if (e.getMessage().toLowerCase().contains("is not a float"))
				throw new ServiceException(
						String.format("Error getting integer value for - %s error value %s ", key, jObject.get(key)),
						e);
			return deflt;
		}
	}

	public static Integer getIntVal(JSONObject option, String value, Integer def) {
		try {
			return option.getInt(value);
		} catch (Exception e) {
			return def;
		}
	}

	public static JSONArray getArrayJS(JSONObject data, String key) {
		Object values = data.get(key);
		if (values instanceof String) {
			return new JSONArray((String) values);
		} else if (values instanceof JSONArray) {
			return (JSONArray)values;
		}else {
			JSONArray ret = new JSONArray();
			ret.put(values);
			return ret;
		}
	}

	public static List<String> getStrList(String postProcessor) {
		if(postProcessor == null || postProcessor.trim().isEmpty()) {
			return Collections.emptyList();
		}
		JSONArray array = new JSONArray(postProcessor);
		return CopyUtil.map(array.toList(), a -> a.toString());
	}

	public static String getJSString(Collection<String> valSet) {
		JSONArray array = new JSONArray(valSet);
		return array.toString();
	}

	public static String getStringValue(String data, String string) {
		return getJSObject(data).optString(string,null);
	}
	public static String getStringValue(JSONObject data, String string) {
		return data.optString(string,null);
	}

}