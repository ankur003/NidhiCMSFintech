package com.nidhi.cms.service;

import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.NEFTIncrementalStatusReqModal;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.TxStatusInquiry;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;
import com.nidhi.cms.modal.request.UserUpdateModal;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface UserService {

	String createUser(User user, UserCreateModal userCreateModal) throws Exception;
	
	User getUserByUserEmailOrMobileNumber(String email, String mobile);
	
	User getUserByUserUuid(String userUuid);

	Page<User> getAllUsers(UserRequestFilterModel userRequestFilterModel);
	
	void updateUserIsVerified(Otp otp);
	
	Boolean saveOrUpdateUserDoc(User user, MultipartFile multiipartFile, DocType docType) throws IOException;

	Boolean changeEmailOrPassword(User user, String emailToChange, String passwordToChange) throws Exception;

	Boolean approveOrDisApproveKyc(User user, Boolean kycResponse, DocType docType, String kycRejectReason, Boolean isNotify);

	Boolean userActivateOrDeactivate(User user, Boolean isActivate);

	UserBankDetails saveOrUpdateUserBankDetails(User user, UserBankModal userBankModal);

	UserBankDetails getUserBankDetails(User user);

	Boolean apiWhiteListing(User user, String ip);

	Object txWithoutOTP(User user, UserTxWoOtpReqModal userTxWoOtpReqModal, UserWallet userWallet);

	User updateUserDetails(User user, UserUpdateModal userUpdateModal);

	SystemPrivilege addAccessPrivilegesIntoSystem(String privilegeName);

	SystemPrivilege deleteAccessPrivilegesIntoSystem(String privilegeName);

	SystemPrivilege updateAccessPrivilegesIntoSystem(String oldPrivilegeName, String newPrivilegeName);

	User createSubAdmin(SubAdminCreateModal subAdminCreateModal) throws Exception;

	List<User> getSubAdminList();

	List<SystemPrivilege> getSystemPrivilegeList();

	Object txStatusInquiry(User user, TxStatusInquiry txStatusInquiry);

	Object txNEFTStatus(User user, @Valid NEFTIncrementalStatusReqModal nEFTIncrementalStatusReqModal);

	SystemPrivilege findbyIdprivilege(Long id);

	User getUserDetailByUserUuid(String userUuid);

	User findByUserEmail(String mobileOrEmail);

	User findByUserMobileNumber(String mobileOrEmail);

	User findByApiKey(String apiKey);
	
	User findByUserId(Long userId);

	List<User> getAllUsers();

}



