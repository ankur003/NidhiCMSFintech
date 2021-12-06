package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class NEFTIncrementalStatusReqModal {

	@NotBlank(message = "merchantId : merchantId is invalid or missing")
	private String merchantId;

	@NotBlank(message = "utrnumber : utrnumber is invalid or missing")
	private String utrnumber;

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getUtrnumber() {
		return utrnumber;
	}

	public void setUtrnumber(String utrnumber) {
		this.utrnumber = utrnumber;
	}

}
