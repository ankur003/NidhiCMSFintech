package com.nidhi.cms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.repository.OtpRepository;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.utils.Utility;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Service
public class OtpServiceImpl implements OtpService {

	@Autowired
	private OtpRepository otpRepository;
	
	@Override
	public Boolean sendingOtp(User existingUser) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		String mobileOtp = sendOtpOnMobile(existingUser);
		if (StringUtils.isBlank(mobileOtp)) {
			return Boolean.FALSE;
		}
		String emailOtp = sendOtpOnEmail(existingUser);
		if (StringUtils.isBlank(emailOtp)) {
			return Boolean.FALSE;
		}
		return saveOtpDetails(mobileOtp, emailOtp, existingUser, otp);
	}

	private String sendOtpOnMobile(User existingUser) {
		String mobileOtp = Utility.getRandomNumberString();
		if (mobileOtp != null) {
			return mobileOtp;
		}
		return StringUtils.EMPTY;
	}

	private String sendOtpOnEmail(User existingUser) {
		String emailOtp = Utility.getRandomNumberString();
		if (emailOtp != null) {
			return emailOtp;
		}
		return StringUtils.EMPTY;
	}

	private Boolean saveOtpDetails(String mobileOtp, String emailOtp, User existingUser, Otp existingOtp) {
		if (existingOtp == null) {
			Otp otp = new Otp();
			otp.setMobileOtp(mobileOtp);
			otp.setEmailOtp(emailOtp);
			otp.setUserId(existingUser.getUserId());
			otp.setOtpUuid(Utility.getUniqueUuid());
			otp.setIsActive(true);
			return otpRepository.save(otp) != null;
		}
		existingOtp.setMobileOtp(mobileOtp);
		existingOtp.setEmailOtp(emailOtp);
		return otpRepository.save(existingOtp) != null;
	}

}
