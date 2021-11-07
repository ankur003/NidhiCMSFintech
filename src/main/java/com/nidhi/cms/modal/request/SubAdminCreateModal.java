package com.nidhi.cms.modal.request;

import java.util.Arrays;

public class SubAdminCreateModal extends UserCreateModal{

	private String[] privilageNames;

	public String[] getPrivilageNames() {
		return privilageNames;
	}

	public void setPrivilageNames(String[] privilageNames) {
		this.privilageNames = privilageNames;
	}

	@Override
	public String toString() {
		return "SubAdminCreateModal [privilageNames=" + Arrays.toString(privilageNames) + "]";
	}

	
}
