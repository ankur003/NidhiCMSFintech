package com.nidhi.cms.controller.react;

import java.util.HashMap;
import java.util.Map;
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
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.react.request.TriggerOtpModel;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

import springfox.documentation.annotations.ApiIgnore;

@ApiIgnore
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class OtpReactController extends AbstractController {
	
	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/otp")
	public ResponseEntity<Object> verifySignUpOtpOrUserOtp(@Valid @RequestBody VerifyOtpRequestModal verifyOtpRequestModal) throws Exception {
		if (StringUtils.isBlank(verifyOtpRequestModal.getOtpUuid())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp Uuid is missing or null", HttpStatus.PRECONDITION_FAILED);
		}
		Otp otp = otpService.getOtpDetails(verifyOtpRequestModal);
		if (Objects.isNull(otp)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "InCorrect Otp", HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isFalse(otp.getIsActive())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp already verified, Please login", HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isExpired = otpService.doesOtpExpired(otp);
		if (BooleanUtils.isTrue(isExpired)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp has been expired", HttpStatus.PRECONDITION_FAILED);

		}
		userService.updateUserIsVerified(otp);
		otp.setIsActive(false);
		otpService.updateOtp(otp);
		return ResponseHandler.getOkResponse();
	}
	
	@PostMapping(value = "/trigger-otp")
	public ResponseEntity<Object> triggerOtp(@Valid @RequestBody TriggerOtpModel triggerOtpModel) throws Exception {
		final User user = userService.findByUserEmail(triggerOtpModel.getEmail());
		if (user == null || user.getMobileNumber() == null || !user.getMobileNumber().equals(triggerOtpModel.getMobile()) 
				|| BooleanUtils.isFalse(user.getIsActive())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "user not found", HttpStatus.BAD_REQUEST);
		}
		if (BooleanUtils.isFalse(user.getIsUserCreatedByAdmin()) || BooleanUtils.isTrue(user.getIsUserVerified())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Incorrect user or already verified", HttpStatus.PRECONDITION_FAILED);
		}
		String otpUuid = otpService.sendingOtpToUserCreatedByAdmin(user);
			if (otpUuid == null) {
				return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp-already sent, please verify the email & mobile otp."
						+ "if you have lost the OTP , please try again in 5 min", HttpStatus.PRECONDITION_FAILED);
			} else if (StringUtils.isNotBlank(otpUuid)) {
				final Map<String, Object> responseMap = new HashMap<>();
				responseMap.put("otpUuid", otpUuid);
				responseMap.put("message", "Otp-Sent, please verify the email & mobile otp");
				return ResponseHandler.getContentResponse(responseMap);
			}
		return ResponseHandler.getResponseEntity(ErrorCode.GENERIC_SERVER_ERROR, "Some thing wenut wrong", HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
