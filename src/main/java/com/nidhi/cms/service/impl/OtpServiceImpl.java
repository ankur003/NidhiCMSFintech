package com.nidhi.cms.service.impl;

import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.nidhi.cms.config.ApplicationConfig;
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

	@Autowired
	private ApplicationConfig applicationConfig;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public Boolean sendingOtp(User existingUser) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		if (BooleanUtils.isFalse(doesOtpExpired(otp))) {
			return null;
		}
		String mobileOtp = "123456";/*
									 * Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(),
									 * applicationConfig.getTextLocalApiSender(), existingUser.getMobileNumber());
									 */
		if (StringUtils.isBlank(mobileOtp)) {
			return Boolean.FALSE;
		}
		String emailOtp = Utility.getRandomNumberString();
		sendOtpOnEmail(existingUser, emailOtp);
		if (StringUtils.isBlank(emailOtp)) {
			return Boolean.FALSE;
		}
		return saveOtpDetails(mobileOtp, emailOtp, existingUser, otp);
	}
	
	@Override
	public void sendingOtp(User existingUser, String password) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		String mobileOtp = "123456";/*
									 * Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(),
									 * applicationConfig.getTextLocalApiSender(), existingUser.getMobileNumber());
									 */
		if (StringUtils.isBlank(mobileOtp)) {
			return ;
		}
		String emailOtp = Utility.getRandomNumberString();
		sendOtpOnEmail(existingUser, emailOtp, password);
		if (StringUtils.isBlank(emailOtp)) {
			return ;
		}
		 saveOtpDetails(mobileOtp, emailOtp, existingUser, otp);
	}
	
	private void sendOtpOnEmail(User existingUser, String emailOtp,  String password) {
		CompletableFuture.runAsync(() -> {
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(existingUser.getUserEmail());
				msg.setSubject("Verify OTP | Nidhi CMS");
				msg.setText("Use the OTP " + emailOtp
						+ " to verify your NidhiCMS account. \n Password : " +password+"\n DO NOT SHARE OTP WITH ANYONE. \n \n \n \nRegards NidhiCms");
				javaMailSender.send(msg);
			} catch (final Exception e) {
				// to be handled
			}
		});

	}

	private void sendOtpOnEmail(User existingUser, String emailOtp) {
		CompletableFuture.runAsync(() -> {
			try {
				SimpleMailMessage msg = new SimpleMailMessage();
				msg.setTo(existingUser.getUserEmail());
				msg.setSubject("Verify OTP | Nidhi CMS");
				msg.setText("Use the OTP " + emailOtp
						+ " to verify your NidhiCMS account. This is valid for 30 minutes. DO NOT SHARE OTP WITH ANYONE. \n \n \n \nRegards NidhiCms");
				javaMailSender.send(msg);
			} catch (final Exception e) {
				// to be handled
			}
		});

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
