package com.nidhi.cms.controller;

import java.io.IOException;
import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.UserDetailModal;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;
import com.nidhi.cms.utils.ValidUuid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;

/**
 * @author Devendra Gread
 *
 */
@Api(tags = { SwaggerConstant.ApiTag.USER })
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class UserController extends AbstractController {

	@Autowired
	private UserService userservice;

	@Autowired
	private DocService docService;

	@Autowired
	private OtpService otpService;

	@PostMapping(value = "")
	public ResponseEntity<Object> userSignUp(@Valid @RequestBody UserCreateModal userCreateModal) {
		final User user = beanMapper.map(userCreateModal, User.class);
		User existingUser = userservice.getUserByUserEmailOrMobileNumber(user.getUserEmail(), user.getMobileNumber());
		if (Objects.nonNull(existingUser) && BooleanUtils.isTrue(existingUser.getIsUserVerified())) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID,
					"username : user already exist by mobile or email.");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		// user has not filled the OTP yet and trying again for signUp
		// in that case - user is available in our system but not veryfied
		// So, we are triggering again OTP to mobile & email
		if (Objects.nonNull(existingUser) && BooleanUtils.isFalse(existingUser.getIsUserVerified())) {
			Boolean isOtpSent = otpService.sendingOtp(existingUser);
			if (BooleanUtils.isTrue(isOtpSent)) {
				return ResponseHandler.getMapResponse("message", "Otp-Resent, please verify the email & mobile otp");
			}
			if (isOtpSent == null) {
				ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID,
						"Otp-already sent, please verify the email & mobile otp."
								+ "if you have lost the OTP , please try again in 30 min");
				return ResponseEntity.status(HttpStatus.PRECONDITION_FAILED).body(errorResponse);
			}
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
					"please try again in some time or reach to the support");
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(errorResponse);
		}
		Boolean isCreated = userservice.createUser(user);
		if (BooleanUtils.isFalse(isCreated)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR,
					"please try again in some time or reach to the support");
			return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(errorResponse);
		}
		return ResponseHandler.getMapResponse("message", "please verify the email & mobile otp");
	}

	@GetMapping(value = "/{userUuid}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"), @Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getUserDetail(
			@ApiParam(value = "User Unique uiud", required = true) @ValidUuid(message = "userUuid : usreUuid is missing.") @PathVariable("userUuid") final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (Objects.isNull(user)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID,
					"userUuid : no data found against requested userUuid");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		final UserDetailModal userDetailModal = beanMapper.map(user, UserDetailModal.class);
		return ResponseHandler.getContentResponse(userDetailModal);
	}

	@GetMapping(value = "")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "Get All Users", authorizations = { @Authorization(value = "accessToken"), @Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getAllUser(
			@Valid @ModelAttribute final UserRequestFilterModel userRequestFilterModel) {
		Page<User> users = userservice.getAllUsers(userRequestFilterModel);
		if (users == null || CollectionUtils.isEmpty(users.getContent())) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseHandler.getpaginationResponse(beanMapper, users, UserDetailModal.class);
	}

	@PutMapping(value = "/{userUuid}/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "save or update user doc", authorizations = { @Authorization(value = "accessToken"), @Authorization(value = "oauthToken") })
	public ResponseEntity<Object> saveOrUpdateUserDoc(
			@ApiParam(value = "User Unique uiud", required = true) @ValidUuid(message = "userUuid : usreUuid is missing.") @PathVariable("userUuid") final String userUuid,
			@RequestParam("file") final MultipartFile multiipartFile,
			@RequestParam(required = true, name = "docType") final DocType docType) throws IOException {
		User user = userservice.getUserByUserUuid(userUuid);
		if (Objects.isNull(user)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID,
					"userUuid : no data found against requested userUuid");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
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

	@GetMapping(value = "/{userUuid}/doc")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "get user doc", authorizations = { @Authorization(value = "accessToken"), @Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getUserDoc(
			@ApiParam(value = "User Unique uiud", required = true) @ValidUuid(message = "userUuid : usreUuid is missing.") @PathVariable("userUuid") final String userUuid,
			@RequestParam(required = true, name = "docType") final DocType docType) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (Objects.isNull(user)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "userUuid : no data found against requested userUuid");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		UserDoc doc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		if (doc == null) {
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
		return ResponseEntity.ok().contentType(MediaType.parseMediaType(doc.getContentType()))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + doc.getFileName() + "\"")
				.body(new ByteArrayResource(doc.getData()));
	}
}
