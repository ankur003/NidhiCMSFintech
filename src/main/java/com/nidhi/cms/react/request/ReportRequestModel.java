package com.nidhi.cms.react.request;

import javax.validation.constraints.NotBlank;

public class ReportRequestModel {
	
	@NotBlank(message = "mid is missing")
	private String mid ;
	
	@NotBlank(message = "startDate is missing")
	private String startDate;
	
	@NotBlank(message = "endDate is missing")
	private String endDate;

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
}
