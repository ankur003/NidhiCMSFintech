package com.nidhi.cms.constants.enums;

/**
 * 
 *
 * @author Ankur Bansala
 */


public enum ErrorCode {

	/** The none. */
	NONE(1000),

	/** The generic server error. */
	GENERIC_SERVER_ERROR(1001),

	/** The content not found. */
	CONTENT_NOT_FOUND(1002),

	/** The malformed auth request. */
	MALFORMED_AUTH_REQUEST(1003),

	/** The authentication failure. */
	AUTHENTICATION_FAILURE(1004),

	/** The authentication required. */
	AUTHENTICATION_REQUIRED(1005),

	/** The authorization failure. */
	AUTHORIZATION_FAILURE(1008),

	/** The generic authentication error. */
	GENERIC_AUTHENTICATION_ERROR(1009),

	/** The parameter missing invalid. */
	PARAMETER_MISSING_INVALID(1010),

	/** The un-processable entity. */
	UNPROCESSABLE_ENTITY(1011),

	ENTITY_ALREADY_EXISTS(1014),

	/** The malformed request format. */
	MALFORMED_REQUEST_FORMAT(1017),

	/** The unsupported media type. */
	UNSUPPORTED_MEDIA_TYPE(1018),

	/** The user not found. */
	USER_NOT_FOUND(1060),

	/** The invalid resource url. */
	INVALID_RESOURCE_URL(1057),

	/** The method not allowed. */
	METHOD_NOT_ALLOWED(1058),

	PASSWORD_DO_NOT_MATCH(1066),

	/** The entity not found. */
	ENTITY_NOT_FOUND(1068),

	/** The invalid session. */
	INVALID_SESSION(1071),

	/** The invalid payment request. */
	INVALID_PAYMENT_REQUEST(1075),

	MANDATORY_VALUES_NOT_FILLED(1024),

	RECORD_MERGED(1100),

	UNAVAILABLE_FOR_LEGAL_REASONS(1100),

	PAYMENT_RULES_CHECKING_FAILED(1101),

	RESOURCE_ALREADY_PROCESSED(1012),

	DOCUMENT_SCAN_FAILED(1013);

	/** The value. */
	private final int value;

	/**
	 * Instantiates a new error code.
	 *
	 * @param value the value
	 */
	ErrorCode(final int value) {
		this.value = value;
	}

	/**
	 * Value.
	 *
	 * @return the int
	 */
	public int value() {
		return value;
	}

	/**
	 * Gets the error code.
	 *
	 * @param code the code
	 * @return the error code
	 */
	public static ErrorCode getErrorCode(final int code) {
		for (final ErrorCode errorCode : ErrorCode.values()) {
			if (errorCode.value == code) {
				return errorCode;
			}
		}
		return ErrorCode.GENERIC_SERVER_ERROR;
	}
}
