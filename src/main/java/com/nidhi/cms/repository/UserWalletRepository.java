package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UserWallet;

@Repository
public interface UserWalletRepository extends JpaRepository<UserWallet, Long> , PagingAndSortingRepository<UserWallet, Long>, JpaSpecificationExecutor<UserWallet> {

	UserWallet findByUserId(Long userId);

	
}