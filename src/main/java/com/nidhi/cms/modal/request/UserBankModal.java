package com.nidhi.cms.modal.request;

public class UserBankModal {
	
	private String accountNumber;
	
	private String ifsc;
	
	private String bankAccHolderName;
	
	private String bankName;

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
