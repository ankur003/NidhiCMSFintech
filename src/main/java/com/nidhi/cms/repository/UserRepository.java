package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsernameAndIsBlocked(String username, Boolean isBlocked);
}
