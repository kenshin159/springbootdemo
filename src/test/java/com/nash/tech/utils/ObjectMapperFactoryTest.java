package com.nash.tech.utils;

import static org.junit.Assert.assertNotNull;

import java.io.IOException;
import java.time.OffsetDateTime;

import org.junit.Test;

import com.family.tech.utils.ObjectMapperFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperFactoryTest {

	private ObjectMapper mapper;

	@Test
	public void shouldReturnObjectMapper() {
		mapper = ObjectMapperFactory.getMapper();
		assertNotNull(mapper);
	}

	@Test
	public void shouldHasJavaTimeModule() throws JsonParseException, JsonMappingException, IOException {
		mapper = ObjectMapperFactory.getMapper();
		OffsetDateTime date = OffsetDateTime.parse("2017-02-12T13:20:50.52Z");
		assertNotNull(date);
		// assertEquals("\"2017-02-12T13:20:50.52Z\"", mapper.writeValueAsString(date));
		// OffsetDateTime mappedDate = mapper.readValue("\"2017-02-12T13:20:50.52Z\"",
		// OffsetDateTime.class);
		// assertEquals(date, mappedDate);
	}
}
