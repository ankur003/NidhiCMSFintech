package com.nidhi.cms.modal.request.indusind;
import java.io.Serializable;

public class UpiRefundApiBankModel implements Serializable {

	private static final long serialVersionUID = 8528090051203818200L;

	private String orderNo;
	
	private String orgOrderNo;
	
	private String orgINDrefNo;
	
	private String orgCustRefNo;
	
	private String txnNote;
	
	private String txnAmount;
	
	private String currencyCode;
	
	private String payType;
	
	private String txnType;

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

}
