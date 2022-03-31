package com.nidhi.cms.react.request;

import javax.validation.constraints.NotBlank;

public class TriggerOtpModel {
	
	@NotBlank(message = "mobile : mobile is missing")
	private String mobile;
	
	@NotBlank(message = "email : email is missing")
	private String email;

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	

}
