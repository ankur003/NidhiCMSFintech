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

	private String walletUuid;
	
	private String upiVirtualAddress;

	private String merchantId;

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

	public String getWalletUuid() {
		return walletUuid;
	}

	public void setWalletUuid(String walletUuid) {
		this.walletUuid = walletUuid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getUpiVirtualAddress() {
		return upiVirtualAddress;
	}

	public void setUpiVirtualAddress(String upiVirtualAddress) {
		this.upiVirtualAddress = upiVirtualAddress;
	}
	
}
