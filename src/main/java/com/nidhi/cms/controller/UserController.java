package com.nidhi.cms.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.EnumUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.NEFTIncrementalStatusReqModal;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.TxStatusInquiry;
import com.nidhi.cms.modal.request.UserAccountActivateModal;
import com.nidhi.cms.modal.request.UserAllocateFundModal;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserPaymentModeModal;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;
import com.nidhi.cms.modal.request.UserUpdateModal;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.UserBusinessKycModal;
import com.nidhi.cms.modal.response.UserDetailModal;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserAccountStatementService;
import com.nidhi.cms.service.UserBusnessKycService;
import com.nidhi.cms.service.UserPaymentModeService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.utils.ResponseHandler;
import com.nidhi.cms.utils.Utility;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * @author Devendra Gread
 *
 */
@Api(tags = { SwaggerConstant.ApiTag.USER })
@RestController
//@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userservice;

	@Autowired
	private DocService docService;

	@Autowired
	private OtpService otpService;

	@Autowired
	private UserWalletService userWalletService;

	@Autowired
	private UserBusnessKycService userBusnessKycService;

	@Autowired
	private UserAccountStatementService userAccountStatementService;
	
	@Autowired
	private UserPaymentModeService userPaymentModeService;
	
//	@PostMapping(value = "")
	public String userSignUp(@Valid @ModelAttribute UserCreateModal userCreateModal) {
		final User user = beanMapper.map(userCreateModal, User.class);
		User existingUser = userservice.getUserByUserEmailOrMobileNumber(user.getUserEmail(), user.getMobileNumber());
		if (Objects.nonNull(existingUser) && BooleanUtils.isTrue(existingUser.getIsUserVerified())) {
			return "username : user already exist by mobile or email.";
		}
		// user has not filled the OTP yet and trying again for signUp
		// in that case - user is available in our system but not veryfied
		// So, we are triggering again OTP to mobile & email
		if (Objects.nonNull(existingUser) && BooleanUtils.isFalse(existingUser.getIsUserVerified())) {
			Boolean isOtpSent = otpService.sendingOtp(existingUser);
			if (BooleanUtils.isTrue(isOtpSent)) {
				return "Otp-Resent, please verify the email & mobile otp";
			}
			if (isOtpSent == null) {
				return "Otp-already sent, please verify the email & mobile otp."
						+ "if you have lost the OTP , please try again in 30 min";
			}
			return "please try again in some time or reach to the support";
		}
		Boolean isCreated = userservice.createUser(user, userCreateModal.getIsCreatedByAdmin());
		if (BooleanUtils.isFalse(isCreated)) {
			return "please try again in some time or reach to the support";
		}
		return "please verify the email & mobile otp";
	}

//	@GetMapping(value = "/user")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public User getUserDetail() {
		return getLoggedInUserDetails();
	}

	public User getUserByEmailOrMobile(
			@RequestParam(required = true, name = "emailOrMobile") final String emailOrMobile) {
		return userservice.getUserByUserEmailOrMobileNumber(emailOrMobile, emailOrMobile);
	}

	@GetMapping(value = "")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public Map<String, Object> getAllUser(@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		Page<User> users = userservice.getAllUsers(userRequestFilterModel);
		if (users == null || CollectionUtils.isEmpty(users.getContent())) {
			return null;
		}
		return ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
	}

	@PutMapping(value = "/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public ResponseEntity<Object> saveOrUpdateUserDoc(@RequestParam("file") final MultipartFile multiipartFile,
			@RequestParam(required = true, name = "docType") final DocType docType) throws IOException {
		User user = getLoggedInUserDetails();
		if (multiipartFile == null) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "file is blank");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isSaved = userservice.saveOrUpdateUserDoc(user, multiipartFile, docType);
		if (BooleanUtils.isTrue(isSaved)) {
			return ResponseHandler.getMapResponse("message", "file saved successfully");
		}
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
				"Please contact support, unable to persist file");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// @GetMapping(value = "/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public UserDoc getUserDoc(@RequestParam(required = true, name = "docType") final DocType docType) {
		User user = getLoggedInUserDetails();
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		return doc;
	}

//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserDoc getUserDocbyUserId(@RequestParam(required = true, name = "docType") final DocType docType,
			@RequestParam(required = true, name = "userUuid") final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		if (doc != null) {
			return doc;
		}
		return doc;
	}

	@PutMapping(value = "/business-kyc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public ResponseEntity<Object> saveOrUpdateUserBusnessKyc(
			@Valid @RequestBody UserBusinessKycRequestModal userBusunessKycRequestModal) {
		User user = getLoggedInUserDetails();
		final UserBusinessKyc userBusinessKyc = beanMapper.map(userBusunessKycRequestModal, UserBusinessKyc.class);
		userBusinessKyc.setUserId(user.getUserId());
		Boolean isSaved = userBusnessKycService.saveOrUpdateUserBusnessKyc(beanMapper, userBusinessKyc);
		if (BooleanUtils.isTrue(isSaved)) {
			return ResponseHandler.getMapResponse("message", "data saved");
		}
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
				"Please contact support, unable to persist data");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

//	@GetMapping(value = "/get-business-kyc")
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get business kyc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserBusinessKycModal getUserBusnessKyc() {
		User user = getLoggedInUserDetails();
		UserBusinessKyc userBusinessKyc = userBusnessKycService.getUserBusnessKyc(user.getUserId());
		if (userBusinessKyc == null) {
			return null;
		}
		return beanMapper.map(userBusinessKyc, UserBusinessKycModal.class);
	}

	@GetMapping(value = "/get-user-all-kyc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "get business kyc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public List<UserDoc> getUserAllKyc() {
		User user = getLoggedInUserDetails();
		List<UserDoc> userDoc = docService.getUserAllKyc(user.getUserId());
//		if (userDoc == null) {
//			return Collections.emptyList();
//		}
		// return ResponseHandler.getListResponse(beanMapper, userDoc,
		// UserDocModal.class);
		return userDoc;
	}

	// @PutMapping(value = "/change-email-password")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "change Email Or Password", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public String changeEmailOrPassword(@RequestParam(name = "email", required = false) String emailToChange,
			@RequestParam(name = "password", required = false) String passwordToChange) {
		User user = getLoggedInUserDetails();
		if (StringUtils.isBlank(emailToChange) && StringUtils.isBlank(passwordToChange)) {
			return "please provide either email or password";
		}
		Boolean isChanged = userservice.changeEmailOrPassword(user, emailToChange, passwordToChange);
		return Boolean.TRUE.equals(isChanged) ? "Email Or Password changed"
				: "Provided email is taken by someone, Please provide unique email";
	}

//	@PutMapping(value = "/kyc-auth")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public Boolean approveOrDisApproveKyc(@RequestParam("userUuid") String userUuid,
			@RequestParam("kycResponse") Boolean kycResponse,
			@RequestParam(required = true, name = "docType") final DocType docType) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return false;
		}
		if (user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return false;
		}
		return userservice.approveOrDisApproveKyc(user, kycResponse, docType);
	}

//	@GetMapping(value = "/get-user-account-statement")
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get-user-account-statement", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public List<UserAccountStatement> getUserAccountStatementService(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate) {
		User user = getLoggedInUserDetails();
		List<UserAccountStatement> userAccountStatement = userAccountStatementService.getUserAccountStatements(
				user.getUserId(), Utility.stringToLocalDate(fromDate), Utility.stringToLocalDate(toDate));
		if (CollectionUtils.isEmpty(userAccountStatement)) {
			return null;
		}
		return userAccountStatement;
	}

	@PostMapping(value = "/allocate-fund")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "allocate the fund to the user by admin", authorizations = {
			@Authorization(value = "accessToken"), @Authorization(value = "oauthToken") }, hidden = true)
	public Boolean allocateFund(@RequestBody UserAllocateFundModal userAllocateFundModal) {
		User user = userservice.getUserByUserUuid(userAllocateFundModal.getUserUuid());
		if (user == null) {
			return false;
		}
		return userWalletService.allocateFund(user.getUserId(), userAllocateFundModal.getAmount());
	}

//	@GetMapping(value = "/user-wallet")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "get user wallet", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserWallet getUserWallet(@RequestParam("userUuid") String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userWalletService.findByUserId(user.getUserId());
	}

	@PutMapping(value = "/user-account")
	@PreAuthorize("hasAnyRole('ADMIN')")
	@ApiOperation(value = "activate - deactivate user account", authorizations = {
			@Authorization(value = "accessToken"), @Authorization(value = "oauthToken") }, hidden = true)
	public Boolean userActivateOrDeactivate(@RequestBody UserAccountActivateModal userAccountActivateModal) {
		User user = userservice.getUserByUserUuid(userAccountActivateModal.getUserUuid());
		if (user == null) {
			return false;
		}
		return userservice.userActivateOrDeactivate(user, userAccountActivateModal.getIsActivate());
	}

	public Boolean userPaymentMode(@RequestBody UserPaymentModeModal userPaymentModeModal) {
		User user = userservice.getUserByUserUuid(userPaymentModeModal.getUserUuid());
		if (user == null) {
			return false;
		}
		return userWalletService.updateUserPaymentMode(user, userPaymentModeModal.getPaymentMode());
	}

//	@PutMapping(value = "/user-bank-account")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "save or update user bank details", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserBankDetails saveOrUpdateUserBankDetails(@RequestBody UserBankModal userBankModal) {
		User user = getLoggedInUserDetails();
		return userservice.saveOrUpdateUserBankDetails(user, userBankModal);
	}

//	@GetMapping(value = "/get-user-bank-account")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "get user bank details", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserBankDetails getUserBankDetails(@RequestParam("userUuid") String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		return userservice.getUserBankDetails(user);
	}

	@PostMapping(value = "/transaction/payout")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "tx without otp", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txWithoutOTP(@Valid @RequestBody UserTxWoOtpReqModal userTxWoOtpReqModal) {
		User user = getLoggedInUserDetails();
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created");
			errorResponse.addError("errorCode", "" +ErrorCode.ENTITY_NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		
		UserPaymentMode userPaymentMode = userPaymentModeService.getUserPaymentMode(user, EnumUtils.getEnum(PaymentMode.class, userTxWoOtpReqModal.getTxntype()));
		if (userPaymentMode == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "txntype is in-valid");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		if (BooleanUtils.isNotTrue(userPaymentMode.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "permission not granted for txntype - " + userTxWoOtpReqModal.getTxntype());
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		BigDecimal userTxWoAmount = new BigDecimal(userTxWoOtpReqModal.getAmount()).setScale(2, RoundingMode.HALF_DOWN);
		if ((userPaymentMode.getFeePercent() == null) && (userWallet.getAmount() + userWallet.getAdminAllocatedFund()) < userTxWoAmount.doubleValue()) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		if ((userPaymentMode.getFeePercent() != null) && (getFee(userPaymentMode.getFeePercent(), userTxWoOtpReqModal.getAmount()) + userWallet.getAmount() + userWallet.getAdminAllocatedFund()) < userTxWoAmount.doubleValue()) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		userTxWoOtpReqModal.setAmount(userTxWoAmount.doubleValue());
		return userservice.txWithoutOTP(user, userTxWoOtpReqModal);
	}
	
	private Double getFee(Double feePercent, Double amount) {
		return (amount * feePercent) / 100;
	}

	@PostMapping(value = "/transaction/inquiry")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "transaction inquiry", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txStatusInquiry(@Valid @RequestBody TxStatusInquiry txStatusInquiry) {
		User user = getLoggedInUserDetails();
		return userservice.txStatusInquiry(user, txStatusInquiry);
	}

	
	@PostMapping(value = "/transaction/NEFT-status")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "NEFT Incremental status API", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txNEFTStatus(@Valid @RequestBody NEFTIncrementalStatusReqModal nEFTIncrementalStatusReqModal) {
		User user = getLoggedInUserDetails();
		return userservice.txNEFTStatus(user, nEFTIncrementalStatusReqModal);
	}
	
	
	public Boolean apiWhiteListing(@RequestParam("userUuid") String userUuid, @RequestParam("ip") String ip)
			throws IOException {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null || StringUtils.isBlank(ip)) {
			return Boolean.FALSE;
		}
		return userservice.apiWhiteListing(user, ip);
	}

	public User updateUserDetails(UserUpdateModal userUpdateModal)  {
		User user = getLoggedInUserDetails();
		return userservice.updateUserDetails(user, userUpdateModal);
	}
	
	public SystemPrivilege addAccessPrivilegesIntoSystem(String privilegeName) {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.addAccessPrivilegesIntoSystem(privilegeName);
		}
		return null;
	}
	
	public SystemPrivilege updateAccessPrivilegesIntoSystem(String oldPrivilegeName, String newPrivilegeName) {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.updateAccessPrivilegesIntoSystem(oldPrivilegeName, newPrivilegeName);
		}
		return null;
	}
	
	public SystemPrivilege deleteAccessPrivilegesIntoSystem(String privilegeName) {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.deleteAccessPrivilegesIntoSystem(privilegeName);
		}
		return null;
	}
	
	public User createSubAdmin(SubAdminCreateModal subAdminCreateModal) {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.createSubAdmin(subAdminCreateModal);
		}
		return null;
	}
	
	public List<User> getSubAdminList() {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.getSubAdminList();
		}
		return null;
	}
	
	public List<SystemPrivilege> getSystemPrivlegeList() {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.getSystemPrivilegeList();
		}
		return null;
	}


	public UserBusinessKycModal getUserBusnessKybyid(@RequestParam("userUuid") String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		UserBusinessKyc userBusinessKyc = userBusnessKycService.getUserBusnessKyc(user.getUserId());
		if (userBusinessKyc == null) {
			return null;
		}
		return beanMapper.map(userBusinessKyc, UserBusinessKycModal.class);
	}
	
	public UserPaymentMode saveOrUpdateUserPaymentMode(UserPaymentModeModalReqModal userPaymentModeModalReqModal) {
		User user = userservice.getUserByUserUuid(userPaymentModeModalReqModal.getUserUuid());
		if (user == null) {
			return null;
		}
		return userPaymentModeService.saveOrUpdateUserPaymentMode(user, userPaymentModeModalReqModal);
		
	}
	
	public UserPaymentMode activateOrDeActivateUserPaymentMode(String userUuid, PaymentMode paymentMode,  Boolean isActivate) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.activateOrDeActivateUserPaymentMode(user, paymentMode, isActivate);
		
	}
	
}
