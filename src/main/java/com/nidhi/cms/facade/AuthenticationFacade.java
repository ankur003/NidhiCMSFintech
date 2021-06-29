package com.nidhi.cms.facade;

import org.springframework.security.core.Authentication;

public interface AuthenticationFacade {
	Authentication getAuthentication();
}