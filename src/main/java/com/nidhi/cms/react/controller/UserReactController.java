package com.nidhi.cms.react.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.controller.AbstractController;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;
import com.nidhi.cms.utils.Utility;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class UserReactController extends AbstractController{
	
	@Autowired
	private UserService userservice;
	
	@Autowired
	private OtpService otpService;
	
	@PostMapping(value = "/sign-up")
	public ResponseEntity<Object> clientSignUp(@Valid @RequestBody UserCreateModal userCreateModal) throws Exception {
		final User existingUser = userservice.getUserByUserEmailOrMobileNumber(userCreateModal.getUserEmail(), userCreateModal.getMobileNumber());
		if (existingUser == null) {
			User user = Utility.getUser(userCreateModal);
			String otpUuid = userservice.createUser(user, userCreateModal);
			if (StringUtils.isBlank(otpUuid)) {
				return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Some thing went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
			}
			return ResponseHandler.getMapResponse("otpUuid", otpUuid);
		}
		if (BooleanUtils.isTrue(existingUser.getIsUserVerified())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Either Email or Mobile already Exist.", HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isFalse(existingUser.getIsUserVerified())) {
			String otpUuid = otpService.sendingOtp(existingUser);
			if (otpUuid == null) {
				return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp-already sent, please verify the email & mobile otp."
						+ "if you have lost the OTP , please try again in 5 min", HttpStatus.PRECONDITION_FAILED);
			} else if (StringUtils.isNotBlank(otpUuid)) {
				return ResponseHandler.getMapResponse("message", "Otp-Resent, please verify the email & mobile otp");
			}
		}
		return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Some thing went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
