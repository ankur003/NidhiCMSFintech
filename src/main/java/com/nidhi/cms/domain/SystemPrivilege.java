package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SystemPrivilege extends BaseDomain {

	private static final long serialVersionUID = -4080450940200160144L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long systemPrivilegeId;

	private String privilegeName;

	public Long getSystemPrivilegeId() {
		return systemPrivilegeId;
	}

	public void setSystemPrivilegeId(Long systemPrivilegeId) {
		this.systemPrivilegeId = systemPrivilegeId;
	}

	public String getPrivilegeName() {
		return privilegeName;
	}

	public void setPrivilegeName(String privilegeName) {
		this.privilegeName = privilegeName;
	}

}