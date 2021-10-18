package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserBankDetails extends BaseDomain {

	private static final long serialVersionUID = -4080450940200160144L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userBankDetailsId;

	private Long userId;

	private String accountNumber;

	private String ifsc;

	private String bankAccHolderName;

	private String bankName;

	public Long getUserBankDetailsId() {
		return userBankDetailsId;
	}

	public void setUserBankDetailsId(Long userBankDetailsId) {
		this.userBankDetailsId = userBankDetailsId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getAccountNumber() {
		return accountNumber;
	}

	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public String getBankAccHolderName() {
		return bankAccHolderName;
	}

	public void setBankAccHolderName(String bankAccHolderName) {
		this.bankAccHolderName = bankAccHolderName;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

}
