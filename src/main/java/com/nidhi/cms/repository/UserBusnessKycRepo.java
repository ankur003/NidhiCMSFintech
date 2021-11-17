package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UserBusinessKyc;

@Repository
public interface UserBusnessKycRepo extends JpaRepository<UserBusinessKyc, Long> , PagingAndSortingRepository<UserBusinessKyc, Long>, JpaSpecificationExecutor<UserBusinessKyc> {

	UserBusinessKyc findByUserId(Long userId);

	UserBusinessKyc findByIndividualPan(String pan);

}
