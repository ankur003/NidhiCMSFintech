package com.nidhi.cms.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nidhi.cms.facade.AuthenticationFacade;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Component
public class LoggedInUserUtil {

	@Autowired
	private AuthenticationFacade authenticationFacade;

	public String getLoggedInUserName() {
		return authenticationFacade.getAuthentication().getName();
	}
}
