package com.nidhi.cms.react.request;

public class UserActivateOrDeActivateReqModel {

	private String reason;

	private Boolean isActivate;

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Boolean getIsActivate() {
		return isActivate;
	}

	public void setIsActivate(Boolean isActivate) {
		this.isActivate = isActivate;
	}

}
