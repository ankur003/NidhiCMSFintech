package com.nidhi.cms.service.impl;

import static com.nidhi.cms.constants.JwtConstants.TOKEN_PREFIX;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.security.AuthToken;
import com.nidhi.cms.security.TokenProvider;
import com.nidhi.cms.service.LoginService;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Service
public class LoginServiceImpl implements LoginService {
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private TokenProvider jwtTokenUtil;

	@Override
	public String login(LoginRequestModal loginRequestModal) {
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestModal.getUsername(), loginRequestModal.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String authToken = jwtTokenUtil.generateToken(authentication);
		return new AuthToken(TOKEN_PREFIX + authToken).toString();
	}
}
