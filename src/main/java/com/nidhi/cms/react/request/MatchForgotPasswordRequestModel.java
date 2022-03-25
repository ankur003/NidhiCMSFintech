package com.nidhi.cms.react.request;
import javax.validation.constraints.NotBlank;

public class MatchForgotPasswordRequestModel {

	@NotBlank(message = "otpUuid : otpUuid is blank or missing")
	private String otpUuid;
	
	@NotBlank(message = "otp : otp is blank or missing")
	private String otp;
	
	@NotBlank(message = "newPass : newPass is blank or missing")
	private String newPass;
	
	@NotBlank(message = "confirmPass : confirmPass is blank or missing")
	private String confirmPass;

	public String getOtpUuid() {
		return otpUuid;
	}

	public void setOtpUuid(String otpUuid) {
		this.otpUuid = otpUuid;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getNewPass() {
		return newPass;
	}

	public void setNewPass(String newPass) {
		this.newPass = newPass;
	}

	public String getConfirmPass() {
		return confirmPass;
	}

	public void setConfirmPass(String confirmPass) {
		this.confirmPass = confirmPass;
	}
	
}