package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Repository
public interface OtpRepository extends JpaRepository<Otp, Long> , PagingAndSortingRepository<Otp, Long>, JpaSpecificationExecutor<User> {

	Otp findByUserId(Long userId);
	
}
