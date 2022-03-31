package com.nidhi.cms.modal.response;

import com.nidhi.cms.domain.SystemPrivilege;

public class SystemPrivilegesModel {
	
	private String privilegeName;
	
	public SystemPrivilegesModel() {
		
	}

	public SystemPrivilegesModel(String privilegeName) {
		super();
		this.privilegeName = privilegeName;
	}
	
	public SystemPrivilegesModel(SystemPrivilege systemPrivilege) {
		this.privilegeName = systemPrivilege.getPrivilegeName();
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}
	

}
