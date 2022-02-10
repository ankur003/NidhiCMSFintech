package com.nidhi.cms.modal.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserTxWoOtpReqModal {

	@JsonIgnore
	private String aggrid;

	@JsonIgnore
	private String aggrname;

	@JsonIgnore
	private String corpid;

	@JsonIgnore
	private String userid;

	@JsonIgnore
	private String urn;

	@NotBlank(message = "uniqueId : uniqueId is invalid or missing")
	@Size(min = 15, max = 15, message = "uniqueId : uniqueId length should be 15")
	private String uniqueId;

	@JsonIgnore
	private String debitacc;

	@NotBlank(message = "creditacc : creditacc is invalid or missing")
	private String creditacc;

	@NotBlank(message = "ifsc : ifsc is invalid or missing")
	private String ifsc;

	@NotNull(message = "amount : amount is invalid or missing")
	private Double amount;

	@NotBlank(message = "currency : currency is invalid or missing")
	private String currency;

	@NotBlank(message = "txntype : txntype is invalid or missing")
	private String txntype;

	@NotBlank(message = "payeename : payeename is invalid or missing")
	private String payeename;

	@NotBlank(message = "merchantId : merchantId is invalid or missing")
	private String merchantId;

	private String remarks;

	@JsonIgnore
	private Double fee;

	@JsonIgnore
	private String feeType;

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public String getDebitacc() {
		return debitacc;
	}

	public void setDebitacc(String debitacc) {
		this.debitacc = debitacc;
	}

	public String getCreditacc() {
		return creditacc;
	}

	public void setCreditacc(String creditacc) {
		this.creditacc = creditacc;
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

	public String getTxntype() {
		return txntype;
	}

	public void setTxntype(String txntype) {
		this.txntype = txntype;
	}

	public String getPayeename() {
		return payeename;
	}

	public void setPayeename(String payeename) {
		this.payeename = payeename;
	}

	public String getAggrid() {
		return aggrid;
	}

	public void setAggrid(String aggrid) {
		this.aggrid = aggrid;
	}

	public String getAggrname() {
		return aggrname;
	}

	public void setAggrname(String aggrname) {
		this.aggrname = aggrname;
	}

	public String getCorpid() {
		return corpid;
	}

	public void setCorpid(String corpid) {
		this.corpid = corpid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUrn() {
		return urn;
	}

	public void setUrn(String urn) {
		this.urn = urn;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Double getFee() {
		return fee;
	}

	public void setFee(Double fee) {
		this.fee = fee;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

}
