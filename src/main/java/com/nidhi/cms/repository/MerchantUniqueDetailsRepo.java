package com.nidhi.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.nidhi.cms.domain.MerchantUniqueDetails;
import com.nidhi.cms.domain.User;

public interface MerchantUniqueDetailsRepo extends JpaRepository<MerchantUniqueDetails, Long> , PagingAndSortingRepository<MerchantUniqueDetails, Long>, JpaSpecificationExecutor<User> {

	List<MerchantUniqueDetails> findByMerchantIdAndUniqueId(String merchantId, String uniqueid);

}
