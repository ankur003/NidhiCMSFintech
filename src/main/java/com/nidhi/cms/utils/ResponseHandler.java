package com.nidhi.cms.utils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class ResponseHandler {

	private ResponseHandler() {
		//
	}

	public static ResponseEntity<Object> getMapResponse(final String key, final Object value) {
		if (value == null || StringUtils.isBlank(String.valueOf(value))) {
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).build();
		}
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put(key, value);
		return ResponseEntity.status(HttpStatus.CREATED).body(responseMap);
	}

	public static ResponseEntity<Object> getContentResponse(Object value) {
		if (value == null || StringUtils.isBlank(String.valueOf(value))) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(value);
	}

}
