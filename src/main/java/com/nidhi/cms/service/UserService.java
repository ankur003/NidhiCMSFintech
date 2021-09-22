package com.nidhi.cms.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
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
	
	Boolean saveOrUpdateUserDoc(User user, MultipartFile multiipartFile, DocType docType) throws IOException;

	Boolean changeEmailOrPassword(User user, String emailToChange, String passwordToChange);

}
