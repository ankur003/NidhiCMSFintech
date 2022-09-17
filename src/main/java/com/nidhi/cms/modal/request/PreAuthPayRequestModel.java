package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PreAuthPayRequestModel {
	
	@NotBlank
	private String orderNo;
	
	@NotBlank
	private String txnNote;
	
	@NotBlank
	private String txnAmount;
	
	@NotBlank
	private String mcc;
	
	@NotBlank
	private String payeeName;
	
	@NotBlank
	private String payerAccNo;

	@NotBlank
	private String payeeVPAType;
	
	@NotBlank
	private String apiKey;
	
	@JsonIgnore
	private String paymentType;
	
	@JsonIgnore
	private String pgMerchantId;
	
	@JsonIgnore
	private String txnType;
	
	@JsonIgnore
	private String currencyCode = "INR";

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMcc() {
		return mcc;
	}

	public void setMcc(String mcc) {
		this.mcc = mcc;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getPayerAccNo() {
		return payerAccNo;
	}

	public void setPayerAccNo(String payerAccNo) {
		this.payerAccNo = payerAccNo;
	}

	public String getPayeeVPAType() {
		return payeeVPAType;
	}

	public void setPayeeVPAType(String payeeVPAType) {
		this.payeeVPAType = payeeVPAType;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	@Override
	public String toString() {
		return "PreAuthPayRequestModel [pgMerchantId=" + pgMerchantId + ", orderNo=" + orderNo + ", txnNote=" + txnNote
				+ ", txnAmount=" + txnAmount + ", currencyCode=" + currencyCode + ", paymentType=" + paymentType
				+ ", txnType=" + txnType + ", mcc=" + mcc + ", payeeName=" + payeeName + ", payerAccNo=" + payerAccNo
				+ ", payeeVPAType=" + payeeVPAType + ", apiKey=" + apiKey + "]";
	}

}
