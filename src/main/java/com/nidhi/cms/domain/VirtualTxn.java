package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "remitterUTR" }) })
public class VirtualTxn extends BaseDomain {

	private static final long serialVersionUID = -7824944373343786102L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long creditAmountTransactionsId;

	private String requestId;

	private String challanCode;

	private String challanNo;

	private String clientAccountNo;

	private String clientName;

	private String amount;

	private String remitterName;

	private String remitterAccountNo;

	private String remitterIFSC;

	private String remitterBank;

	private String remitterBranch;

	private String remitterUTR;

	private String payMethod;

	private String creditAccountNo;

	private String inwardRefNum;

	private String creditTime;

	private String status;

	public Long getCreditAmountTransactionsId() {
		return creditAmountTransactionsId;
	}

	public void setCreditAmountTransactionsId(Long creditAmountTransactionsId) {
		this.creditAmountTransactionsId = creditAmountTransactionsId;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getChallanCode() {
		return challanCode;
	}

	public void setChallanCode(String challanCode) {
		this.challanCode = challanCode;
	}

	public String getChallanNo() {
		return challanNo;
	}

	public void setChallanNo(String challanNo) {
		this.challanNo = challanNo;
	}

	public String getClientAccountNo() {
		return clientAccountNo;
	}

	public void setClientAccountNo(String clientAccountNo) {
		this.clientAccountNo = clientAccountNo;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getRemitterName() {
		return remitterName;
	}

	public void setRemitterName(String remitterName) {
		this.remitterName = remitterName;
	}

	public String getRemitterAccountNo() {
		return remitterAccountNo;
	}

	public void setRemitterAccountNo(String remitterAccountNo) {
		this.remitterAccountNo = remitterAccountNo;
	}

	public String getRemitterIFSC() {
		return remitterIFSC;
	}

	public void setRemitterIFSC(String remitterIFSC) {
		this.remitterIFSC = remitterIFSC;
	}

	public String getRemitterBank() {
		return remitterBank;
	}

	public void setRemitterBank(String remitterBank) {
		this.remitterBank = remitterBank;
	}

	public String getRemitterBranch() {
		return remitterBranch;
	}

	public void setRemitterBranch(String remitterBranch) {
		this.remitterBranch = remitterBranch;
	}

	public String getRemitterUTR() {
		return remitterUTR;
	}

	public void setRemitterUTR(String remitterUTR) {
		this.remitterUTR = remitterUTR;
	}

	public String getPayMethod() {
		return payMethod;
	}

	public void setPayMethod(String payMethod) {
		this.payMethod = payMethod;
	}

	public String getCreditAccountNo() {
		return creditAccountNo;
	}

	public void setCreditAccountNo(String creditAccountNo) {
		this.creditAccountNo = creditAccountNo;
	}

	public String getInwardRefNum() {
		return inwardRefNum;
	}

	public void setInwardRefNum(String inwardRefNum) {
		this.inwardRefNum = inwardRefNum;
	}

	public String getCreditTime() {
		return creditTime;
	}

	public void setCreditTime(String creditTime) {
		this.creditTime = creditTime;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
