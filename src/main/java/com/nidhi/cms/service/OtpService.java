package com.nidhi.cms.service;

import com.nidhi.cms.domain.User;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface OtpService {

	Boolean sendingOtp(User existingUser);

}
