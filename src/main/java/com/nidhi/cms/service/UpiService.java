package com.nidhi.cms.service;

import com.nidhi.cms.domain.UpiRegistrationDetail;
import com.nidhi.cms.domain.UserWallet;

public interface UpiService {

	void save(UpiRegistrationDetail upiRegistrationDetail);

	String activateDeActivateUpi(UserWallet usrWallet, boolean isUpiActive);

	String getUpiTransactionStatus(String custRefNo);

}
