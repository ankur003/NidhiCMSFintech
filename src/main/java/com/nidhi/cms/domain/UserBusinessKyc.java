package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserBusinessKyc extends BaseDomain {

	private static final long serialVersionUID = -7466226900998720249L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userBusinessKycId;

	private Long userId;

	private String entityType;

	private String industry;

	private String compnayName;

	private String noOfEmp;

	private String individualPan;

	private String gstNo;

	private String websiteLink;

	private String address1;

	private String address2;

	private String pincode;

	private String state;

	private String city;

	public Long getUserBusinessKycId() {
		return userBusinessKycId;
	}

	public void setUserBusinessKycId(Long userBusinessKycId) {
		this.userBusinessKycId = userBusinessKycId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

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
}
