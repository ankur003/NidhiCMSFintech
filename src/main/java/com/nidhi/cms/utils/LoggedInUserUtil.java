package com.nidhi.cms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.facade.AuthenticationFacade;
import com.nidhi.cms.service.UserService;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Component
public class LoggedInUserUtil {

	@Autowired
	private AuthenticationFacade authenticationFacade;
	
	@Autowired
	private UserService userService;

	public String getLoggedInUserName() {
		return authenticationFacade.getAuthentication().getName();
	}

	public User getLoggedInUserDetails() {
		String username = getLoggedInUserName();
		return userService.getUserByUserEmailOrMobileNumber(username, username);
	}
}
