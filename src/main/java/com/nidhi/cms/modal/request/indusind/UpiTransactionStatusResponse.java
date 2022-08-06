package com.nidhi.cms.modal.request.indusind;

public class UpiTransactionStatusResponse {
	
	private String statusDesc;
	
	private String amount;
	
	private String txnAuthDate;
	
	private String payeeVPA;

	private String npciTransId;

	private String payerVPA;

	private String responseCode;

	private String upiTransRefNo;
	
	private String custRefNo;

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getTxnAuthDate() {
		return txnAuthDate;
	}

	public void setTxnAuthDate(String txnAuthDate) {
		this.txnAuthDate = txnAuthDate;
	}

	public String getPayeeVPA() {
		return payeeVPA;
	}

	public void setPayeeVPA(String payeeVPA) {
		this.payeeVPA = payeeVPA;
	}

	public String getNpciTransId() {
		return npciTransId;
	}

	public void setNpciTransId(String npciTransId) {
		this.npciTransId = npciTransId;
	}

	public String getPayerVPA() {
		return payerVPA;
	}

	public void setPayerVPA(String payerVPA) {
		this.payerVPA = payerVPA;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getUpiTransRefNo() {
		return upiTransRefNo;
	}

	public void setUpiTransRefNo(String upiTransRefNo) {
		this.upiTransRefNo = upiTransRefNo;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "UpiTransactionStatusResponse [statusDesc=" + statusDesc + ", amount=" + amount + ", txnAuthDate="
				+ txnAuthDate + ", payeeVPA=" + payeeVPA + ", npciTransId=" + npciTransId + ", payerVPA=" + payerVPA
				+ ", responseCode=" + responseCode + ", upiTransRefNo=" + upiTransRefNo + ", custRefNo=" + custRefNo
				+ ", getStatusDesc()=" + getStatusDesc() + ", getTxnAuthDate()=" + getTxnAuthDate() + ", getPayeeVPA()="
				+ getPayeeVPA() + ", getNpciTransId()=" + getNpciTransId() + ", getPayerVPA()=" + getPayerVPA()
				+ ", getResponseCode()=" + getResponseCode() + ", getUpiTransRefNo()=" + getUpiTransRefNo()
				+ ", getCustRefNo()=" + getCustRefNo() + ", getAmount()=" + getAmount() + ", getClass()=" + getClass()
				+ ", hashCode()=" + hashCode() + ", toString()=" + super.toString() + "]";
	}
	

}
