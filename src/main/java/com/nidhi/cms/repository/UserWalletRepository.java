package com.nidhi.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> , PagingAndSortingRepository<UserWallet, Long>, JpaSpecificationExecutor<UserWallet> {

	UserWallet findByUserId(Long userId);

	UserWallet findFirstByOrderByUserWalletIdDesc();

	UserWallet findByMerchantId(String marchantId);
	
	UserWallet findByWalletUuid(String virtualID);

	List<UserWallet> findByMerchantIdContaining(String marchantId);

	List<UserWallet> findByUserIdIn(List<Long> userIds);
	
	UserWallet findByUpiVirtualAddress(String upiVirtualAddress);

}