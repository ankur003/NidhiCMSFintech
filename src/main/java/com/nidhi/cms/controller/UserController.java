package com.nidhi.cms.controller;

import static com.nidhi.cms.constants.JwtConstants.AUTH_TOKEN;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
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
import com.nidhi.cms.repository.UserRepository;
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
	private UserRepository userRepo;

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
			return "Either Email or Mobile already Exist.";
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
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public User getUserDetail() {
		return getLoggedInUserDetails();
	}

	public User getUserByEmailOrMobile(
			@RequestParam(required = true, name = "emailOrMobile") final String emailOrMobile) {
		return userservice.getUserByUserEmailOrMobileNumber(emailOrMobile, emailOrMobile);
	}

	@GetMapping(value = "")
	// @PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public Map<String, Object> getAllUser(@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		if(BooleanUtils.isTrue(getLoggedInUserDetails().getIsAdmin())) {
			Page<User> users = userservice.getAllUsers(userRequestFilterModel);
			if (users == null || CollectionUtils.isEmpty(users.getContent())) {
				return null;
			}
			return ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
		}
		return null;
	}

	@PutMapping(value = "/doc")
	// @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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
			userBusnessKycService.updateKycStatus(user, KycStatus.UNDER_REVIEW);
			return ResponseHandler.getMapResponse("message", "file saved successfully");
		}
		ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
				"Please contact support, unable to persist file");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	// @GetMapping(value = "/doc")
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserDoc getUserDoc(@RequestParam(required = true, name = "docType") final DocType docType) {
		User user = getLoggedInUserDetails();
		return docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
	}

//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserDoc getUserDocbyUserId(@RequestParam(required = true, name = "docType") final DocType docType,
			@RequestParam(required = true, name = "userUuid") final String userUuid) {
		User user = userservice.getUserDetailByUserUuid(userUuid);
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		if (null!=doc) {
			return doc;
		}
		return doc;
	}

//	@PutMapping(value = "/business-kyc")
//	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") }, hidden = true)
	public ResponseEntity<Object> saveOrUpdateUserBusnessKyc(UserBusinessKycRequestModal userBusunessKycRequestModal) {
		User user = getLoggedInUserDetails();
		final UserBusinessKyc userBusinessKyc = beanMapper.map(userBusunessKycRequestModal, UserBusinessKyc.class);
		userBusinessKyc.setUserId(user.getUserId());
		ErrorResponse errorResponse = validatePan(user.getUserId(), userBusunessKycRequestModal.getIndividualPan());
		if (errorResponse != null) {
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isSaved = userBusnessKycService.saveOrUpdateUserBusnessKyc(beanMapper, userBusinessKyc);
		if (BooleanUtils.isTrue(isSaved)) {
			return ResponseHandler.getMapResponse("message", "data saved");
		}
		errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
				"Please contact support, unable to persist data");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorResponse validatePan(Long userId, String pan) {
		if (pan == null) {
			return null;
		}
		UserBusinessKyc userBusinessKyc = userBusnessKycService.getUserBusnessKycByPan(pan);
		if (userBusinessKyc == null) {
			return null;
		}
		if (pan.equals(userBusinessKyc.getIndividualPan()) && !userId.equals(userBusinessKyc.getUserId())) {
			return new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "pan number should be unique");
		}
		return null;
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
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "get business kyc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") }, hidden = true)
	public List<UserDoc> getUserAllKyc() {
		User user = getLoggedInUserDetails();
		return docService.getUserAllKyc(user.getUserId());
	}

	// @PutMapping(value = "/change-email-password")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
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
			@RequestParam(name = "kycRejectReason", required = false) String kycRejectReason,
			@RequestParam(required = true, name = "docType") final DocType docType,
			HttpServletRequest request) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return false;
		}
		if (user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return false;
		}
		if (BooleanUtils.isNotTrue(kycResponse) && kycRejectReason == null) {
			return false;
		}
		Boolean isDone = userservice.approveOrDisApproveKyc(user, kycResponse, docType, kycRejectReason);
		if (BooleanUtils.isTrue(isDone) && user.getKycStatus().equals(KycStatus.VERIFIED) && user.getToken() == null) {
			HttpSession session = request.getSession();
			Object token = session.getServletContext().getAttribute(AUTH_TOKEN);
			if (Objects.isNull(token)) {
				token = session.getAttribute(AUTH_TOKEN);
			}
			user.setToken(token.toString());
			userRepo.save(user);
		}
		return isDone;
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
			return Collections.emptyList();
		}
		return userAccountStatement;
	}

	@PostMapping(value = "/allocate-fund")
	//@PreAuthorize("hasAnyRole('ADMIN')")
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
//	@PreAuthorize("hasAnyRole('ADMIN')")
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
		UserBankDetails response = userservice.saveOrUpdateUserBankDetails(user, userBankModal);
		if (response != null ) {
			userBusnessKycService.updateKycStatus(user, KycStatus.UNDER_REVIEW);
		}
		return response;
	}

//	@GetMapping(value = "/get-user-bank-account")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "get user bank details", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserBankDetails getUserBankDetails(@RequestParam("userUuid") String userUuid) {
		User user = userservice.getUserDetailByUserUuid(userUuid);
		return userservice.getUserBankDetails(user);
	}
	
	public static boolean getRemoteIpAddress(User user, final HttpServletRequest httpServletRequest) {
		String ip = user.getWhiteListIp();
		if (ip == null) {
			return false;
		}
		String remoteIpAddress = httpServletRequest.getHeader("X-FORWARDED-FOR");

		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		remoteIpAddress = httpServletRequest.getHeader("X-REAL-IP");
		if (ip.equals(remoteIpAddress)) {
			return true;
		}
		remoteIpAddress = httpServletRequest.getRemoteAddr();
		if(ip.equals(remoteIpAddress)) {
			return true;
		}
		return getClientIpAddress(ip, httpServletRequest);
	}
	
	private static final String[] HEADERS_TO_TRY = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR" };

private static boolean getClientIpAddress(String ip2, HttpServletRequest request) {
    for (String header : HEADERS_TO_TRY) {
        String ip = request.getHeader(header);
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip) && ip.equalsIgnoreCase(ip2)) {
            	 return true;
        }
    }

    return false;
}

	@PostMapping(value = "/transaction/payout")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "tx without otp", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txWithoutOTP(@Valid @RequestBody UserTxWoOtpReqModal userTxWoOtpReqModal, final HttpServletRequest httpServletRequest) {
		
		String apiKey = httpServletRequest.getHeader("apiKey");
		if (StringUtils.isBlank(apiKey)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied - apiKey is reuired");
			errorResponse.addError("errorCode", "" +ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		User user = userservice.findByApiKey(apiKey);
		if (user == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied - invalid apiKey");
			errorResponse.addError("errorCode", "" +ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (BooleanUtils.isNotTrue(user.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over de-activated account");
			errorResponse.addError("errorCode", "" +ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		boolean isValid = getRemoteIpAddress(user, httpServletRequest);
		if (!isValid) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over ip");
			errorResponse.addError("errorCode", "" +ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		}
		
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created");
			errorResponse.addError("errorCode", "" +ErrorCode.ENTITY_NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		
		if (userWallet.getMerchantId() == null || !userWallet.getMerchantId().equals(userTxWoOtpReqModal.getMerchantId())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "merchantId not valid.");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
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
		if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.PERCENTAGE)) {
			if ((userPaymentMode.getFee() == null)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
			}
		if ((userWallet.getAmount() + userWallet.getAdminAllocatedFund()) < (getFee(userPaymentMode.getFee(), userTxWoOtpReqModal.getAmount())) + userTxWoAmount.doubleValue()) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance.");
			errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
			}
		} else if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.FLAT)) {
			if (userPaymentMode.getFee() == null) {
				final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
				errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
	            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
				}
			if ((userWallet.getAmount() + userWallet.getAdminAllocatedFund()) < (userTxWoAmount.doubleValue() + userPaymentMode.getFee())) {
				final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance.");
				errorResponse.addError("errorCode", "" +ErrorCode.PARAMETER_MISSING_INVALID.value());
	            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
				}
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
	
	
	public Boolean apiWhiteListing(@RequestParam("userUuid") String userUuid, @RequestParam("ip") String ip) {
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
	
	public User updateSubadminuser(User user,UserUpdateModal userUpdateModal)  {
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
	public SystemPrivilege getPriviligebyid(Long privilegeId) {
			return userservice.findbyIdprivilege(privilegeId);
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
		if (CollectionUtils.isEmpty(subAdminCreateModal.getPrivilageNames())) {
			return null;
		}
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
		return Collections.emptyList();
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
		if (userPaymentModeModalReqModal.getPaymentModeFeeType() == null || userPaymentModeModalReqModal.getPaymentMode() == null) {
			return null;
		}
		User userAdmin = getLoggedInUserDetails();
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userPaymentModeModalReqModal.getUserUuid());
		if (user == null) {
			return null;
		}
		return userPaymentModeService.saveOrUpdateUserPaymentMode(user, userPaymentModeModalReqModal);
		
	}
	
	public UserPaymentMode activateOrDeActivateUserPaymentMode(String userUuid, PaymentMode paymentMode,  Boolean isActivate) {
		User userAdmin = getLoggedInUserDetails();
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.activateOrDeActivateUserPaymentMode(user, paymentMode, isActivate);
		
	}
	
	public UserPaymentMode getUserPaymentModeDetailsByPaymentMode(String userUuid, PaymentMode paymentMode) {
		User userAdmin = getLoggedInUserDetails();
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.getUserPaymentMode(user, paymentMode);
	}
	
	public List<UserPaymentMode> getUserAllPaymentModeDetails(String userUuid) {
		User userAdmin = getLoggedInUserDetails();
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.getUserAllPaymentMode(user);
	}
		
	
	/// doc for other
	public ResponseEntity<Object> saveOrUpdateUserDoc2(@RequestParam("file") final MultipartFile multiipartFile,
			@RequestParam(required = true, name = "docType") final DocType docType,User user) throws IOException {
		
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
	
	public ResponseEntity<Object> saveOrUpdateUserBusnessKyc2(
			@Valid @RequestBody UserBusinessKycRequestModal userBusunessKycRequestModal,User user) {
		final UserBusinessKyc userBusinessKyc = beanMapper.map(userBusunessKycRequestModal, UserBusinessKyc.class);
		userBusinessKyc.setUserId(user.getUserId());
		ErrorResponse errorResponse = validatePan(user.getUserId(), userBusunessKycRequestModal.getIndividualPan());
		if (errorResponse != null) {
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		Boolean isSaved = userBusnessKycService.saveOrUpdateUserBusnessKyc(beanMapper, userBusinessKyc);
		if (BooleanUtils.isTrue(isSaved)) {
			return ResponseHandler.getMapResponse("message", "data saved");
		}
		errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
				"Please contact support, unable to persist data");
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	public UserBankDetails saveOrUpdateUserBankDetails2(@RequestBody UserBankModal userBankModal,User user) {
		return userservice.saveOrUpdateUserBankDetails(user, userBankModal);
	}
	
	public Boolean userActivateOrDeactivate2(String reason,Boolean flag,User user) {
		user.setDeactivateReason(reason);
		return userservice.userActivateOrDeactivate(user, flag);
	}
	
	
	public Boolean sendOTPForgotPassword(String mobileOrEmail, ForgotPassType forgotPassType) {
		if (forgotPassType == null || mobileOrEmail == null) {
			return false;
		}
		User user = null;
		if (forgotPassType.equals(ForgotPassType.EMAIL)) {
			user  = userservice.findByUserEmail(mobileOrEmail);
			if (user == null || user.getUserEmail() == null || BooleanUtils.isNotTrue(user.getIsActive())) {
				return false;
			}
			
		} else if (forgotPassType.equals(ForgotPassType.PHONE)) {
			user = userservice.findByUserMobileNumber(mobileOrEmail);
			if (user == null || user.getMobileNumber() == null || BooleanUtils.isNotTrue(user.getIsActive())) {
				return false;
			}
			
		}
		return otpService.sendingOtp(user, forgotPassType);
	}
	
	public Boolean matchOtpForgotPassword(String otp) {
		Otp otpDetails = otpService.findByMobileOtpOrEmailOtp(otp, otp);
		return otpDetails != null;
		
	}
	
	public Boolean updatePasswordForgotPassword(String mobileOrEmail, String newPass, String confirmPass) {
		if (mobileOrEmail == null || newPass ==null || confirmPass == null || !newPass.equals(confirmPass)) {
			return false;
		}
		User user = userservice.getUserByUserEmailOrMobileNumber(mobileOrEmail, mobileOrEmail);
		if (user == null) {
			return false;
		}
		return userservice.changeEmailOrPassword(user, null, newPass);
		
	}
	
	public String generateApiKey() {
		User user = getLoggedInUserDetails();
		if (user == null || BooleanUtils.isNotTrue(user.getIsActive()) || BooleanUtils.isNotTrue(user.getIsUserVerified())
				|| !user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return null;
		}
		user.setApiKey(Utility.getUniqueUuid());
		userRepo.save(user);
		String apiKey = user.getApiKey();
		String token = user.getToken();
		return apiKey + "dev_Ankur" +token;
		
	}
	
	public String getGeneratedApiKey() {
		User user = getLoggedInUserDetails();
		String apiKey = user.getApiKey();
		String token = user.getToken();
		
		if (apiKey != null && token !=null) {
			return apiKey + "dev_Ankur" +token;
		}
		return null;
	}
	
	public List<User> getUserByPanAndMarchantId(String pan, String marchantId) {
		User user = getLoggedInUserDetails();
		if (BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		if (pan == null && marchantId == null) {
			return Collections.emptyList();
		}
		List<User> userList = new ArrayList<>();

		if (pan != null) {
			UserBusinessKyc userBusinessKyc = userBusnessKycService.getUserBusnessKycByPan(pan);
			if (userBusinessKyc != null) {
				User userByPan = userservice.findByUserId(userBusinessKyc.getUserId());
				userList.add(userByPan);
			}
		}
		if (marchantId != null) {
			UserWallet userWallet = userWalletService.findByMerchantId(marchantId);
			if (userWallet != null) {
				User userByMarchantId = userservice.findByUserId(userWallet.getUserId());
				userList.add(userByMarchantId);
			}
		}
		return userList;
		
	}
}
