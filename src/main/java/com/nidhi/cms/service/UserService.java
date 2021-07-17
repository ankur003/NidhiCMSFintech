package com.nidhi.cms.service;

import org.springframework.data.domain.Page;

import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserRequestFilterModel;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface UserService {

	Boolean createUser(User user);
	
	User getUserByUserEmailOrMobileNumber(String email, String mobile);
	
	User getUserByUserUuid(String userUuid);

	Page<User> getAllUsers(UserRequestFilterModel userRequestFilterModel);
	
	void updateUserIsVerified(Otp otp);

}
