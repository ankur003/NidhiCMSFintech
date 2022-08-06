package com.nidhi.cms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.UpiRegistrationDetail;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.repository.UpiRegistrationDetailRepo;
import com.nidhi.cms.utils.indsind.UPIHelper;

@Service
public class UpiServiceImpl implements UpiService {
	
	@Autowired
	private UpiRegistrationDetailRepo upiRegistrationDetailRepo;
	
	@Autowired
	private UPIHelper upiHelper;
	

	@Override
	public void save(UpiRegistrationDetail upiRegistrationDetail) {
		upiRegistrationDetailRepo.save(upiRegistrationDetail);
	}


	@Override
	public String activateDeActivateUpi(UserWallet usrWallet, boolean isUpiActive) {
		return upiHelper.activateDeActivateUpi(usrWallet, isUpiActive);
	}


	@Override
	public String getUpiTransactionStatus(String txVpaType, String txId) {
		return upiHelper.getUpiTransactionStatus(txVpaType, txId);
	}

}
