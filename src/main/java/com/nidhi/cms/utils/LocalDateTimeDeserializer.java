package com.nidhi.cms.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class LocalDateTimeDeserializer extends JsonDeserializer<LocalDateTime> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.fasterxml.jackson.databind.JsonDeserializer#deserialize(com.fasterxml.
	 * jackson.core.JsonParser,
	 * com.fasterxml.jackson.databind.DeserializationContext)
	 */
	@Override
	public LocalDateTime deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
			throws IOException, JsonProcessingException {
		return LocalDateTime.parse(jsonParser.getText(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

}
