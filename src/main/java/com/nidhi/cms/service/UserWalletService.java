package com.nidhi.cms.service;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;

public interface UserWalletService {

	UserWallet findByUserId(Long userId);

	Boolean allocateFund(Long userId, Double amount);

	Boolean updateUserPaymentMode(User user, PaymentMode paymentMode);

	UserWallet findByMerchantId(String marchantId);

}
