package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "pspRefNo" , "upiTransRefNo", "npciTransId", "orderNo"}) })
public class UpiTxn extends BaseDomain {
	
	private static final long serialVersionUID = -1765522567039746896L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long upiTxnId;
	
	private String pspRefNo; 
	
	private String upiTransRefNo;
	
	private String npciTransId; 
	
	private String orderNo;
	
	private String custRefNo;
	
	private Double amount;
	
	private String txnAuthDate;
	
	private String responseCode;
	
	private String approvalNumber;
	
	private String status; 
	
	private String addInfo1;
	
	private String addInfo2;
	
	private String addInfo3;
	
	private String payerVPA; 
	
	private String payeeVPA;
	
	private String currentStatusDesc; 
	
	private String txnNote; 
	
	private String txnType;
	
	private String refUrl;
	
	private Long userId;

	public String getPspRefNo() {
		return pspRefNo;
	}

	public void setPspRefNo(String pspRefNo) {
		this.pspRefNo = pspRefNo;
	}

	public String getUpiTransRefNo() {
		return upiTransRefNo;
	}

	public void setUpiTransRefNo(String upiTransRefNo) {
		this.upiTransRefNo = upiTransRefNo;
	}

	public String getNpciTransId() {
		return npciTransId;
	}

	public void setNpciTransId(String npciTransId) {
		this.npciTransId = npciTransId;
	}

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getTxnAuthDate() {
		return txnAuthDate;
	}

	public void setTxnAuthDate(String txnAuthDate) {
		this.txnAuthDate = txnAuthDate;
	}

	public String getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}

	public String getApprovalNumber() {
		return approvalNumber;
	}

	public void setApprovalNumber(String approvalNumber) {
		this.approvalNumber = approvalNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAddInfo1() {
		return addInfo1;
	}

	public void setAddInfo1(String addInfo1) {
		this.addInfo1 = addInfo1;
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

	public String getPayerVPA() {
		return payerVPA;
	}

	public void setPayerVPA(String payerVPA) {
		this.payerVPA = payerVPA;
	}

	public String getPayeeVPA() {
		return payeeVPA;
	}

	public void setPayeeVPA(String payeeVPA) {
		this.payeeVPA = payeeVPA;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getCurrentStatusDesc() {
		return currentStatusDesc;
	}

	public void setCurrentStatusDesc(String currentStatusDesc) {
		this.currentStatusDesc = currentStatusDesc;
	}

	public String getTxnNote() {
		return txnNote;
	}

	public void setTxnNote(String txnNote) {
		this.txnNote = txnNote;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getRefUrl() {
		return refUrl;
	}

	public void setRefUrl(String refUrl) {
		this.refUrl = refUrl;
	}

	public Long getUpiTxnId() {
		return upiTxnId;
	}

	public void setUpiTxnId(Long upiTxnId) {
		this.upiTxnId = upiTxnId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}
	
}
