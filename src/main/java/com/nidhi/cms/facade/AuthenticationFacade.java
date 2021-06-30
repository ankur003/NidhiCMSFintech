package com.nidhi.cms.facade;

import org.springframework.security.core.Authentication;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface AuthenticationFacade {
	Authentication getAuthentication();
}