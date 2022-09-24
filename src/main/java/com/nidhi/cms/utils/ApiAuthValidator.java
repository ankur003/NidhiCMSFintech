package com.nidhi.cms.utils;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.response.ErrorResponse;

public class ApiAuthValidator {
	
	public static ErrorResponse validateApiRequestHeader(String apiKey, String authorizationToken) {
		if (StringUtils.isAnyBlank(apiKey, authorizationToken)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_OR_INVALID, "apiKey/Authorization is reuired -  please provide apiKey/Authorization in header");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
			return errorResponse;
		}
		return null;
	}
	
	public static ErrorResponse validateUserWallet(UserWallet userWallet, String requestedMerchantId) {
		if (userWallet == null || BooleanUtils.isFalse(userWallet.getIsUpiActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created or upi not active");
			errorResponse.addError("errorCode", "" + ErrorCode.ENTITY_NOT_FOUND.value());
		    return errorResponse;
		}
		
		if (userWallet.getMerchantId() == null || !userWallet.getMerchantId().equals(requestedMerchantId)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_OR_INVALID, "merchantId not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_OR_INVALID.value());
		    return errorResponse;
		}
		return null;
	}
	
	public static ErrorResponse validateUser(final HttpServletRequest httpServletRequest, String authorizationToken, User user) {
		if (user == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "invalid apiKey - please contact admin");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
		    return errorResponse;
		}
		
		if (!user.getToken().equals(authorizationToken)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "authorization Token is invalid");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
			return errorResponse;
		}
		
		if (BooleanUtils.isNotTrue(user.getIsActive()) || !user.getKycStatus().name().equals(KycStatus.VERIFIED.name())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over de-activated account or KYC is - " + user.getKycStatus().name());
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
			return errorResponse;
		}
		boolean isValid = getRemoteIpAddress(user, httpServletRequest);
		if (!isValid) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over ip");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
			return errorResponse;
		}
		return null;
	}
	
	public static boolean getRemoteIpAddress(User user, final HttpServletRequest httpServletRequest) {
		String ip = user.getWhiteListIp();
		System.out.println("DB ip ----> " +ip);
		if (ip == null) {
			return false;
		}
		String remoteIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");
		System.out.println("X-FORWARDED-FOR ----> " +remoteIpAddress);
		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		return false;
//		remoteIpAddress = httpServletRequest.getHeader("X-REAL-IP");
//		System.out.println("X-REAL-IP ----> " +remoteIpAddress);
//		if (ip.equals(remoteIpAddress)) {
//			return true;
//		}
//		remoteIpAddress = httpServletRequest.getRemoteAddr();
//		System.out.println("getRemoteAddr() ----> " +remoteIpAddress);
//		if(ip.equals(remoteIpAddress)) {
//			return true;
//		}
//		return getClientIpAddress(ip, httpServletRequest);
	}

}
