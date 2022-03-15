package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class UpiDeActivateModel implements Serializable {

	private static final long serialVersionUID = -3797435814691482323L;

	private String pgMerchantId;
	private String subMerchantId;
	private String subMerVirtualAdd;
	private String merchantType;
	private String action;
	private String remarks;

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}

	public String getSubMerchantId() {
		return subMerchantId;
	}

	public void setSubMerchantId(String subMerchantId) {
		this.subMerchantId = subMerchantId;
	}

	public String getSubMerVirtualAdd() {
		return subMerVirtualAdd;
	}

	public void setSubMerVirtualAdd(String subMerVirtualAdd) {
		this.subMerVirtualAdd = subMerVirtualAdd;
	}

	public String getMerchantType() {
		return merchantType;
	}

	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

}
