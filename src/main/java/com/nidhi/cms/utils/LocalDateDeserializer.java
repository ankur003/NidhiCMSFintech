package com.nidhi.cms.utils;

import java.io.IOException;
import java.time.LocalDate;
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

public class LocalDateDeserializer extends JsonDeserializer<LocalDate> {

	/*
	 * (non-Javadoc)
	 *
	 * @see
	 * org.codehaus.jackson.map.JsonDeserializer#deserialize(org.codehaus.jackson.
	 * JsonParser, org.codehaus.jackson.map.DeserializationContext)
	 */
	@Override
	public LocalDate deserialize(final JsonParser jsonparser, final DeserializationContext deserializationcontext)
			throws IOException, JsonProcessingException {
		final String dateString = jsonparser.getText();
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		return LocalDate.parse(dateString, formatter);
	}
}
