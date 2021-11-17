package com.nidhi.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.utils.Utility;

@Service
public class UserWalletServiceImpl implements UserWalletService {
	
	@Autowired
	UserWalletRepository userWalletRepo;

	@Override
	public UserWallet findByUserId(Long userId) {
		return userWalletRepo.findByUserId(userId);
	}

	@Override
	public Boolean allocateFund(Long userId, Double amount) {
		UserWallet userWallet = findByUserId(userId);
		if (userWallet == null || amount == null) {
			return Boolean.FALSE;
		}
		userWallet.setAdminAllocatedFund(Double.sum(userWallet.getAdminAllocatedFund(), amount));
		userWalletRepo.save(userWallet);
		return Boolean.TRUE;
	}

	@Override
	public Boolean updateUserPaymentMode(User user, PaymentMode paymentMode) {
		UserWallet userWallet = findByUserId(user.getUserId());
		if (userWallet == null || paymentMode == null) {
			return Boolean.FALSE;
		}
		userWallet.setPaymentMode(paymentMode);
		userWalletRepo.save(userWallet);
		return Boolean.TRUE;
	}

	@Override
	public UserWallet findByMerchantId(String marchantId) {
		return userWalletRepo.findByMerchantId(marchantId);
	}

}
