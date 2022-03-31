package com.nidhi.cms.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.modal.response.ErrorResponse;

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
		if (value instanceof Collection<?> && CollectionUtils.isEmpty((List<?>) value)) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok(value);
	}
	
	public static Map<String, Object> getpaginationResponse(Mapper beanMapper, Page<?> pageData, Class<?> clazz) {
		List<?> data = DozerMapperUtil.mapCollection(beanMapper, pageData.getContent(), clazz);
		final Map<String, Object> responseMap = new HashMap<>();
		responseMap.put("data", data);
		responseMap.put("count", pageData.getTotalElements());
		responseMap.put("page", pageData.getTotalPages());
		//return ResponseEntity.ok(responseMap);
		return responseMap;
	}
	
	public static <S> List<Object> getListResponse(Mapper beanMapper, final Collection<S> srcCollection, Class<?> clazz) {
		return DozerMapperUtil.mapCollection(beanMapper, srcCollection, clazz);
	}

	public static ResponseEntity<Object> getResponseEntity(ErrorCode errorCode, String message, HttpStatus httpStatus) {
		final ErrorResponse errorResponse = new ErrorResponse(errorCode, message);
        return ResponseEntity.status(httpStatus).body(errorResponse);
	}
	
	public static ResponseEntity<Object> getOkResponse() {
		return ResponseEntity.ok().build();
	}

	public static ResponseEntity<Object> get204Response() {
		return ResponseEntity.noContent().build();
	}
	
	public static ResponseEntity<Object> get201Response() {
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

}
