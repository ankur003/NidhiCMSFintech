package com.nidhi.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;
import com.nidhi.cms.repository.UserPaymentModeRepo;
import com.nidhi.cms.service.UserPaymentModeService;

@Service
public class UserPaymentModeServiceImpl implements UserPaymentModeService {
	
	@Autowired
	private UserPaymentModeRepo userPaymentModeRepo;

	@Override
	public UserPaymentMode saveOrUpdateUserPaymentMode(User user,
			UserPaymentModeModalReqModal userPaymentModeModalReqModal) {
		UserPaymentMode userPaymentMode = userPaymentModeRepo.findByUserIdAndPaymentMode(user.getUserId(), userPaymentModeModalReqModal.getPaymentMode());
		if (userPaymentMode == null) {
			userPaymentMode = new UserPaymentMode();
			userPaymentMode.setPaymentMode(userPaymentModeModalReqModal.getPaymentMode());
			userPaymentMode.setUserId(user.getUserId());
			userPaymentMode.setFeePercent(userPaymentModeModalReqModal.getFeePercent());
			return userPaymentModeRepo.save(userPaymentMode);
		}
		if (userPaymentModeModalReqModal.getPaymentMode() != null) {
			userPaymentMode.setPaymentMode(userPaymentModeModalReqModal.getPaymentMode());

		}
		if (userPaymentModeModalReqModal.getFeePercent() != null) {
			userPaymentMode.setFeePercent(userPaymentModeModalReqModal.getFeePercent());

		}
		if (userPaymentModeModalReqModal.getPaymentMode() == null && userPaymentModeModalReqModal.getFeePercent() == null) {
			return null;
		}
		return userPaymentModeRepo.save(userPaymentMode);
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
