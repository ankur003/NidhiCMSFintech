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
	
	User findByUsernameAndIsBlocked(String username, Boolean isBlocked);

	User findByUserUuidAndIsBlocked(String userUuid, Boolean isBlocked);
}
