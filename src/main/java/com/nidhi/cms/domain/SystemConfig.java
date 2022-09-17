package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class SystemConfig extends BaseDomain {

	private static final long serialVersionUID = 8576733359118381807L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long systemConfigId;
	
	private String systemKey;
	
	private String lastListApiStartTime;

	public Long getSystemConfigId() {
		return systemConfigId;
	}

	public void setSystemConfigId(Long systemConfigId) {
		this.systemConfigId = systemConfigId;
	}

	public String getSystemKey() {
		return systemKey;
	}

	public void setSystemKey(String systemKey) {
		this.systemKey = systemKey;
	}

	public String getLastListApiStartTime() {
		return lastListApiStartTime;
	}

	public void setLastListApiStartTime(String lastListApiStartTime) {
		this.lastListApiStartTime = lastListApiStartTime;
	}

}
