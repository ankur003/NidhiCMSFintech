package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class UserBusinessKycRequestModal {

	@NotBlank
	private String entityType;

	@NotBlank
	private String industry;

	@NotBlank
	private String compnayName;

	@NotBlank
	private String noOfEmp;

	@NotBlank
	private String individualPan;

	@NotBlank
	private String gstNo;

	@NotBlank
	private String websiteLink;

	@NotBlank
	private String address1;

	@NotBlank
	private String address2;

	@NotBlank
	private String pincode;

	@NotBlank
	private String state;

	@NotBlank
	private String city;
	
	@NotBlank
	private String userUuid;

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

	public String getAddress1() {
		return address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public String getPincode() {
		return pincode;
	}

	public void setPincode(String pincode) {
		this.pincode = pincode;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}
	

}
