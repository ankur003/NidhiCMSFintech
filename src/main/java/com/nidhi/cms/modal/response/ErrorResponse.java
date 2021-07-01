package com.nidhi.cms.modal.response;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.nidhi.cms.constants.enums.ErrorCode;

/**
 * 
 *
 * @author Ankur Bansala
 */


public class ErrorResponse {

	private ErrorCode errorCode;

	private String message;

	@JsonProperty
	@JsonInclude(value = Include.NON_NULL)
	private Map<String, String> errors;

	/**
	 * Gets the errors.
	 *
	 * @return the errors
	 */
	public Map<String, String> getErrors() {
		return errors;
	}

	/**
	 * Sets the errors.
	 *
	 * @param errors the new errors
	 */
	public void setErrors(final Map<String, String> errors) {
		this.errors = errors;
	}

	/**
	 * Adds the error.
	 *
	 * @param fieldName    the field name
	 * @param errorMessage the error message
	 */
	public void addError(final String fieldName, final String errorMessage) {
		if (errors == null) {
			errors = new HashMap<>();
		}
		errors.put(fieldName, errorMessage);
	}

	/**
	 * Instantiates a new error response.
	 */
	public ErrorResponse() {
	}

	/**
	 * Instantiates a new error response.
	 *
	 * @param errorCode the error code
	 * @param message   the message
	 */
	public ErrorResponse(final ErrorCode errorCode, final String message) {
		super();
		this.errorCode = errorCode;
		this.message = message;
	}

	/**
	 * Gets the error code.
	 *
	 * @return the error code
	 */
	public ErrorCode getErrorCode() {
		return errorCode;
	}

	/**
	 * Sets the error code.
	 *
	 * @param errorCode the new error code
	 */
	public void setErrorCode(final ErrorCode errorCode) {
		this.errorCode = errorCode;
	}

	/**
	 * Gets the message.
	 *
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * Sets the message.
	 *
	 * @param message the new message
	 */
	public void setMessage(final String message) {
		this.message = message;
	}

}
