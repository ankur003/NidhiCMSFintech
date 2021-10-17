package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> , PagingAndSortingRepository<User, Long>, JpaSpecificationExecutor<User>{
	
	User findByUserEmailOrMobileNumber(String email, String mobile);

	User findByUserUuidAndIsUserVerified(String userUuid, Boolean isVerified);
	User findByUserUuid(String userUuid);
	
	User findByUserEmail(String email);
}
