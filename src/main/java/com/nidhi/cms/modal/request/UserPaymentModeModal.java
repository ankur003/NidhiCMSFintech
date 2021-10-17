package com.nidhi.cms.modal.request;

import com.nidhi.cms.constants.enums.PaymentMode;

public class UserPaymentModeModal {

	private String userUuid;

	private PaymentMode paymentMode;

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

}
