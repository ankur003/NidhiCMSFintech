package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class ValidateUPIAddressReqModel {
	
	@NotBlank
	private String upiAddress;
	
	@NotBlank
	private String merchantId;
	
	@NotBlank
	private String vpaType;

	public String getUpiAddress() {
		return upiAddress;
	}

	public void setUpiAddress(String upiAddress) {
		this.upiAddress = upiAddress;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getVpaType() {
		return vpaType;
	}

	public void setVpaType(String vpaType) {
		this.vpaType = vpaType;
	}
	

}
