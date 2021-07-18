package com.nidhi.cms.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

import io.swagger.annotations.Api;

/**
 * 
 *
 * @author Ankur Bansala
 */
@Api(tags = { SwaggerConstant.ApiTag.OTP })
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/otp")
public class OtpController extends AbstractController {

	@Autowired
	private OtpService otpService;
	
	@Autowired
	private UserService userService;

	@PostMapping(value = "/verify")
	public ResponseEntity<Object> verifyOTP(@Valid @RequestBody VerifyOtpRequestModal verifyOtpRequestModal) {
		Otp otp  = otpService.getOtpDetails(verifyOtpRequestModal.getMobileOtp(), verifyOtpRequestModal.getEmailOtp());
		if (Objects.isNull(otp)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "Either email or mobile OTP is incorrect, please try again");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isFalse(otp.getIsActive())) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "Otp already verified, please login");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isExpired = otpService.doesOtpExpired(otp);
		if (BooleanUtils.isTrue(isExpired)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "Otp expired, please signUp again.");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		userService.updateUserIsVerified(otp);
		return ResponseHandler.getMapResponse("message", "Otp verified, please proceed with login");
	}

}
