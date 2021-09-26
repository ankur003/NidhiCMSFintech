package com.nidhi.cms.service;

import com.nidhi.cms.domain.UserWallet;

public interface UserWalletService {

	UserWallet findByUserId(Long userId);

}
