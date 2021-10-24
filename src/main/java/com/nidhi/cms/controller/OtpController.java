package com.nidhi.cms.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;

/**
 * 
 *
 * @author Ankur Bansala
 */
@Component
public class OtpController extends AbstractController {

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserService userService;

	public String verifyOTP(@Valid @RequestBody VerifyOtpRequestModal verifyOtpRequestModal) {
		Otp otp = otpService.getOtpDetails(verifyOtpRequestModal.getMobileOtp(), verifyOtpRequestModal.getEmailOtp());
		if (Objects.isNull(otp)) {
			return "Either email or mobile OTP is incorrect, please try again.";
		}
		if (BooleanUtils.isFalse(otp.getIsActive())) {
			return "Otp already verified, please login";
		}
		Boolean isExpired = otpService.doesOtpExpired(otp);
		if (BooleanUtils.isTrue(isExpired)) {
			return "Otp expired, please signUp again.";
		}
		userService.updateUserIsVerified(otp);
		return "Otp verified, please proceed with login.";
	}

}
