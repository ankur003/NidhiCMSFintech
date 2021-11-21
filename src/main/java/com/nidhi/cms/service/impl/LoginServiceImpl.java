package com.nidhi.cms.service.impl;

import static com.nidhi.cms.constants.JwtConstants.TOKEN_PREFIX;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.repository.UserRepository;
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
	private UserRepository userRepo;

	@Autowired
	private TokenProvider jwtTokenUtil;
	
	@Autowired
	private ApplicationConfig applicationConfig;

	@Override
	public AuthToken login(LoginRequestModal loginRequestModal) {
		final Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequestModal.getUsername(), loginRequestModal.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		final String authToken = jwtTokenUtil.generateToken(authentication);
		String userUuid = ApplicationConfig.loggedInUsers.get(authentication.getName()); // userUuid
		User user = userRepo.findByUserUuid(userUuid);
		user.setToken(TOKEN_PREFIX + authToken);
		userRepo.save(user);
		return new AuthToken(TOKEN_PREFIX + authToken, userUuid, LocalDateTime.now(), applicationConfig.getTokenValiditySeconds());
	}
}
