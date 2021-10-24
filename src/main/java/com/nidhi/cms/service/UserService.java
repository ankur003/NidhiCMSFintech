package com.nidhi.cms.service;

import java.io.IOException;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserIciciInfo;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface UserService {

	Boolean createUser(User user, Boolean isCreatedByAdim);
	
	User getUserByUserEmailOrMobileNumber(String email, String mobile);
	
	User getUserByUserUuid(String userUuid);

	Page<User> getAllUsers(UserRequestFilterModel userRequestFilterModel);
	
	void updateUserIsVerified(Otp otp);
	
	Boolean saveOrUpdateUserDoc(User user, MultipartFile multiipartFile, DocType docType) throws IOException;

	Boolean changeEmailOrPassword(User user, String emailToChange, String passwordToChange);

	Boolean approveOrDisApproveKyc(User user, Boolean kycResponse, DocType docType);

	Boolean userActivateOrDeactivate(User user, Boolean isActivate);

	UserBankDetails saveOrUpdateUserBankDetails(User user, UserBankModal userBankModal);

	UserBankDetails getUserBankDetails(User user);

	Object getUserRegistrationStatus(UserIciciInfo userIciciInfo) throws IOException;

	Boolean apiWhiteListing(User user, String ip);

	Object txWithoutOTP(User user, UserTxWoOtpReqModal userTxWoOtpReqModal);

}
