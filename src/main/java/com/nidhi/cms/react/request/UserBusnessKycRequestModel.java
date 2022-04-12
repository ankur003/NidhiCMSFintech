package com.nidhi.cms.react.request;

public class UserBusnessKycRequestModel {
	
	private String entityType;
	
	private String industry;

	private String compnayName;

	private String noOfEmp;

	private String individualPan;

	private String gstNo;

	private String websiteLink;

	public String getEntityType() {
		return entityType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getIndustry() {
		return industry;
	}

	public void setIndustry(String industry) {
		this.industry = industry;
	}

	public String getCompnayName() {
		return compnayName;
	}

	public void setCompnayName(String compnayName) {
		this.compnayName = compnayName;
	}

	public String getNoOfEmp() {
		return noOfEmp;
	}

	public void setNoOfEmp(String noOfEmp) {
		this.noOfEmp = noOfEmp;
	}

	public String getIndividualPan() {
		return individualPan;
	}

	public void setIndividualPan(String individualPan) {
		this.individualPan = individualPan;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public String getWebsiteLink() {
		return websiteLink;
	}

	public void setWebsiteLink(String websiteLink) {
		this.websiteLink = websiteLink;
	}
	

}
