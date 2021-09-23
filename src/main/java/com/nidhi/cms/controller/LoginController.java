package com.nidhi.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.security.AuthToken;
import com.nidhi.cms.service.LoginService;

import io.swagger.annotations.Api;

/**
 * 
 *
 * @author Ankur Bansala
 */
@Api(tags = { SwaggerConstant.ApiTag.LOGIN })
@RestController
//@RequestMapping(value = ApiConstants.API_VERSION + "/login")
public class LoginController extends AbstractController {

	@Autowired
	private LoginService loginservice;

	//@PostMapping(value = "")
	public String login(@Valid @RequestBody LoginRequestModal loginRequestModal) {
		AuthToken authToken = loginservice.login(loginRequestModal);
		//return ResponseHandler.getContentResponse(authToken);
		return authToken.getToken();
	}

}
