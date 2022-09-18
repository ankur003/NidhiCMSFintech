package com.nidhi.cms.modal.request.indusind;

import javax.validation.constraints.NotBlank;

public class UpiCollectTxRequestModel {
	
	@NotBlank
	private String merchantId;

	private Double amount;
	
	private String transactionNote;
	
	private String upiTransRefNo;
	
	private Long expiryTime;
	
	private RequestInfo requestInfo;
	
	private AddInfo addInfo;
	
	private PayeeType payerType;
	
	private InvoiceDetails invoice_details;
	
	private GstDetails GST_details;
	
	

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTransactionNote() {
		return transactionNote;
	}

	public void setTransactionNote(String transactionNote) {
		this.transactionNote = transactionNote;
	}

	public String getUpiTransRefNo() {
		return upiTransRefNo;
	}

	public void setUpiTransRefNo(String upiTransRefNo) {
		this.upiTransRefNo = upiTransRefNo;
	}

	public Long getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(Long expiryTime) {
		this.expiryTime = expiryTime;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}

	public AddInfo getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(AddInfo addInfo) {
		this.addInfo = addInfo;
	}

	public PayeeType getPayerType() {
		return payerType;
	}

	public void setPayerType(PayeeType payerType) {
		this.payerType = payerType;
	}

	public InvoiceDetails getInvoice_details() {
		return invoice_details;
	}

	public void setInvoice_details(InvoiceDetails invoice_details) {
		this.invoice_details = invoice_details;
	}

	public GstDetails getGST_details() {
		return GST_details;
	}

	public void setGST_details(GstDetails gST_details) {
		GST_details = gST_details;
	}

}
