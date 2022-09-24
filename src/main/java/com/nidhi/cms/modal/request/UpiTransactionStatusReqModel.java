package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class UpiTransactionStatusReqModel {
	
	private String pspRefNo;
	
	private String custRefNo;
	
	private String npciTranId;
	
	@NotBlank
	private String merchantId;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPspRefNo() {
		return pspRefNo;
	}

	public void setPspRefNo(String pspRefNo) {
		this.pspRefNo = pspRefNo;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getNpciTranId() {
		return npciTranId;
	}

	public void setNpciTranId(String npciTranId) {
		this.npciTranId = npciTranId;
	}

}
