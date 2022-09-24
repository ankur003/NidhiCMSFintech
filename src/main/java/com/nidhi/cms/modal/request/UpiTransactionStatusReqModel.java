package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class UpiTransactionStatusReqModel {
	
	@NotBlank
	private String txVpaType;
	
	@NotBlank
	private String txId;
	
	@NotBlank
	private String merchantId;

	public String getTxVpaType() {
		return txVpaType;
	}

	public void setTxVpaType(String txVpaType) {
		this.txVpaType = txVpaType;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
