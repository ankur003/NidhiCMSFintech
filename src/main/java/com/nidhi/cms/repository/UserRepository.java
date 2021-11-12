package com.nidhi.cms.repository;

import java.util.List;

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
	
	List<User> findByIsSubAdmin(Boolean isSubAdmin);
	
	User findByUserUuid(String userUuid);
	
	User findByUserEmail(String email);

	User findByUserMobileNumber(String mobileNumber);
}
