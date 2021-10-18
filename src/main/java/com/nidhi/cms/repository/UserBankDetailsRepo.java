package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UserBankDetails;

@Repository
public interface UserBankDetailsRepo extends JpaRepository<UserBankDetails, Long> , PagingAndSortingRepository<UserBankDetails, Long>, JpaSpecificationExecutor<UserBankDetails> {

	UserBankDetails findByUserId(Long userId);

}