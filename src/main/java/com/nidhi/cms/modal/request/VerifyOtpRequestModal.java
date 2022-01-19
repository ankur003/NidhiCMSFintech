package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class VerifyOtpRequestModal {

	@NotBlank(message = "mobileOtp : mobileOtp is blank")
	@Pattern(regexp = "^[0-9]{6}$", message = "Only numbers allowed with length 6")
	private String mobileOtp;

	@NotBlank(message = "emailOtp : emailOtp is blank")
	@Pattern(regexp = "^[0-9]{6}$", message = "Only numbers allowed with length 6")
	private String emailOtp;

	private String otpUuid;

	public String getMobileOtp() {
		return mobileOtp;
	}

	public void setMobileOtp(String mobileOtp) {
		this.mobileOtp = mobileOtp;
	}

	public String getEmailOtp() {
		return emailOtp;
	}

	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}

	public String getOtpUuid() {
		return otpUuid;
	}

	public void setOtpUuid(String otpUuid) {
		this.otpUuid = otpUuid;
	}

}
