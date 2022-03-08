package com.nidhi.cms.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nidhi.cms.constants.ApiConstants;
import com.nidhi.cms.constants.SwaggerConstant;
import com.nidhi.cms.modal.request.LoginModal;
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
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping(value = ApiConstants.API_VERSION + "/login")
public class LoginController extends AbstractController {

	@Autowired
	private LoginService loginservice;
	
	public String login(@Valid @RequestBody LoginRequestModal loginRequestModal) {
		AuthToken authToken = loginservice.login(loginRequestModal);
		return authToken.getToken();
	}
	
	@PostMapping(value = "/client")
	public AuthToken loginClient(@Valid @RequestBody LoginModal loginModal) {
		LoginRequestModal loginRequestModal = new LoginRequestModal();
		loginRequestModal.setUsername(loginModal.getEmail());
		loginRequestModal.setPassword(loginModal.getPassword());
		return loginservice.login(loginRequestModal);
	}

}
