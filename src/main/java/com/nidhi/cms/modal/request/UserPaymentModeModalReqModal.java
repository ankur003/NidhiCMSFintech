package com.nidhi.cms.modal.request;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;

public class UserPaymentModeModalReqModal {

	private String adminUuid;

	private String userUuid;

	private PaymentMode paymentMode;

	private PaymentModeFeeType paymentModeFeeType;

	private Double fee;

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

	public String getAdminUuid() {
		return adminUuid;
	}

	public void setAdminUuid(String adminUuid) {
		this.adminUuid = adminUuid;
	}

}
