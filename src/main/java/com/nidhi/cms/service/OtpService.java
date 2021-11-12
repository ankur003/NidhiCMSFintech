package com.nidhi.cms.service;

import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface OtpService {

	Boolean sendingOtp(User existingUser);

	Boolean doesOtpExpired(Otp otp);

	Otp getOtpDetails(String mobileOtp, String emailOtp);

	void sendingOtp(User savedUser, String password);

	Boolean sendingOtp(User user, ForgotPassType forgotPassType);

	Otp findByMobileOtpOrEmailOtp(String mobileOtp, String emailOtp);

}
