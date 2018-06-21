package com.family.tech.utils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

/**
 * Factory to generate ObjectMapper that registered JavaTimeModule and some
 * other rule
 *
 */
public class ObjectMapperFactory {
	private static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();
		// MAPPER.registerModule(new JavaTimeModule());
		MAPPER.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		MAPPER.setSerializationInclusion(Include.NON_NULL);
		MAPPER.setSerializationInclusion(Include.NON_EMPTY);
		// MAPPER.enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
		MAPPER.enable(JsonParser.Feature.STRICT_DUPLICATE_DETECTION);
	}

	private ObjectMapperFactory() {

	}

	/**
	 * Get the created ObjectMapper
	 * 
	 * @return ObjectMapper that registered JavaTimeModule and not write date as
	 *         timestamps
	 */
	public static ObjectMapper getMapper() {
		return MAPPER;
	}
}
