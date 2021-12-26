package com.nidhi.cms.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;
import com.nidhi.cms.repository.UserPaymentModeRepo;
import com.nidhi.cms.service.UserPaymentModeService;
import com.nidhi.cms.service.email.EmailService;

@Service
public class UserPaymentModeServiceImpl implements UserPaymentModeService {
	
	@Autowired
	private UserPaymentModeRepo userPaymentModeRepo;
	
	@Autowired
	private EmailService emailService;

	private static final Logger LOGGER = LoggerFactory.getLogger(UserPaymentModeServiceImpl.class);

	@Override
	public UserPaymentMode saveOrUpdateUserPaymentMode(User user,
			UserPaymentModeModalReqModal userPaymentModeModalReqModal) {
		UserPaymentMode userPaymentMode = userPaymentModeRepo.findByUserIdAndPaymentMode(user.getUserId(), userPaymentModeModalReqModal.getPaymentMode());
		if (userPaymentMode == null) {
			userPaymentMode = new UserPaymentMode();
			userPaymentMode.setPaymentMode(userPaymentModeModalReqModal.getPaymentMode());
			userPaymentMode.setUserId(user.getUserId());
			userPaymentMode.setFee(userPaymentModeModalReqModal.getFee());
			userPaymentMode.setPaymentModeFeeType(userPaymentModeModalReqModal.getPaymentModeFeeType());
			userPaymentMode.setIsActive(userPaymentModeModalReqModal.isActive());
			UserPaymentMode savedUserPaymentMode =  userPaymentModeRepo.save(userPaymentMode);
			triggerBillingChargesNotifications(user, savedUserPaymentMode);
			return savedUserPaymentMode;
		}
		userPaymentMode.setIsActive(userPaymentModeModalReqModal.isActive());
		if (userPaymentModeModalReqModal.getPaymentMode() != null) {
			userPaymentMode.setPaymentMode(userPaymentModeModalReqModal.getPaymentMode());

		}
		if (userPaymentModeModalReqModal.getFee() != null) {
			userPaymentMode.setFee(userPaymentModeModalReqModal.getFee());

		}
		if (userPaymentMode.getPaymentModeFeeType() != null) {
			userPaymentMode.setPaymentModeFeeType(userPaymentModeModalReqModal.getPaymentModeFeeType());
		}
		if (userPaymentModeModalReqModal.getPaymentMode() == null && userPaymentModeModalReqModal.getFee() == null) {
			return null;
		}
		UserPaymentMode savedUserPaymentMode = userPaymentModeRepo.save(userPaymentMode);
		triggerBillingChargesNotifications(user, savedUserPaymentMode);
		return savedUserPaymentMode;
	}

	private void triggerBillingChargesNotifications(User user, UserPaymentMode userPaymentMode) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[UserPaymentModeServiceImpl.triggerBillingChargesNotifications] user email is blank - {}", user.getUserEmail());
			return;
		}
			MailRequest request = new MailRequest();
			request.setName(user.getFullName());
			request.setSubject("Billing Charges Updated on your NidhiCMS Account");
			request.setTo(new String[] { user.getUserEmail() });
			Map<String, Object> model = new HashMap<>();
			model.put("name", user.getFullName());
			model.put("fee", userPaymentMode.getFee());
			model.put("type", userPaymentMode.getPaymentModeFeeType().name());
			model.put("mode", userPaymentMode.getPaymentMode().name());
			emailService.sendEmail(request, model, null, EmailTemplateConstants.BILLING_CHARGE_SET);
	}

	@Override
	public UserPaymentMode activateOrDeActivateUserPaymentMode(User user, PaymentMode paymentMode, Boolean isActivate) {
		UserPaymentMode userPaymentMode = userPaymentModeRepo.findByUserIdAndPaymentMode(user.getUserId(), paymentMode);
		if (userPaymentMode != null) {
			userPaymentMode.setIsActive(isActivate);
			return userPaymentModeRepo.save(userPaymentMode);
		}
		return null;
	}
	
	@Override
	public UserPaymentMode getUserPaymentMode(User user, PaymentMode paymentMode) {
		if (paymentMode == null || user == null) {
			return null;
		}
		return userPaymentModeRepo.findByUserIdAndPaymentMode(user.getUserId(), paymentMode);
	}

	@Override
	public List<UserPaymentMode> getUserAllPaymentMode(User user) {
		return userPaymentModeRepo.findByUserId(user.getUserId());
	}

}
