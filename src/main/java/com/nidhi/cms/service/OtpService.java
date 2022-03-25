package com.nidhi.cms.service;

import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface OtpService {
	
	Otp findByOtpUuid(String otpUuid);

	String sendingOtp(User existingUser);

	Boolean doesOtpExpired(Otp otp);

	Otp getOtpDetails(VerifyOtpRequestModal verifyOtpRequestModal);

	void sendingOtp(User savedUser, String password);

	String sendingOtp(User user, ForgotPassType forgotPassType);

	void sendPasswordOnEmail(User user, String password);

	String sendingOtpToUserCreatedByAdmin(User user);

	void updateOtp(Otp otp);

}
