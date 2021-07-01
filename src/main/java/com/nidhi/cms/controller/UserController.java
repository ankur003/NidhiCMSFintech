package com.nidhi.cms.controller;

import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.enums.ErrorCode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.UserCreateModal;
import com.nidhi.cms.modal.response.ErrorResponse;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.ResponseHandler;

/**
 * @author Devendra Gread
 *
 */
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/user")
public class UserController extends AbstractController {
	
	@Autowired
	private UserService userservice;

	@PostMapping(value = "")
	public ResponseEntity<Object> createUser(@Valid @RequestBody UserCreateModal userCreateModal) {
		final User user = beanMapper.map(userCreateModal, User.class);
		String userUuid = userservice.createUser(user);
		if (StringUtils.isBlank(userUuid)) {
			ErrorResponse errorResponse = new ErrorResponse(ErrorCode.PARAMETER_MISSING_INVALID, "please try again in some time or reach to the support");
			return new ResponseEntity<>(errorResponse, HttpStatus.NOT_MODIFIED);
		}
		return ResponseHandler.getMapResponse("userUuid", userUuid);
	}

}
