package com.nidhi.cms.facade;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Service
public class AuthenticationFacadeImpl implements AuthenticationFacade {

	@Override
	public Authentication getAuthentication() {
		return SecurityContextHolder.getContext().getAuthentication();
	}
}
