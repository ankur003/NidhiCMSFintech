package com.nidhi.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.MerchantUniqueDetails;
import com.nidhi.cms.repository.MerchantUniqueDetailsRepo;
import com.nidhi.cms.service.MerchantUniqueDetailsService;

@Service
public class MerchantUniqueDetailsImpl implements  MerchantUniqueDetailsService{
	
	@Autowired
	MerchantUniqueDetailsRepo merchantUniqueDetailsRepo;

	@Override
	public List<MerchantUniqueDetails> findByMerchantIdAndUniqueId(String merchantId, String uniqueid) {
		return merchantUniqueDetailsRepo.findByMerchantIdAndUniqueId(merchantId, uniqueid);
	}

	@Override
	public void save(String merchantId, String uniqueId) {
		MerchantUniqueDetails merchantUniqueDetails = new MerchantUniqueDetails();
		merchantUniqueDetails.setMerchantId(merchantId);
		merchantUniqueDetails.setUniqueId(uniqueId);
		merchantUniqueDetailsRepo.save(merchantUniqueDetails);
	}

}
