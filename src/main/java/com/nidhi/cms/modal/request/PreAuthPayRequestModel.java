package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class PreAuthPayRequestModel {
	
	@NotBlank
	private String orderNo;
	
	@NotBlank
	private String merchantId;
	
	@NotBlank
	private String txnNote;
	
	@NotBlank
	private String txnAmount;
	
	@JsonIgnore
	private String mcc;
	
	private String payeeName;
	
	@JsonIgnore
	private String payerAccNo;

	@NotBlank
	private String payeeVPAType;
	
	private String paymentType;
	
	@JsonIgnore
	private String payerIfsc;
	
	private String payeeIfsc;
	
	private String payeeMobNo;
	
	private String payeeEmail;
	
	private String payeeVPA;
	
	private String addInfo2;
	
	private String addInfo3;
	
	private String payeeAccNo;
	
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

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPayerIfsc() {
		return payerIfsc;
	}

	public void setPayerIfsc(String payerIfsc) {
		this.payerIfsc = payerIfsc;
	}

	public String getPayeeIfsc() {
		return payeeIfsc;
	}

	public void setPayeeIfsc(String payeeIfsc) {
		this.payeeIfsc = payeeIfsc;
	}

	public String getPayeeMobNo() {
		return payeeMobNo;
	}

	public void setPayeeMobNo(String payeeMobNo) {
		this.payeeMobNo = payeeMobNo;
	}

	public String getPayeeEmail() {
		return payeeEmail;
	}

	public void setPayeeEmail(String payeeEmail) {
		this.payeeEmail = payeeEmail;
	}

	public String getPayeeVPA() {
		return payeeVPA;
	}

	public void setPayeeVPA(String payeeVPA) {
		this.payeeVPA = payeeVPA;
	}

	public String getAddInfo2() {
		return addInfo2;
	}

	public void setAddInfo2(String addInfo2) {
		this.addInfo2 = addInfo2;
	}

	public String getAddInfo3() {
		return addInfo3;
	}

	public void setAddInfo3(String addInfo3) {
		this.addInfo3 = addInfo3;
	}

	public String getPayeeAccNo() {
		return payeeAccNo;
	}

	public void setPayeeAccNo(String payeeAccNo) {
		this.payeeAccNo = payeeAccNo;
	}

	@Override
	public String toString() {
		return "PreAuthPayRequestModel [orderNo=" + orderNo + ", merchantId=" + merchantId + ", txnNote=" + txnNote
				+ ", txnAmount=" + txnAmount + ", mcc=" + mcc + ", payeeName=" + payeeName + ", payerAccNo="
				+ payerAccNo + ", payeeVPAType=" + payeeVPAType + ", paymentType=" + paymentType + ", payerIfsc="
				+ payerIfsc + ", payeeIfsc=" + payeeIfsc + ", payeeMobNo=" + payeeMobNo + ", payeeEmail=" + payeeEmail
				+ ", payeeVPA=" + payeeVPA + ", addInfo2=" + addInfo2 + ", addInfo3=" + addInfo3 + ", payeeAccNo="
				+ payeeAccNo + ", pgMerchantId=" + pgMerchantId + ", txnType=" + txnType + ", currencyCode="
				+ currencyCode + "]";
	}
	
}
