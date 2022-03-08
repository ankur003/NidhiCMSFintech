package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UpiRegistrationDetail extends BaseDomain {

	private static final long serialVersionUID = -8406783419459936928L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long upiRegistrationDetailId;
	
	private String mebussname; 
	
	private String pgMerchantID; 
	
	private String statusDesc; 
	
	private String integrationType; 
	
	private String merVirtualAdd; 
	
	private String merchantKey; 
	
	private String legalStrName; 
	
	private String  extMID;
	
	private String  extTID; 
	
	private String meEmailID; 
	
	private String accNO;

	private Long userId;
	
	private String crtDate;
	
	public Long getUpiRegistrationDetailId() {
		return upiRegistrationDetailId;
	}

	public void setUpiRegistrationDetailId(Long upiRegistrationDetailId) {
		this.upiRegistrationDetailId = upiRegistrationDetailId;
	}

	public String getMebussname() {
		return mebussname;
	}

	public void setMebussname(String mebussname) {
		this.mebussname = mebussname;
	}

	public String getPgMerchantID() {
		return pgMerchantID;
	}

	public void setPgMerchantID(String pgMerchantID) {
		this.pgMerchantID = pgMerchantID;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getIntegrationType() {
		return integrationType;
	}

	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}

	public String getMerVirtualAdd() {
		return merVirtualAdd;
	}

	public void setMerVirtualAdd(String merVirtualAdd) {
		this.merVirtualAdd = merVirtualAdd;
	}

	public String getMerchantKey() {
		return merchantKey;
	}

	public void setMerchantKey(String merchantKey) {
		this.merchantKey = merchantKey;
	}

	public String getLegalStrName() {
		return legalStrName;
	}

	public void setLegalStrName(String legalStrName) {
		this.legalStrName = legalStrName;
	}

	public String getExtMID() {
		return extMID;
	}

	public void setExtMID(String extMID) {
		this.extMID = extMID;
	}

	public String getExtTID() {
		return extTID;
	}

	public void setExtTID(String extTID) {
		this.extTID = extTID;
	}

	public String getMeEmailID() {
		return meEmailID;
	}

	public void setMeEmailID(String meEmailID) {
		this.meEmailID = meEmailID;
	}

	public String getAccNO() {
		return accNO;
	}

	public void setAccNO(String accNO) {
		this.accNO = accNO;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCrtDate() {
		return crtDate;
	}

	public void setCrtDate(String crtDate) {
		this.crtDate = crtDate;
	} 
	
}
