package com.nidhi.cms.react.request;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;

public class UserChargesRequestModel {
	
	private PaymentMode paymentMode;

	private PaymentModeFeeType paymentModeFeeType;

	private Double fee;

	private boolean isActive;

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public PaymentModeFeeType getPaymentModeFeeType() {
		return paymentModeFeeType;
	}

	public void setPaymentModeFeeType(PaymentModeFeeType paymentModeFeeType) {
		this.paymentModeFeeType = paymentModeFeeType;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}
