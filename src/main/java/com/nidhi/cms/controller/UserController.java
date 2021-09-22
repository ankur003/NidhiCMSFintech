package com.nidhi.cms.controller;

import java.io.IOException;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.modal.request.UserBusinessKycRequestModal;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.UserDetailModal;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserBusnessKycService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

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
	private UserBusnessKycService userBusnessKycService;

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
		Boolean isCreated = userservice.createUser(user);
		if (BooleanUtils.isFalse(isCreated)) {
			return "please try again in some time or reach to the support";
		}
		return "please verify the email & mobile otp";
	}

	@GetMapping(value = "/user")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getUserDetail(){
		User user = getLoggedInUserDetails();
		final UserDetailModal userDetailModal = beanMapper.map(user, UserDetailModal.class);
		return ResponseHandler.getContentResponse(userDetailModal);
	}

	@GetMapping(value = "")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getAllUser(
			@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		Page<User> users = userservice.getAllUsers(userRequestFilterModel);
		if (users == null || CollectionUtils.isEmpty(users.getContent())) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
	}

	@PutMapping(value = "/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public ResponseEntity<Object> saveOrUpdateUserDoc(
			@RequestParam("file") final MultipartFile multiipartFile,
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

	@GetMapping(value = "/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getUserDoc(
			@RequestParam(required = true, name = "docType") final DocType docType) {
		User user = getLoggedInUserDetails();
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		if (doc == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
				.body(new ByteArrayResource(doc.getData()));
	}
	
	
	@PutMapping(value = "/business-kyc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public ResponseEntity<Object> saveOrUpdateUserBusnessKyc(@Valid  @RequestBody UserBusinessKycRequestModal userBusunessKycRequestModal) {
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
	
	@PutMapping(value = "/change-email-password")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
	public String changeEmailOrPassword(@RequestParam(name = "email", required = false) String emailToChange, @RequestParam(name = "password", required = false) String passwordToChange) {
		User user = getLoggedInUserDetails();
		if (StringUtils.isBlank(emailToChange) && StringUtils.isBlank(passwordToChange)) { 
			return "please provide either email or password";
		}
		Boolean isChanged = userservice.changeEmailOrPassword(user, emailToChange, passwordToChange);
		return Boolean.TRUE.equals(isChanged) ? "Email Or Password changed" : "Provided email is taken by someone, Please provide unique email";
	}
	
}
