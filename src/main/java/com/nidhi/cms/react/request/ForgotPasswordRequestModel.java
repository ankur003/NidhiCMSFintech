package com.nidhi.cms.react.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.nidhi.cms.constants.enums.ForgotPassType;

public class ForgotPasswordRequestModel {

	@NotBlank(message = "username : username is blank")
	private String username;

	@NotNull(message = "forgotPassType : forgotPassType is blank")
	private ForgotPassType forgotPassType;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public ForgotPassType getForgotPassType() {
		return forgotPassType;
	}

	public void setForgotPassType(ForgotPassType forgotPassType) {
		this.forgotPassType = forgotPassType;
	}

}
