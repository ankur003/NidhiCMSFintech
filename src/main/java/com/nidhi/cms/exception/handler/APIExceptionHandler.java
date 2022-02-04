
package com.nidhi.cms.exception.handler;


import java.time.format.DateTimeParseException;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.modal.response.ErrorResponse;

@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class APIExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(APIExceptionHandler.class);

    private static final String SERVER_ERROR = "An error occurred on the server. Please contact support.";

    private static final String INVALID_JSON_FORMAT_MESSAGE = "Request body is not a valid Json format.";

	private static final String REQUIRED_PARAM_ERROR_MESSAGE = "invalid params";

	private static final String GLOBAL_PARAM_ERROR_SUFFIX = "invalid";

    @ExceptionHandler({
        RuntimeException.class,
        IllegalStateException.class,
        IllegalArgumentException.class,
        AuthenticationCredentialsNotFoundException.class,
    })
    public ResponseEntity<Object> exceptionHandler(final Exception exception, final WebRequest request) {
        logErrorMessage(exception);
        if (exception.getClass().isAssignableFrom(AccessDeniedException.class)) {
            return mapToAccessDeniedException();
        }
        if (exception.getClass().isAssignableFrom(AuthenticationCredentialsNotFoundException.class)) {
            return mapToAuthenticationCredentialsNotFoundException();
        }
        if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)) {
            return mapToUsernameNotFoundExceptionException();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR, SERVER_ERROR));
    }
    
	@ExceptionHandler(value = UsernameNotFoundException.class)
	public ResponseEntity<Object> handle(final UsernameNotFoundException exception) {
		return mapToUsernameNotFoundExceptionException();
	}

    private ResponseEntity<Object> mapToUsernameNotFoundExceptionException() {
    	   final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHORIZATION_FAILURE, "username or password invalid or not verfied user.");
           String errorMessage = "";
           try {
               errorMessage = new ObjectMapper().writeValueAsString(errorResponse);
           } catch (final JsonProcessingException e) {
               log.error("mapToUsernameNotFoundExceptionException - username or password invalid or not verfied user.", e.getCause());
           }
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
	}

	@ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(final Exception exception, final WebRequest request) {
        logErrorMessage(exception);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR.value()).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR, SERVER_ERROR));
    }

    private ResponseEntity<Object> mapToAccessDeniedException() {
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHORIZATION_FAILURE, "Request does not have required permission to access the requested API");
        String errorMessage = "Access is denied";
        try {
            errorMessage = new ObjectMapper().writeValueAsString(errorResponse);
        } catch (final JsonProcessingException e) {
            log.error("[PropCo API] Access Denied - Request does not have required permission to access the requested API", e.getCause());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }
    
    private ResponseEntity<Object> mapToAuthenticationCredentialsNotFoundException() {
        final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHORIZATION_FAILURE, "Authorization header is invalid or missing.");
        String errorMessage = "Access is denied";
        try {
            errorMessage = new ObjectMapper().writeValueAsString(errorResponse);
        } catch (final JsonProcessingException e) {
            log.error("[PropCo API] Access Denied - Request does not have required permission to access the requested API", e.getCause());
        }
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(errorMessage);
    }

    @Override
    protected ResponseEntity<Object> handleBindException(final BindException bindException, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logErrorMessage("Validation failed for one or more method parameters", bindException);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage("invalid param found");
        for (final ObjectError objectError : bindException.getAllErrors()) {
            final String[] errorInfo = StringUtils.split(objectError.getDefaultMessage(), ":", 2);
            errorResponse.addError(errorInfo[0].trim(), errorInfo[1].trim());
        }
        errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<Object> handleConstraintViolation(final ConstraintViolationException exception) {
        logErrorMessage(exception);
        final Set<ConstraintViolation<?>> constraintViolations = exception.getConstraintViolations();
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
        for (final ConstraintViolation<?> constraintViolation : constraintViolations) {
            final String[] errorInfo = StringUtils.split(constraintViolation.getMessage(), ":", 2);
            errorResponse.addError(errorInfo[0].trim(), errorInfo[1].trim());
        }
        errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    ResponseEntity<Object> handleMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException exception) {
        logErrorMessage(exception);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
        errorResponse.addError(exception.getName(), exception.getName() + ": invalid or missing");
        errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(
        final MissingServletRequestParameterException ex, final HttpHeaders headers, final HttpStatus status, final WebRequest request) {
        logErrorMessage("Validation failed for one or more method parameters", ex);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
        errorResponse.addError(ex.getParameterName(), ex.getMessage());
        errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request) {
        logErrorMessage(ex);
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
        for (final ObjectError objectError : ex.getBindingResult().getAllErrors()) {
            final String[] errorInfo = StringUtils.split(objectError.getDefaultMessage(), ":", 2);
            errorResponse.addError(errorInfo[0].trim(), errorInfo[1].trim());
        }
        errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(final HttpMessageNotReadableException exception, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request) {
        logErrorMessage(exception);
        if (exception.getMostSpecificCause().getClass().isAssignableFrom(DateTimeParseException.class)
            || exception.getMostSpecificCause().getClass().isAssignableFrom(InvalidFormatException.class)) {
            final String errorFieldName = StringUtils.remove(StringUtils.removeEnd(StringUtils.split(exception.getCause().getMessage(), "[", 2)[1], "])"), "\"");
            final ErrorResponse errorResponse = new ErrorResponse();
            errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
            errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
            errorResponse.addError(errorFieldName, errorFieldName + GLOBAL_PARAM_ERROR_SUFFIX);
            errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
            return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);

        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST.value()).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(ErrorCode.MALFORMED_REQUEST_FORMAT, INVALID_JSON_FORMAT_MESSAGE));
    }

    private ResponseEntity<Object> mapToApiBaseErrorResponse(final String errorMessage, final HttpStatus status) {
        final ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setErrorDesc(ErrorCode.PARAMETER_MISSING_OR_INVALID);
        errorResponse.setMessage(REQUIRED_PARAM_ERROR_MESSAGE);
        final String[] errorInfo = StringUtils.split(errorMessage, ":", 2);
        errorResponse.addError("errors", errorInfo[0].trim());
        errorResponse.addError("errorCode", ""+ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
        return ResponseEntity.status(status).contentType(MediaType.APPLICATION_JSON).body(errorResponse);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(final HttpMediaTypeNotSupportedException exception, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request) {
        logErrorMessage(exception);
        return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value()).contentType(MediaType.APPLICATION_JSON)
            .body(new ErrorResponse(ErrorCode.UNSUPPORTED_MEDIA_TYPE, "Request type is not supported."));
    }

    @Override
    protected ResponseEntity<Object> handleNoHandlerFoundException(final NoHandlerFoundException exception, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request) {
        logErrorMessage(exception);
        return mapToApiBaseErrorResponse(exception.getMessage(), status);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestPart(final MissingServletRequestPartException exception, final HttpHeaders headers, final HttpStatus status,
        final WebRequest request) {
        logErrorMessage(exception);
        return mapToApiBaseErrorResponse(exception.getMessage(), status);
    }

    private static void logErrorMessage(final Exception exception) {
        logErrorMessage("An exception occurred", exception);
    }

    private static void logErrorMessage(final String errorMessage, final Exception exception) {
        log.error("[PropCo API] {}", errorMessage, exception);
    }

}
