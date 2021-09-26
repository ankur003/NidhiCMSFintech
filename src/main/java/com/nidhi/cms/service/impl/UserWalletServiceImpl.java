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

}
