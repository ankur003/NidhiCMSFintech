package com.nidhi.cms.modal.request;

import com.nidhi.cms.constants.enums.PaymentMode;

public class UserPaymentModeModalReqModal {

	private String userUuid;

	private PaymentMode paymentMode;

	private Double feePercent;
	
	private String billChargeType;

	private boolean isActive;
	public String getUserUuid() {
		return userUuid;
	}

	public void setUserUuid(String userUuid) {
		this.userUuid = userUuid;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}

	public Double getFeePercent() {
		return feePercent;
	}

	public void setFeePercent(Double feePercent) {
		this.feePercent = feePercent;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getBillChargeType() {
		return billChargeType;
	}

	public void setBillChargeType(String billChargeType) {
		this.billChargeType = billChargeType;
	}

}
