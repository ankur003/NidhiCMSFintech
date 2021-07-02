package com.nidhi.cms.controller;

import java.util.Objects;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
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
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.modal.response.UserDetailModal;
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

	@PostMapping(value = "")
	public ResponseEntity<Object> userSignUp(@Valid @RequestBody UserCreateModal userCreateModal) {
		final User user = beanMapper.map(userCreateModal, User.class);
		User existingUser = userservice.getUserByUserName(user.getUsername(), false);
		if(Objects.nonNull(existingUser)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "username : username is already exist.");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		String userUuid = userservice.createUser(user);
		if (StringUtils.isBlank(userUuid)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.GENERIC_SERVER_ERROR, "please try again in some time or reach to the support");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_MODIFIED);
		}
		return ResponseHandler.getMapResponse("userUuid", userUuid);
	}
	
	@GetMapping(value = "/{userUuid}")
	@PreAuthorize("hasRole('USER')")
	@ApiOperation(value = "Get User Detail", authorizations = { @Authorization(value = "accessToken"), @Authorization(value = "oauthToken") })
	public ResponseEntity<Object> getUserDetail(
			@ApiParam(value = "User Unique uiud", required = true) @ValidUuid(message = "userUuid : usreUuid is missing.") @PathVariable("userUuid") final String userUuid) {
		User user = userservice.getUserByUserUuid(userUuid);
		if (Objects.isNull(user)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "userUuid : no data found against requested userUuid");
			return new ResponseEntity<>(errorResponse, HttpStatus.PRECONDITION_FAILED);
		}
		final UserDetailModal userDetailModal = beanMapper.map(user, UserDetailModal.class);
		return ResponseEntity.ok(userDetailModal);
	}

}
