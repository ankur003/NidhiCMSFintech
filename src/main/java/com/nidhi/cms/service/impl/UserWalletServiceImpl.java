package com.nidhi.cms.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.UserWalletService;

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
		if (userWallet == null) {
			return Boolean.FALSE;
		}
		userWallet.setAdminAllocatedFund(Double.sum(userWallet.getAdminAllocatedFund(), amount));
		return Boolean.TRUE;
	}

}
