package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class UpiRefundApiRequestModel implements Serializable {

	private static final long serialVersionUID = 3585143933840310036L;

	private String pgMerchantId;
	
	@NotBlank
	private String merchantId;
	
	private String orderNo;
	
	private String orgOrderNo;
	
	private String orgINDrefNo;
	
	private String orgCustRefNo;
	
	private String txnNote;
	
	private String txnAmount;
	
	private String currencyCode;
	
	private String payType;
	
	private String txnType;

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

	public String getOrgOrderNo() {
		return orgOrderNo;
	}

	public void setOrgOrderNo(String orgOrderNo) {
		this.orgOrderNo = orgOrderNo;
	}

	public String getOrgINDrefNo() {
		return orgINDrefNo;
	}

	public void setOrgINDrefNo(String orgINDrefNo) {
		this.orgINDrefNo = orgINDrefNo;
	}

	public String getOrgCustRefNo() {
		return orgCustRefNo;
	}

	public void setOrgCustRefNo(String orgCustRefNo) {
		this.orgCustRefNo = orgCustRefNo;
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	
}
