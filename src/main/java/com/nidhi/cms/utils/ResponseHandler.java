package com.nidhi.cms.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

	public static ResponseEntity<Object> getDocumentResponse(File document) {
		if (document == null) {
            return get204Response();
        }
        CleanupInputStreamResource resource = null;
        try {
            resource = new CleanupInputStreamResource(document);
        } catch (final IOException e) {
            return getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        final String mediaType = probeContentType(document.getAbsolutePath());
        return ResponseEntity.ok().header("Content-Disposition", "attachment; filename=\"" + document.getName() + "\"")
            .contentLength(document.length()).contentType(MediaType.parseMediaType(mediaType)).body(resource);
	}
	
	 public static String probeContentType(final String filePath) {
	        String mediaType = null;
	        try {
	            mediaType = Files.probeContentType(Paths.get(filePath));
	        } catch (final IOException ioe) {
	        }
	        if (StringUtils.isBlank(mediaType)) {
	            mediaType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	        }
	        return mediaType;
	    }

	public static ResponseEntity<Object> getDocumentResponse(byte[] imageAsByte, String fileName) {
		try {
			Path tempFile = Files.createTempFile(FilenameUtils.removeExtension(fileName), "." + FilenameUtils.getExtension(fileName));
			File file = Files.write(tempFile, imageAsByte).toFile();
	        return getDocumentResponse(file);
        } catch (IOException e) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
        }
	}

}
