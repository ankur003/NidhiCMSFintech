package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nidhi.cms.constants.enums.PaymentMode;

@Entity
public class UserWallet extends BaseDomain {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7056330220394252475L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userWalletId;
	
	private Long userId;
	
	private Double amount;
	
	private Double adminAllocatedFund;
	
	@Enumerated(EnumType.STRING)
	private PaymentMode paymentMode;

	public Long getUserWalletId() {
		return userWalletId;
	}

	public void setUserWalletId(Long userWalletId) {
		this.userWalletId = userWalletId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getAdminAllocatedFund() {
		return adminAllocatedFund;
	}

	public void setAdminAllocatedFund(Double adminAllocatedFund) {
		this.adminAllocatedFund = adminAllocatedFund;
	}

	public PaymentMode getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(PaymentMode paymentMode) {
		this.paymentMode = paymentMode;
	}
	
}
