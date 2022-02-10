package com.nidhi.cms.service;

import java.util.List;

import com.nidhi.cms.domain.MerchantUniqueDetails;

public interface MerchantUniqueDetailsService {

	List<MerchantUniqueDetails> findByMerchantIdAndUniqueId(String merchantId, String uniqueid);

	void save(String merchantId, String uniqueId);

}
