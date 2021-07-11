package com.nidhi.cms.service.impl;

import java.time.LocalDateTime;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.response.TextLocalResponseModal;
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

	@Autowired
	private ApplicationConfig applicationConfig;

	@Override
	public Boolean sendingOtp(User existingUser) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		if (BooleanUtils.isFalse(doesOtpExpired(otp))) {
			return null;
		}
		String mobileOtp = Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(), applicationConfig.getTextLocalApiSender(), 
				existingUser.getMobileNumber());
		if (StringUtils.isBlank(mobileOtp)) {
			return Boolean.FALSE;
		}
		String emailOtp = sendOtpOnEmail(existingUser);
		if (StringUtils.isBlank(emailOtp)) {
			return Boolean.FALSE;
		}
		return saveOtpDetails(mobileOtp, emailOtp, existingUser, otp);
	}

	private String sendOtpOnEmail(User existingUser) {
		String emailOtp = Utility.getRandomNumberString();
		System.out.println(emailOtp + "  email otp");
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

	@Override
	public Boolean doesOtpExpired(Otp otp) {
		if (otp == null) {
			return Boolean.TRUE;
		}
		String expireMin = applicationConfig.getOtpExpireMinutes();
		if (StringUtils.isBlank(expireMin)) {
			expireMin = "30";
		}
		if (otp.getUpdatedAt().plusMinutes(Long.valueOf(expireMin)).isAfter(LocalDateTime.now())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	@Override
	public Otp getOtpDetails(String mobileOtp, String emailOtp) {
		return otpRepository.findByMobileOtpAndEmailOtp(mobileOtp, emailOtp);
	}

}
