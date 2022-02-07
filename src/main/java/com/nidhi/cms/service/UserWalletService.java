package com.nidhi.cms.service;

import java.util.List;

import com.nidhi.cms.domain.UserWallet;

public interface UserWalletService {

	UserWallet findByUserId(Long userId);

	Boolean allocateFund(Long userId, Double amount);

	UserWallet findByMerchantId(String marchantId);

	List<String> getUserNameByMarchantId(String marchantId);

	UserWallet findByVirtualId(String virtualWalletId);

	Boolean updateBalance(UserWallet userWallet, Double amt);
	
	UserWallet save(UserWallet userWallet);

}
