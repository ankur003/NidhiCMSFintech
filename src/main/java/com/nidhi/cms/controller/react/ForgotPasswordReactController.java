package com.nidhi.cms.controller.react;
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
import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.controller.AbstractController;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.react.request.ForgotPasswordRequestModel;
import com.nidhi.cms.react.request.MatchForgotPasswordRequestModel;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class ForgotPasswordReactController extends AbstractController {
	
	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/forgot-password")
	public ResponseEntity<Object> forgotPassword(@Valid @RequestBody ForgotPasswordRequestModel forgotPasswordRequestModel) throws Exception {
		User user = null;
		if (forgotPasswordRequestModel.getForgotPassType().equals(ForgotPassType.EMAIL)) {
			user   = userService.findByUserEmail(forgotPasswordRequestModel.getUsername());
			if (user == null || user.getUserEmail() == null || BooleanUtils.isNotTrue(user.getIsActive()) || BooleanUtils.isNotTrue(user.getIsUserVerified())) {
				return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "user not found", HttpStatus.PRECONDITION_FAILED);
			}
			
		} else if (forgotPasswordRequestModel.getForgotPassType().equals(ForgotPassType.PHONE)) {
			user = userService.findByUserMobileNumber(forgotPasswordRequestModel.getUsername());
			if (user == null || user.getMobileNumber() == null || BooleanUtils.isNotTrue(user.getIsActive()) || BooleanUtils.isNotTrue(user.getIsUserVerified())) {
				return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "user not found.", HttpStatus.PRECONDITION_FAILED);
			}
		}
		String otpUuid = otpService.sendingOtp(user, forgotPasswordRequestModel.getForgotPassType());
		if (StringUtils.isBlank(otpUuid)) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Please contact support or try again", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseHandler.getMapResponse("otpUuid", otpUuid);
	}
	
	@PostMapping(value = "/update-password-by-otp")
	public ResponseEntity<Object> updatePasswordByOtp(@RequestBody MatchForgotPasswordRequestModel matchForgotPasswordRequestModel) throws Exception {
		if(BooleanUtils.isFalse(matchForgotPasswordRequestModel.getNewPass().equals(matchForgotPasswordRequestModel.getConfirmPass()))) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "confirmPass and newPass did not matched", HttpStatus.BAD_REQUEST);
		}
		
		Otp otpDetails = otpService.findByOtpUuid(matchForgotPasswordRequestModel.getOtpUuid());
		if (otpDetails == null || BooleanUtils.isFalse(otpDetails.getIsActive()) || otpService.doesOtpExpired(otpDetails)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "otpUuid not valid or already used or expired", HttpStatus.PRECONDITION_FAILED);
		}
		boolean	isMatched = false;
		if (otpDetails.getMobileOtp() != null) {
			isMatched = encoder.matches(matchForgotPasswordRequestModel.getOtp(), otpDetails.getMobileOtp());
		}
		if (isMatched) {
			return updatePassword(matchForgotPasswordRequestModel, otpDetails);
		}
		if (otpDetails.getEmailOtp() != null) {
			isMatched = encoder.matches(matchForgotPasswordRequestModel.getOtp(), otpDetails.getEmailOtp());
		}
		if (isMatched) {
			return updatePassword(matchForgotPasswordRequestModel, otpDetails);
		}
		if (BooleanUtils.isFalse(isMatched)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "wrong otp", HttpStatus.BAD_REQUEST);
		}
		return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);

		
	}

	private ResponseEntity<Object> updatePassword(MatchForgotPasswordRequestModel matchForgotPasswordRequestModel, Otp otpDetails) {
		User user = userService.findByUserId(otpDetails.getUserId());
		if (user == null || BooleanUtils.isNotTrue(user.getIsActive()) || BooleanUtils.isNotTrue(user.getIsUserVerified())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "user not found", HttpStatus.PRECONDITION_FAILED);
		}
		try {
			user.setPassword(encoder.encode(matchForgotPasswordRequestModel.getNewPass()));
			userService.changePassword(user);
			otpDetails.setIsActive(false);
			otpService.updateOtp(otpDetails);
		} catch (Exception e) {
			return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Something went wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return ResponseHandler.getOkResponse();
	}

}
