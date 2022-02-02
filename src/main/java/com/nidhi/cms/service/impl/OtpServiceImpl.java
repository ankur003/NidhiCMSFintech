package com.nidhi.cms.service.impl;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.modal.request.VerifyOtpRequestModal;
import com.nidhi.cms.repository.OtpRepository;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.email.EmailService;
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
	private EmailService emailService;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(OtpServiceImpl.class);
	
	@Override
	public String sendingOtp(User existingUser) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		if (BooleanUtils.isFalse(doesOtpExpired(otp))) {
			return null;
		}
		
		String mobileOtp = Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(), applicationConfig.getTextLocalApiSender(), existingUser.getMobileNumber());
									 
		if (StringUtils.isBlank(mobileOtp)) {
			return mobileOtp;
		}
		String emailOtp = Utility.getRandomNumberString();
		if (BooleanUtils.isTrue(existingUser.getIsUserCreatedByAdmin())) {
			sendPasswordOnEmail(existingUser, existingUser.getRawp());
		} else {
			sendOtpOnEmail(existingUser, emailOtp);
		}
		return saveOtpDetails(mobileOtp, emailOtp, existingUser, otp);
	}
	
	@Override
	public String sendingOtpToUserCreatedByAdmin(User user) {
		Otp otp = otpRepository.findByUserId(user.getUserId());
		if (otp != null && BooleanUtils.isFalse(doesOtpExpired(otp))) {
			return null;
		}
		String emailOtp = Utility.getRandomNumberString();
		sendOtpOnEmail(user, emailOtp);
		String mobileOtp = Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(), applicationConfig.getTextLocalApiSender(), user.getMobileNumber());
		return saveOtpDetails(mobileOtp, emailOtp, user, otp);
	}
	
	@Override
	public void sendingOtp(User existingUser, String password) {
		Otp otp = otpRepository.findByUserId(existingUser.getUserId());
		String mobileOtp = Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(),  applicationConfig.getTextLocalApiSender(), existingUser.getMobileNumber());
									 
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
	
	@Override
	public void sendPasswordOnEmail(User user, String password) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[OtpServiceImpl.sendOtpOnEmail ] user email is blank - {}", user.getUserEmail());
			return;
		}
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Email Verification | Nidhi CMS");
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> modal = new HashMap<>();
		modal.put("name", user.getFullName());
		modal.put("password", password);
		emailService.sendMailAsync(request, modal, "", EmailTemplateConstants.PASSWORD);

	}
	
	private void sendOtpOnEmail(User user, String emailOtp, String password) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[OtpServiceImpl.sendOtpOnEmail ] user email is blank - {}", user.getUserEmail());
			return;
		}
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Email Verification | Nidhi CMS");
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> modal = new HashMap<>();
		modal.put("name", user.getFullName());
		modal.put("OTP", emailOtp);
		modal.put("password", password);
		emailService.sendMailAsync(request, modal, "", EmailTemplateConstants.OTP_PASSWORD);

	}


	private void sendOtpOnEmail(User user, String emailOtp) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[OtpServiceImpl.sendOtpOnEmail] user email is blank - {}", user.getUserEmail());
			return;
		}
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Email Verification | Nidhi CMS Account");
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> modal = new HashMap<>();
		modal.put("name", user.getFullName());
		modal.put("otp", emailOtp);
		emailService.sendMailAsync(request, modal, null, EmailTemplateConstants.OTP);
	}

	private String saveOtpDetails(String mobileOtp, String emailOtp, User existingUser, Otp existingOtp) {
		if (existingOtp == null) {
			Otp otp = new Otp();
			otp.setMobileOtp(encoder.encode(mobileOtp));
			otp.setEmailOtp(encoder.encode(emailOtp));
			otp.setUserId(existingUser.getUserId());
			otp.setOtpUuid(Utility.getUniqueUuid());
			otp.setIsActive(true);
			Otp savedOtp = otpRepository.save(otp);
			return savedOtp.getOtpUuid();
		}
		if (mobileOtp != null ) {
			existingOtp.setMobileOtp(encoder.encode(mobileOtp));
		}
		if (emailOtp != null ) {
			existingOtp.setEmailOtp(encoder.encode(emailOtp));
		}
		Otp savedOtp = otpRepository.save(existingOtp);
		return savedOtp.getOtpUuid();
	}

	@Override
	public Boolean doesOtpExpired(Otp otp) {
		if (otp == null) {
			return Boolean.TRUE;
		}
		String expireMin = applicationConfig.getOtpExpireMinutes();
		if (StringUtils.isBlank(expireMin)) {
			expireMin = "05";
		}
		if (otp.getUpdatedAt().plusMinutes(Long.valueOf(expireMin)).isAfter(LocalDateTime.now())) {
			return Boolean.FALSE;
		}
		return Boolean.TRUE;

	}

	@Override
	public Otp getOtpDetails(VerifyOtpRequestModal verifyOtpRequestModal) {
		Otp otp = otpRepository.findByOtpUuid(verifyOtpRequestModal.getOtpUuid());
		if (otp == null) {
			return null;
		}
		boolean isMatched = encoder.matches(verifyOtpRequestModal.getMobileOtp(), otp.getMobileOtp());
		if (!isMatched) {
			return null;
		}
		isMatched = encoder.matches(verifyOtpRequestModal.getEmailOtp(), otp.getEmailOtp());
		if (!isMatched) {
			return null;
		}
		return otp;
	}

	@Override
	public String sendingOtp(User user, ForgotPassType forgotPassType) {
		Otp otp = otpRepository.findByUserId(user.getUserId());
		if (forgotPassType.equals(ForgotPassType.EMAIL)) {
			String emailOtp = Utility.getRandomNumberString();
			sendOtpOnEmail(user, emailOtp);
			return saveOtpDetails(null, emailOtp, user, otp);
		} else if (forgotPassType.equals(ForgotPassType.PHONE)) {
			String mobileOtp = Utility.sendAndGetMobileOTP(applicationConfig.getTextLocalApiKey(), applicationConfig.getTextLocalApiSender(), user.getMobileNumber());
			return saveOtpDetails(mobileOtp, null, user, otp);
		}
		return null;
	}

	@Override
	public Otp findByOtpUuid(String otpUuid) {
		return otpRepository.findByOtpUuid(otpUuid);
	}

}
