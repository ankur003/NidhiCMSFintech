package com.nidhi.cms.utils;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object,
	 * org.codehaus.jackson.JsonGenerator,
	 * org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(final LocalDateTime value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		final String formattedDateTime = value.format(formatter);
		jgen.writeString(formattedDateTime);
	}

}
