package com.nidhi.cms.security;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.modal.response.ErrorResponse;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -90731367144543199L;

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
		 final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_FAILURE, "username or password incorrect or UNAUTHORIZED Access");
	        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
	        response.getWriter().append(new ObjectMapper().writeValueAsString(errorResponse));
	        response.setStatus(HttpStatus.UNAUTHORIZED.value());
	}
}