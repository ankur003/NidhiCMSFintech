package com.nidhi.cms.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.UserWalletService;

@Service
public class UserWalletServiceImpl implements UserWalletService {
	
	@Autowired
	private UserWalletRepository userWalletRepo;
	
	@Autowired
	private UserRepository userRepository;

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
		userWalletRepo.save(userWallet);
		return Boolean.TRUE;
	}

	@Override
	public UserWallet findByMerchantId(String marchantId) {
		return userWalletRepo.findByMerchantId(marchantId);
	}

	@Override
	public List<String> getUserNameByMarchantId(String marchantId) {
		List<UserWallet> userWallets = userWalletRepo.findByMerchantIdContaining(marchantId);
		if (CollectionUtils.isEmpty(userWallets)) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<>();
		userWallets.forEach(userWallet -> {
			if (userWallet.getUserId() != null) {
				User user = userRepository.findByUserId(userWallet.getUserId());
				if (user != null) {
					list.add(userWallet.getMerchantId() + "-(" +user.getFullName()+ ")");
				}
			}
		});
		return list;
	}

}
