package com.nidhi.cms.service;

import com.nidhi.cms.domain.UpiRegistrationDetail;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.UpiTransactionStatusReqModel;

public interface UpiService {

	void save(UpiRegistrationDetail upiRegistrationDetail);

	String activateDeActivateUpi(UserWallet usrWallet, boolean isUpiActive);

	String getUpiTransactionStatus(String txVpaType, String txId);

	String getUpiTransactionStatus(UpiTransactionStatusReqModel upiTransactionStatusReqModel);

}
