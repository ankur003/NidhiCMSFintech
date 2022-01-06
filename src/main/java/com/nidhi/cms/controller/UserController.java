package com.nidhi.cms.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
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

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.constants.enums.ForgotPassType;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.IndsIndRequestModal;
import com.nidhi.cms.modal.request.NEFTIncrementalStatusReqModal;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.TxStatusInquiry;
import com.nidhi.cms.modal.request.UserAccountActivateModal;
import com.nidhi.cms.modal.request.UserAllocateFundModal;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
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
import com.nidhi.cms.service.TransactionService;
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
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private ApplicationConfig applicationConfig;
	
	OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(5, TimeUnit.MINUTES).build();
	MediaType mediaType = MediaType.parse("application/json");
	
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

	public User getUserByEmailOrMobile(
			@RequestParam(required = true, name = "emailOrMobile") final String emailOrMobile) {
		return userservice.getUserByUserEmailOrMobileNumber(emailOrMobile, emailOrMobile);
	}

//	@GetMapping(value = "")
	// @PreAuthorize("hasRole('ADMIN')")
//	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") }, hidden = true)
	public Map<String, Object> getAllUser(@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		if(BooleanUtils.isTrue(userservice.getUserByUserUuid(userRequestFilterModel.getUserUuid()).getIsAdmin())) {
			Page<User> users = userservice.getAllUsers(userRequestFilterModel);
			if (users == null || CollectionUtils.isEmpty(users.getContent())) {
				return null;
			}
			return ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
		}
		return null;
	}

//	@PutMapping(value = "/doc")
//	// @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") }, hidden = true)
	public ResponseEntity<Object> saveOrUpdateUserDoc(@RequestParam("file") final MultipartFile multiipartFile,
			@RequestParam(required = true, name = "docType") final DocType docType, @RequestParam("userUuid") final String userUuid) throws IOException {
		User user = userservice.getUserByUserUuid(userUuid);
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
	public UserDoc getUserDoc(@RequestParam(required = true, name = "docType") final DocType docType, final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
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
		User user = userservice.getUserByUserUuid(userBusunessKycRequestModal.getUserUuid());
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
	public UserBusinessKycModal getUserBusnessKyc(final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
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
	public List<UserDoc> getUserAllKyc(final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		return docService.getUserAllKyc(user.getUserId());
	}

	// @PutMapping(value = "/change-email-password")
	//@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "change Email Or Password", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public String changeEmailOrPassword(@RequestParam(name = "email", required = false) String emailToChange,
			@RequestParam(name = "password", required = false) String passwordToChange, final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
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
		return userservice.approveOrDisApproveKyc(user, kycResponse, docType, kycRejectReason);
	}

//	@GetMapping(value = "/get-user-account-statement")
//	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
//	@ApiOperation(value = "get-user-account-statement", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public List<UserAccountStatement> getUserAccountStatementService(@RequestParam("fromDate") String fromDate,
			@RequestParam("toDate") String toDate, @RequestParam("userUuid") final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
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
	public UserWallet getUserWallet(String userUuid) {
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

//	@PutMapping(value = "/user-bank-account")
//	@PreAuthorize("hasAnyRole('ADMIN')")
//	@ApiOperation(value = "save or update user bank details", authorizations = { @Authorization(value = "accessToken"),
//			@Authorization(value = "oauthToken") })
	public UserBankDetails saveOrUpdateUserBankDetails(@RequestBody UserBankModal userBankModal) {
		User user = userservice.getUserByUserUuid(userBankModal.getUserUuid());
		UserBankDetails response = userservice.saveOrUpdateUserBankDetails(user, userBankModal);
		if (user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return response;
		}
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
		String authorizationToken = httpServletRequest.getHeader("Authorization");
		if (StringUtils.isBlank(apiKey)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "apiKey is reuired - please provide apiKey in header");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		
		User user = userservice.findByApiKey(apiKey);
		if (user == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "invalid apiKey - please contact admin");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (authorizationToken == null || !user.getToken().equals(authorizationToken)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "authorization Token is invalid");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (BooleanUtils.isNotTrue(user.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over de-activated account");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		boolean isValid = getRemoteIpAddress(user, httpServletRequest);
		if (!isValid) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over ip");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		}
		
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created");
			errorResponse.addError("errorCode", "" + ErrorCode.ENTITY_NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
		}
		
		if (userWallet.getMerchantId() == null || !userWallet.getMerchantId().equals(userTxWoOtpReqModal.getMerchantId())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "merchantId not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		if (StringUtils.isBlank(userTxWoOtpReqModal.getTxntype())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "txntype is missing.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		
		PaymentMode txType = EnumUtils.getEnum(PaymentMode.class, userTxWoOtpReqModal.getTxntype());
		
		if (txType == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "txntype is in-valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		
		UserPaymentMode userPaymentMode = userPaymentModeService.getUserPaymentMode(user, txType);
		if (userPaymentMode == null || BooleanUtils.isNotTrue(userPaymentMode.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "permission not granted by admin for txntype - " +userTxWoOtpReqModal.getTxntype());
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		BigDecimal userTxWoAmount = new BigDecimal(userTxWoOtpReqModal.getAmount()).setScale(2, RoundingMode.HALF_DOWN);
		if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.PERCENTAGE)) {
			if ((userPaymentMode.getFee() == null)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
			}
		if ((userWallet.getAmount()) < (getFee(userPaymentMode.getFee(), userTxWoOtpReqModal.getAmount())) + userTxWoAmount.doubleValue()) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
			}
		} else if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.FLAT)) {
			if (userPaymentMode.getFee() == null) {
				final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance");
				errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
	            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
				}
			if ((userWallet.getAmount()) < (userTxWoAmount.doubleValue() + userPaymentMode.getFee())) {
				final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "low balance.");
				errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
	            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
				}
		}
		userTxWoOtpReqModal.setAmount(userTxWoAmount.doubleValue());
		setFeeRelatedInfo(userPaymentMode, userTxWoOtpReqModal);
		return userservice.txWithoutOTP(user, userTxWoOtpReqModal, userWallet);
	}
	
	private void setFeeRelatedInfo(UserPaymentMode userPaymentMode, UserTxWoOtpReqModal userTxWoOtpReqModal) {
		userTxWoOtpReqModal.setFeeType(userPaymentMode.getPaymentModeFeeType().name());
		if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.PERCENTAGE)) {
			Double fee = getFee(userPaymentMode.getFee(), userTxWoOtpReqModal.getAmount());
			userTxWoOtpReqModal.setFee(fee);
		} else if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.FLAT)) {
			BigDecimal fee = new BigDecimal(userPaymentMode.getFee()).setScale(2, RoundingMode.HALF_DOWN);
			userTxWoOtpReqModal.setFee(fee.doubleValue());
		}
	}
		
	private Double getFee(Double feePercent, Double amount) {
		BigDecimal amt = new BigDecimal(amount).setScale(2, RoundingMode.HALF_DOWN);
		BigDecimal feePer = new BigDecimal(feePercent).setScale(2, RoundingMode.HALF_DOWN);
		return new BigDecimal((amt.doubleValue() * feePer.doubleValue()) / 100).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
	}

	@PostMapping(value = "/transaction/inquiry")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "transaction inquiry", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txStatusInquiry(@Valid @RequestBody TxStatusInquiry txStatusInquiry, final HttpServletRequest httpServletRequest) {

		String apiKey = httpServletRequest.getHeader("apiKey");
		String authorizationToken = httpServletRequest.getHeader("Authorization");
		if (StringUtils.isBlank(apiKey)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "apiKey is reuired - please provide apiKey in header");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		
		User user = userservice.findByApiKey(apiKey);
		if (user == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "invalid apiKey - please contact admin");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (authorizationToken == null || !user.getToken().equals(authorizationToken)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "authorization Token is invalid");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (BooleanUtils.isNotTrue(user.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over de-activated account");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		boolean isValid = getRemoteIpAddress(user, httpServletRequest);
		if (!isValid) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over ip");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		}
		
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created");
			errorResponse.addError("errorCode", "" + ErrorCode.ENTITY_NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
		}
		
		if (userWallet.getMerchantId() == null || !userWallet.getMerchantId().equals(txStatusInquiry.getMerchantId())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "merchantId not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		List<Transaction> transactions = transactionService.findByUserIdAndUniqueId(user.getUserId(), txStatusInquiry.getUniqueid());
		if (CollectionUtils.isEmpty(transactions)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "unique id not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		return userservice.txStatusInquiry(user, txStatusInquiry);
	}

	
	@PostMapping(value = "/transaction/NEFT-status")
	@PreAuthorize("hasAnyRole('ADMIN', 'USER')")
	@ApiOperation(value = "NEFT Incremental status API", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public Object txNEFTStatus(@Valid @RequestBody NEFTIncrementalStatusReqModal nEFTIncrementalStatusReqModal, final HttpServletRequest httpServletRequest) {
		String apiKey = httpServletRequest.getHeader("apiKey");
		String authorizationToken = httpServletRequest.getHeader("Authorization");
		if (StringUtils.isBlank(apiKey)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "apiKey is reuired - please provide apiKey in header");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}
		
		User user = userservice.findByApiKey(apiKey);
		if (user == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "invalid apiKey - please contact admin");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (authorizationToken == null || !user.getToken().equals(authorizationToken)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "authorization Token is invalid");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		
		if (BooleanUtils.isNotTrue(user.getIsActive())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over de-activated account");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponse);
		}
		boolean isValid = getRemoteIpAddress(user, httpServletRequest);
		if (!isValid) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.AUTHENTICATION_REQUIRED, "access denied over ip");
			errorResponse.addError("errorCode", "" + ErrorCode.AUTHENTICATION_REQUIRED.value());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorResponse);
		}
		
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.ENTITY_NOT_FOUND, "user wallet not created");
			errorResponse.addError("errorCode", "" + ErrorCode.ENTITY_NOT_FOUND.value());
            return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(errorResponse);
		}
		
		if (userWallet.getMerchantId() == null || !userWallet.getMerchantId().equals(nEFTIncrementalStatusReqModal.getMerchantId())) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "merchantId not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
		List<Transaction> transactions = transactionService.findByUserIdAndUniqueId(user.getUserId(), nEFTIncrementalStatusReqModal.getUtrnumber());
		if (CollectionUtils.isEmpty(transactions)) {
			final ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "Utrnumber not valid.");
			errorResponse.addError("errorCode", "" + ErrorCode.PARAMETER_MISSING_INVALID.value());
            return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
		}
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
		User user = userservice.getUserByUserUuid(userUpdateModal.getUserUuid());
		return userservice.updateUserDetails(user, userUpdateModal);
	}
	
	public User updateSubadminuser(User user,UserUpdateModal userUpdateModal)  {
		return userservice.updateUserDetails(user, userUpdateModal);
	}
	
	public SystemPrivilege addAccessPrivilegesIntoSystem(String privilegeName, String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.addAccessPrivilegesIntoSystem(privilegeName);
		}
		return null;
	}
	
	public SystemPrivilege updateAccessPrivilegesIntoSystem(String oldPrivilegeName, String newPrivilegeName, String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.updateAccessPrivilegesIntoSystem(oldPrivilegeName, newPrivilegeName);
		}
		return null;
	}
	public SystemPrivilege getPriviligebyid(Long privilegeId) {
			return userservice.findbyIdprivilege(privilegeId);
	}
	
	
	public SystemPrivilege deleteAccessPrivilegesIntoSystem(String privilegeName, String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.deleteAccessPrivilegesIntoSystem(privilegeName);
		}
		return null;
	}
	
	public User createSubAdmin(SubAdminCreateModal subAdminCreateModal, String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (CollectionUtils.isEmpty(subAdminCreateModal.getPrivilageNames())) {
			return null;
		}
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.createSubAdmin(subAdminCreateModal);
		}
		return null;
	}
	
	public List<User> getSubAdminList(String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (BooleanUtils.isTrue(user.getIsAdmin())) {
			return userservice.getSubAdminList();
		}
		return Collections.emptyList();
	}
	
	public List<SystemPrivilege> getSystemPrivlegeList(String userUuid) {
		User user = userservice.getUserDetailByUserUuid(userUuid);
		if (BooleanUtils.isTrue(user.getIsAdmin()) || BooleanUtils.isTrue(user.getIsSubAdmin())) {
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
		User userAdmin = userservice.getUserByUserUuid(userPaymentModeModalReqModal.getAdminUuid());
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userPaymentModeModalReqModal.getUserUuid());
		if (user == null) {
			return null;
		}
		return userPaymentModeService.saveOrUpdateUserPaymentMode(user, userPaymentModeModalReqModal);
		
	}
	
	public UserPaymentMode activateOrDeActivateUserPaymentMode(String userUuid, String adminUuid, PaymentMode paymentMode,  Boolean isActivate) {
		User userAdmin = userservice.getUserByUserUuid(adminUuid);
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.activateOrDeActivateUserPaymentMode(user, paymentMode, isActivate);
		
	}
	
	public UserPaymentMode getUserPaymentModeDetailsByPaymentMode(String userUuid, String adminUuid, PaymentMode paymentMode) {
		User userAdmin = userservice.getUserByUserUuid(adminUuid);
		if (BooleanUtils.isNotTrue(userAdmin.getIsAdmin())) {
			return null;
		}
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return null;
		}
		return userPaymentModeService.getUserPaymentMode(user, paymentMode);
	}
	
	public List<UserPaymentMode> getUserAllPaymentModeDetails(String adminUuid, String userUuid) {
		User userAdmin = userservice.getUserByUserUuid(adminUuid);
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
	
	public String generateApiKey(String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null 
				|| BooleanUtils.isNotTrue(user.getIsActive())
				|| BooleanUtils.isNotTrue(user.getIsUserVerified())
				|| !user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return null;
		}
		if (user.getApiKey() == null) {
			user.setApiKey(Utility.getUniqueUuid());
			userRepo.save(user);
		}

		String apiKey = user.getApiKey();
		String token = user.getToken();
		return apiKey + "dev_Ankur" + token;

	}
	
	public String getGeneratedApiKey(String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		String apiKey = user.getApiKey();
		String token = user.getToken();
		
		if (apiKey != null && token !=null) {
			return apiKey + "dev_Ankur" +token;
		}
		return null;
	}
	
	public List<Object> getUserByPanAndMarchantId(String pan, String marchantId, String adminUuid) {
		User user = userservice.getUserByUserUuid(adminUuid);
		if (BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		if (StringUtils.isNotBlank(pan) && StringUtils.isNotBlank(marchantId)) {
			return Collections.emptyList();
		}
		List<Object> userList = new ArrayList<>();

		if (StringUtils.isNotBlank(pan)) {
			UserBusinessKyc userBusinessKyc = userBusnessKycService.getUserBusnessKycByPan(pan);
			if (userBusinessKyc != null) {
				User userByPan = userservice.findByUserId(userBusinessKyc.getUserId());
				userList.add(userByPan);
			} else {
				return Collections.emptyList();
			}
		}
		if (StringUtils.isNotBlank(marchantId)) {
			UserWallet userWallet = userWalletService.findByMerchantId(marchantId);
			if (userWallet != null) {
				User userByMarchantId = userservice.findByUserId(userWallet.getUserId());
				userList.add(userByMarchantId);
			} else {
				return Collections.emptyList();
			}
		}
		return userList;
		
	}

	public List<Object> getUserByUserEmailAndContactNumber(String userEmail, String contactNumber, String adminUuid) {
		User user = userservice.getUserByUserUuid(adminUuid);
		if (BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		if (StringUtils.isNotBlank(userEmail) && StringUtils.isNotBlank(contactNumber)) {
			return Collections.emptyList();
		}
		List<Object> userList = new ArrayList<>();

		if (StringUtils.isNotBlank(userEmail)) {
			User userByEmail = userservice.findByUserEmail(userEmail);
			if (userByEmail != null) {
				userList.add(userByEmail);
			} else {
				return Collections.emptyList();
			}
		}
		if (StringUtils.isNotBlank(contactNumber)) {
			User userByContact = userservice.findByUserMobileNumber(contactNumber);
			if (userByContact != null) {
				userList.add(userByContact);
			} else {
				return Collections.emptyList();
			}

		}
		return userList;
	}

	public List<User> getAllUsers(String adminUuid) {
		User user = userservice.getUserByUserUuid(adminUuid);
		if (BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		return userservice.getAllUsers();
	}

	public User getUserDetail(String userUuid) {
		return userservice.getUserByUserUuid(userUuid);
	}
	
	public List<Transaction> getUserTransactions(String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return Collections.emptyList();
		}
		return transactionService.getUserTransactions(user.getUserId());
	}
	
	public List<Transaction> getUserTransactionsByUniqueId(String userUuid, String uniqueId) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return Collections.emptyList();
		}
		return transactionService.getUserTransactionsByUniqueId(user.getUserId(), uniqueId);
	}
	
	public List<Transaction> getUserTransactionsByDates(String userUuid, LocalDate startDate, LocalDate endDate) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return Collections.emptyList();
		}
		return transactionService.getUserTransactionsByDates(user.getUserId(), startDate, endDate);
	}
	
	public List<Transaction> getUserTransactionsBytoadyDate(String userUuid, LocalDate date) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return Collections.emptyList();
		}
		return transactionService.getUserTransactionsBytoadyDate(user.getUserId(), date);
	}
	
	public List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(String userUuid, String uniqueId, LocalDate startDate, LocalDate endDate) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (user == null) {
			return Collections.emptyList();
		}
		return transactionService.findByUserIdAndUniqueIdAndTxDateBetween(user.getUserId(), uniqueId, startDate, endDate);
	}
	
	public List<Transaction> findByMerchantIdAndTxDateBetween(String marchantId, LocalDate startDate, LocalDate endDate) {
		return transactionService.findByMerchantIdAndTxDateBetween(marchantId, startDate, endDate);
	}
	
	public List<String> getUserNameByMarchantId(String marchantId) {
		return userWalletService.getUserNameByMarchantId(marchantId);
	}
	
	public List<Transaction> getAllTransactionsByDates(String adminuserUuid, LocalDate startDate, LocalDate endDate) {
		User user = userservice.getUserByUserUuid(adminuserUuid);
		if (user == null || BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		return transactionService.getAllTransactionsByDates(startDate, endDate);
	}
	
	public List<Transaction> getTransactionsByUniqueId(String adminuserUuid, String uniqueId) {
		User user = userservice.getUserByUserUuid(adminuserUuid);
		if (user == null || BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		return transactionService.getTransactionsByUniqueId(uniqueId);
	}
	
	public Object getTransactionStatus(String adminuserUuid, String uniqueIdOrUtrNumber, PaymentMode paymentMode) {
		User user = userservice.getUserByUserUuid(adminuserUuid);
		if (user == null || BooleanUtils.isNotTrue(user.getIsAdmin())) {
			return Collections.emptyList();
		}
		return transactionService.getTransactionStatus(uniqueIdOrUtrNumber, paymentMode);
	}
	

	@PostMapping(value = "/indsind/onBoardSubMerchant")
	public ResponseEntity<Object> indsind(@Valid @RequestBody IndsIndRequestModal indsIndRequestModal,
			final HttpServletRequest httpServletRequest) {
		try {
			
			okhttp3.RequestBody body = okhttp3.RequestBody.create(Utility.getEncyptedReqBody(indsIndRequestModal, applicationConfig.getIndBankKey()), mediaType);
			Request request = new Request.Builder()
					.url("https://ibluatapig.indusind.com/app/uat/web/onBoardSubMerchant").method("POST", body)
					.addHeader("X-IBM-Client-Id", httpServletRequest.getHeader("X-IBM-Client-Id"))
					.addHeader("X-IBM-Client-Secret", httpServletRequest.getHeader("X-IBM-Client-Secret"))
					.addHeader("Accept", "application/json")
					.addHeader("Content-Type", "application/json").build();
			Response response = client.newCall(request).execute();
			String responseBody = response.body().string();
			String decryptedResponse = Utility.decryptResponse(responseBody, "resp", applicationConfig.getIndBankKey());
			if (decryptedResponse == null) {
				return ResponseEntity.ok(responseBody);
			}
			return ResponseEntity.ok(decryptedResponse);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

	}

}
