package com.nidhi.cms.modal.request;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class LoginRequestModal {

	private String username;

	private String password;
	
	private String otpflag;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getOtpflag() {
		return otpflag;
	}

	public void setOtpflag(String otpflag) {
		this.otpflag = otpflag;
	}

}
