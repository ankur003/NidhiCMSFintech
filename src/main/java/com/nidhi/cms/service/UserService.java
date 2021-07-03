package com.nidhi.cms.service;

import org.springframework.data.domain.Page;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserRequestFilterModel;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface UserService {

	String createUser(User user);
	
	User getUserByUserName(String username, Boolean isBlocked);
	
	User getUserByUserUuid(String userUuid);

	Page<User> getAllUsers(UserRequestFilterModel userRequestFilterModel);

}
