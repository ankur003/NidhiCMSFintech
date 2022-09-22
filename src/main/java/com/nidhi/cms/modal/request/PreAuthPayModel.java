package com.nidhi.cms.modal.request;

public class PreAuthPayModel {
	
	private String orderNo;
	
	private String merchantId;
	
	private String payeeAadhar;
	
	private String txnNote;
	
	private String txnAmount;
	
	private String payeeVPAType;
	
	private String pgMerchantId;
	
	private String paymentType;
	
	private String payeeIfsc;
	
	private String payeeMobNo;
	
	private String payeeEmail;
	
	private String payeeVpa;
	
	private String addInfo2;
	
	private String addInfo3;
	
	private String payeeAccNo;
	
	private String mcc;
	
	private String payeeName;
	
	private String payerAccNo;

	private String payerIfsc;

	private String txnType;
	
	private String currencyCode = "INR";

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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

	public String getPayeeVPAType() {
		return payeeVPAType;
	}

	public void setPayeeVPAType(String payeeVPAType) {
		this.payeeVPAType = payeeVPAType;
	}

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}

	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
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

	public String getPayerIfsc() {
		return payerIfsc;
	}

	public void setPayerIfsc(String payerIfsc) {
		this.payerIfsc = payerIfsc;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getCurrencyCode() {
		return currencyCode;
	}

	public void setCurrencyCode(String currencyCode) {
		this.currencyCode = currencyCode;
	}
	
	public String getPayeeAadhar() {
		return payeeAadhar;
	}

	public void setPayeeAadhar(String payeeAadhar) {
		this.payeeAadhar = payeeAadhar;
	}

	@Override
	public String toString() {
		return "PreAuthPayModel [orderNo=" + orderNo + ", payeeAadhar=" + payeeAadhar
				+ ", txnNote=" + txnNote + ", txnAmount=" + txnAmount + ", payeeVPAType=" + payeeVPAType
				+ ", pgMerchantId=" + pgMerchantId + ", paymentType=" + paymentType + ", payeeIfsc=" + payeeIfsc
				+ ", payeeMobNo=" + payeeMobNo + ", payeeEmail=" + payeeEmail + ", payeeVpa=" + payeeVpa + ", addInfo2="
				+ addInfo2 + ", addInfo3=" + addInfo3 + ", payeeAccNo=" + payeeAccNo + ", mcc=" + mcc + ", payeeName="
				+ payeeName + ", payerAccNo=" + payerAccNo + ", payerIfsc=" + payerIfsc + ", txnType=" + txnType
				+ ", currencyCode=" + currencyCode + "]";
	}


}
