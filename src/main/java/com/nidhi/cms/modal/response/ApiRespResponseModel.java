package com.nidhi.cms.modal.response;

public class ApiRespResponseModel {
	
	private String amount;
	
	private String statusDesc;
	
	private String pspRefNo;
	
	private String txnAuthDate;
	
	private String payeeVPA;
	
	private String npciTransId;
	
	private String approvalNumber;
	
	private String payerVPA;
	
	private String responseCode;
	
	private Long upiTransRefNo;
	
	private String custRefNo;
	
	private String status;
	
	private AddInfo addInfo;

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public String getPspRefNo() {
		return pspRefNo;
	}

	public void setPspRefNo(String pspRefNo) {
		this.pspRefNo = pspRefNo;
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

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
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

	public Long getUpiTransRefNo() {
		return upiTransRefNo;
	}

	public void setUpiTransRefNo(Long upiTransRefNo) {
		this.upiTransRefNo = upiTransRefNo;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public AddInfo getAddInfo() {
		return addInfo;
	}

	public void setAddInfo(AddInfo addInfo) {
		this.addInfo = addInfo;
	}
	
}
