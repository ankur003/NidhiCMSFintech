package com.nidhi.cms.service;

import com.nidhi.cms.modal.request.LoginRequestModal;
import com.nidhi.cms.security.AuthToken;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface LoginService {

	AuthToken login(LoginRequestModal loginRequestModal);

}
