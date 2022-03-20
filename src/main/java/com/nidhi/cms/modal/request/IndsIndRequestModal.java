package com.nidhi.cms.modal.request;

public class IndsIndRequestModal {

	private String pgMerchantId;

	private String mebussname; // companyName

	private String legalStrName; //// companyName

	private String merVirtualAdd; // 

	private String awlmcc; // dropdown on ui

	private String strCntMobile; // 

	private String requestUrl1;

	private String requestUrl2;

	private String merchantType = "AGGMER";

	private String integrationType = "WEBAPI";

	private String settleType = "NET";

	private String panNo;

	private String extMID; // optional

	private String extTID; // optional

	private String accNo; // optional

	private String meEmailID; 

	private String gstin;

	private String gstConsentFlag = "Y"; 

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}

	public String getMebussname() {
		return mebussname;
	}

	public void setMebussname(String mebussname) {
		this.mebussname = mebussname;
	}

	public String getLegalStrName() {
		return legalStrName;
	}

	public void setLegalStrName(String legalStrName) {
		this.legalStrName = legalStrName;
	}

	public String getMerVirtualAdd() {
		return merVirtualAdd;
	}

	public void setMerVirtualAdd(String merVirtualAdd) {
		this.merVirtualAdd = merVirtualAdd;
	}

	public String getAwlmcc() {
		return awlmcc;
	}

	public void setAwlmcc(String awlmcc) {
		this.awlmcc = awlmcc;
	}

	public String getStrCntMobile() {
		return strCntMobile;
	}

	public void setStrCntMobile(String strCntMobile) {
		this.strCntMobile = strCntMobile;
	}

	public String getRequestUrl1() {
		return requestUrl1;
	}

	public void setRequestUrl1(String requestUrl1) {
		this.requestUrl1 = requestUrl1;
	}

	public String getRequestUrl2() {
		return requestUrl2;
	}

	public void setRequestUrl2(String requestUrl2) {
		this.requestUrl2 = requestUrl2;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getIntegrationType() {
		return integrationType;
	}

	public void setIntegrationType(String integrationType) {
		this.integrationType = integrationType;
	}

	public String getSettleType() {
		return settleType;
	}

	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}

	public String getPanNo() {
		return panNo;
	}

	public void setPanNo(String panNo) {
		this.panNo = panNo;
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

	public String getAccNo() {
		return accNo;
	}

	public void setAccNo(String accNo) {
		this.accNo = accNo;
	}

	public String getMeEmailID() {
		return meEmailID;
	}

	public void setMeEmailID(String meEmailID) {
		this.meEmailID = meEmailID;
	}

	public String getGstin() {
		return gstin;
	}

	public void setGstin(String gstin) {
		this.gstin = gstin;
	}

	public String getGstConsentFlag() {
		return gstConsentFlag;
	}

	public void setGstConsentFlag(String gstConsentFlag) {
		this.gstConsentFlag = gstConsentFlag;
	}

	@Override
	public String toString() {
		return "IndsIndRequestModal [pgMerchantId=" + pgMerchantId + ", mebussname=" + mebussname + ", legalStrName="
				+ legalStrName + ", merVirtualAdd=" + merVirtualAdd + ", awlmcc=" + awlmcc + ", strCntMobile="
				+ strCntMobile + ", requestUrl1=" + requestUrl1 + ", requestUrl2=" + requestUrl2 + ", merchantType="
				+ merchantType + ", integrationType=" + integrationType + ", settleType=" + settleType + ", panNo="
				+ panNo + ", extMID=" + extMID + ", extTID=" + extTID + ", accNo=" + accNo + ", meEmailID=" + meEmailID
				+ ", gstin=" + gstin + ", gstConsentFlag=" + gstConsentFlag + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((accNo == null) ? 0 : accNo.hashCode());
		result = prime * result + ((awlmcc == null) ? 0 : awlmcc.hashCode());
		result = prime * result + ((extMID == null) ? 0 : extMID.hashCode());
		result = prime * result + ((extTID == null) ? 0 : extTID.hashCode());
		result = prime * result + ((gstConsentFlag == null) ? 0 : gstConsentFlag.hashCode());
		result = prime * result + ((gstin == null) ? 0 : gstin.hashCode());
		result = prime * result + ((integrationType == null) ? 0 : integrationType.hashCode());
		result = prime * result + ((legalStrName == null) ? 0 : legalStrName.hashCode());
		result = prime * result + ((meEmailID == null) ? 0 : meEmailID.hashCode());
		result = prime * result + ((mebussname == null) ? 0 : mebussname.hashCode());
		result = prime * result + ((merVirtualAdd == null) ? 0 : merVirtualAdd.hashCode());
		result = prime * result + ((merchantType == null) ? 0 : merchantType.hashCode());
		result = prime * result + ((panNo == null) ? 0 : panNo.hashCode());
		result = prime * result + ((pgMerchantId == null) ? 0 : pgMerchantId.hashCode());
		result = prime * result + ((requestUrl1 == null) ? 0 : requestUrl1.hashCode());
		result = prime * result + ((requestUrl2 == null) ? 0 : requestUrl2.hashCode());
		result = prime * result + ((settleType == null) ? 0 : settleType.hashCode());
		result = prime * result + ((strCntMobile == null) ? 0 : strCntMobile.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		IndsIndRequestModal other = (IndsIndRequestModal) obj;
		if (accNo == null) {
			if (other.accNo != null)
				return false;
		} else if (!accNo.equals(other.accNo))
			return false;
		if (awlmcc == null) {
			if (other.awlmcc != null)
				return false;
		} else if (!awlmcc.equals(other.awlmcc))
			return false;
		if (extMID == null) {
			if (other.extMID != null)
				return false;
		} else if (!extMID.equals(other.extMID))
			return false;
		if (extTID == null) {
			if (other.extTID != null)
				return false;
		} else if (!extTID.equals(other.extTID))
			return false;
		if (gstConsentFlag == null) {
			if (other.gstConsentFlag != null)
				return false;
		} else if (!gstConsentFlag.equals(other.gstConsentFlag))
			return false;
		if (gstin == null) {
			if (other.gstin != null)
				return false;
		} else if (!gstin.equals(other.gstin))
			return false;
		if (integrationType == null) {
			if (other.integrationType != null)
				return false;
		} else if (!integrationType.equals(other.integrationType))
			return false;
		if (legalStrName == null) {
			if (other.legalStrName != null)
				return false;
		} else if (!legalStrName.equals(other.legalStrName))
			return false;
		if (meEmailID == null) {
			if (other.meEmailID != null)
				return false;
		} else if (!meEmailID.equals(other.meEmailID))
			return false;
		if (mebussname == null) {
			if (other.mebussname != null)
				return false;
		} else if (!mebussname.equals(other.mebussname))
			return false;
		if (merVirtualAdd == null) {
			if (other.merVirtualAdd != null)
				return false;
		} else if (!merVirtualAdd.equals(other.merVirtualAdd))
			return false;
		if (merchantType == null) {
			if (other.merchantType != null)
				return false;
		} else if (!merchantType.equals(other.merchantType))
			return false;
		if (panNo == null) {
			if (other.panNo != null)
				return false;
		} else if (!panNo.equals(other.panNo))
			return false;
		if (pgMerchantId == null) {
			if (other.pgMerchantId != null)
				return false;
		} else if (!pgMerchantId.equals(other.pgMerchantId))
			return false;
		if (requestUrl1 == null) {
			if (other.requestUrl1 != null)
				return false;
		} else if (!requestUrl1.equals(other.requestUrl1))
			return false;
		if (requestUrl2 == null) {
			if (other.requestUrl2 != null)
				return false;
		} else if (!requestUrl2.equals(other.requestUrl2))
			return false;
		if (settleType == null) {
			if (other.settleType != null)
				return false;
		} else if (!settleType.equals(other.settleType))
			return false;
		if (strCntMobile == null) {
			if (other.strCntMobile != null)
				return false;
		} else if (!strCntMobile.equals(other.strCntMobile))
			return false;
		return true;
	}

	
	
	
}
