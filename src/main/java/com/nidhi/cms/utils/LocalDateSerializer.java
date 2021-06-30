package com.nidhi.cms.utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * 
 *
 * @author Ankur Bansala
 */
public class LocalDateSerializer extends JsonSerializer<LocalDate> {

	/*
	 * (non-Javadoc)
	 *
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object,
	 * org.codehaus.jackson.JsonGenerator,
	 * org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(final LocalDate value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException, JsonProcessingException {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		final String formattedDateTime = value.format(formatter);
		if (StringUtils.isNotBlank(formattedDateTime)) {
			jgen.writeString(formattedDateTime);
		}
	}

}
