package com.nidhi.cms.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TransactionError extends BaseDomain {

	private static final long serialVersionUID = -6842655008178699184L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long transactionErrorId;

	private String aggrId;

	private String aggrName;

	private String corpId;

	private Long userId;

	private String urn;

	private String uniqueId;

	private String debitAcc;

	private String creditAcc;

	private String ifsc;

	private Double amount;

	private Double fee;

	private Double amountPlusfee;

	private String currency;

	private String txnType;

	private String payeeName;

	private String merchantId;

	private String reqId;

	private String status;

	private String utrNumber;

	private String response;

	private LocalDate txDate;

	private String txType;

	public Long getTransactionErrorId() {
		return transactionErrorId;
	}

	public void setTransactionErrorId(Long transactionErrorId) {
		this.transactionErrorId = transactionErrorId;
	}

	public String getAggrId() {
		return aggrId;
	}

	public void setAggrId(String aggrId) {
		this.aggrId = aggrId;
	}

	public String getAggrName() {
		return aggrName;
	}

	public void setAggrName(String aggrName) {
		this.aggrName = aggrName;
	}

	public String getCorpId() {
		return corpId;
	}

	public void setCorpId(String corpId) {
		this.corpId = corpId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDebitAcc() {
		return debitAcc;
	}

	public void setDebitAcc(String debitAcc) {
		this.debitAcc = debitAcc;
	}

	public String getCreditAcc() {
		return creditAcc;
	}

	public void setCreditAcc(String creditAcc) {
		this.creditAcc = creditAcc;
	}

	public String getIfsc() {
		return ifsc;
	}

	public void setIfsc(String ifsc) {
		this.ifsc = ifsc;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getPayeeName() {
		return payeeName;
	}

	public void setPayeeName(String payeeName) {
		this.payeeName = payeeName;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getReqId() {
		return reqId;
	}

	public void setReqId(String reqId) {
		this.reqId = reqId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getUtrNumber() {
		return utrNumber;
	}

	public void setUtrNumber(String utrNumber) {
		this.utrNumber = utrNumber;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public Double getAmountPlusfee() {
		return amountPlusfee;
	}

	public void setAmountPlusfee(Double amountPlusfee) {
		this.amountPlusfee = amountPlusfee;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public String getTxType() {
		return txType;
	}

	public void setTxType(String txType) {
		this.txType = txType;
	}

}
