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
	
	@NotBlank
	private String payeeVPAType;
	
	@JsonIgnore
	private String pgMerchantId;
	
	private String paymentType;
	
	private String payeeIfsc;
	
	private String payeeMobNo;
	
	private String payeeEmail;
	
	private String payeeVpa;
	
	private String addInfo2;
	
	private String addInfo3;
	
	private String payeeAccNo;
	
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

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public String getPayeeVpa() {
		return payeeVpa;
	}

	public void setPayeeVpa(String payeeVpa) {
		this.payeeVpa = payeeVpa;
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

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}
	
}
