package com.sdase.docvalidator.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.LinkedHashMap;

/**
 * @author akif
 * @since 3/10/22
 */
public class JSONObject extends LinkedHashMap<String, Object> {

	private static final ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
	}

	public static <T> T getObjectFromJson(String json, Class<T> tClass) throws JsonProcessingException {
		return objectMapper.readValue(json, tClass);
	}
}
