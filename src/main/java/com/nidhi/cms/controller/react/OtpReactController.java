package com.nidhi.cms.controller.react;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
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
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class OtpReactController extends AbstractController {
	
	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService userService;
	
	
	@PostMapping(value = "/otp")
	public ResponseEntity<Object> verifySignUpOtp(@Valid @RequestBody VerifyOtpRequestModal verifyOtpRequestModal) throws Exception {
		Otp otp = otpService.getOtpDetails(verifyOtpRequestModal);
		if (Objects.isNull(otp)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "InCorrect Otp", HttpStatus.PRECONDITION_FAILED);
		}
		if (BooleanUtils.isFalse(otp.getIsActive())) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp already verified, Please login", HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isExpired = otpService.doesOtpExpired(otp);
		if (BooleanUtils.isTrue(isExpired)) {
			return ResponseHandler.getResponseEntity(ErrorCode.PARAMETER_MISSING_OR_INVALID, "Otp expired, SignUp again", HttpStatus.PRECONDITION_FAILED);

		}
		userService.updateUserIsVerified(otp);
		return ResponseHandler.getOkResponse();

	
	}

}
