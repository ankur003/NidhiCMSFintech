package com.nidhi.cms.modal.request;


import java.util.List;


public class SubAdminCreateModal extends UserCreateModal {

	private List<String> privilageNames;

	public List<String> getPrivilageNames() {
		return privilageNames;
	}

	public void setPrivilageNames(List<String> privilageNames) {
		this.privilageNames = privilageNames;
	}


	
}
