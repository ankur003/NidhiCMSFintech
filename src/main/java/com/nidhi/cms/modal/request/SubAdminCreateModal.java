package com.nidhi.cms.modal.request;

public class SubAdminCreateModal extends UserCreateModal{

	private String[] privilageNames;

	public String[] getPrivilageNames() {
		return privilageNames;
	}

	public void setPrivilageNames(String[] privilageNames) {
		this.privilageNames = privilageNames;
	}

}
