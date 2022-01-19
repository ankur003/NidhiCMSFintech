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
	
	List<User> findByIsSubAdminAndIsAdmin(Boolean isSubAdmin, Boolean isAdmin);
	
	List<User> findByIsSubAdminAndIsAdminAndKycStatusIn(Boolean isSubAdmin, Boolean isAdmin, List<String> kycStatus);
	
	User findByUserUuid(String userUuid);
	
	User findByUserId(Long userId);
	
	User findByUserEmail(String email);

	User findByMobileNumber(String mobileNumber);

	User findByApiKey(String apiKey);

	List<User> findByPrivilageNamesContaining(String privilegeName);

	List<User> findByUserIdIn(List<Long> userIds);
}
