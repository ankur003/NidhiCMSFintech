package com.nidhi.cms.service;

import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface UserService {

	String createUser(User user);
	
	User getUserByUserName(String username, Boolean isBlocked);
	
	User getUserByUserUuid(String userUuid);

}
