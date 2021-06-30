package com.nidhi.cms.utils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.dozer.DozerConverter;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class LocalDateToDateConverter extends DozerConverter<LocalDate, Date> {

	public LocalDateToDateConverter() {
		super(LocalDate.class, Date.class);
	}

	@Override
	public Date convertTo(final LocalDate source, final Date destination) {
		if (source == null) {
			return null;
		}
		return Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
	}

	@Override
	public LocalDate convertFrom(final Date source, final LocalDate destination) {
		if (source == null) {
			return null;
		}
		return Instant.ofEpochMilli(source.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
	}

}