package com.nidhi.cms.modal.response;

import java.time.LocalDate;

public class TransactionResponseModel {
	
	private LocalDate txDate;
	
	private String uniqueId;
	
	private Double amount;
	
	private Double fee;
	
	private String currency;
	
	private String txType;
	
	private String status;

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
