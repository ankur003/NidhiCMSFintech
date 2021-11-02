package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nidhi.cms.constants.enums.PaymentMode;

@Entity
public class UserPaymentMode extends BaseDomain {

	private static final long serialVersionUID = -1293783007980955204L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userPaymentModeId;

	private Long userId;

	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	private Double feePercent;

	public Long getUserPaymentModeId() {
		return userPaymentModeId;
	}

	public void setUserPaymentModeId(Long userPaymentModeId) {
		this.userPaymentModeId = userPaymentModeId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

}