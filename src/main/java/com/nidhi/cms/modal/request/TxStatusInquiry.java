package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class TxStatusInquiry {

	@NotBlank(message = "merchantId : merchantId is invalid or missing")
	private String uniqueid;

	@NotBlank(message = "merchantId : merchantId is invalid or missing")
	private String merchantId;

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

}
