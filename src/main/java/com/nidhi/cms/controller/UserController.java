package com.nidhi.cms.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.UserDetailModal;
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
	private OtpService otpService;

	@PostMapping(value = "")
	public ResponseEntity<Object> userSignUp(@Valid @RequestBody UserCreateModal userCreateModal) {
		final User user = beanMapper.map(userCreateModal, User.class);
		User existingUser = userservice.getUserByUserEmailOrMobileNumber(user.getUserEmail(), user.getMobileNumber());
		if (Objects.nonNull(existingUser) && BooleanUtils.isTrue(existingUser.getIsUserVerified())) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "username : user already exist by mobile or email.");
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
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR, "please try again in some time or reach to the support");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_MODIFIED);
		}
		Boolean isCreated = userservice.createUser(user);
		if (BooleanUtils.isFalse(isCreated)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR, "please try again in some time or reach to the support");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_MODIFIED);
		}
		return ResponseHandler.getMapResponse("message", "please verify the email & mobile otp");
	}

	@GetMapping(value = "/{userUuid}")
	@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"),
			@Authorization(value = "oauthToken") })
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

}
