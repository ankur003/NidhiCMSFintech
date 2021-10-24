package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class UserTxWoOtpReqModal {

	@NotBlank
	private String aggrid;

	@NotBlank
	private String aggrname;

	@NotBlank
	private String corpid;

	@NotBlank
	private String userid;

	@NotBlank
	private String urn;

	@NotBlank
	private String uniqueid;

	@NotBlank
	private String debitacc;

	@NotBlank
	private String creditacc;

	@NotBlank
	private String ifsc;

	@NotNull
	private Double amount;

	@NotBlank
	private String currency;

	private char txntype;

	@NotBlank
	private String payeename;

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
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

	public char getTxntype() {
		return txntype;
	}

	public void setTxntype(char txntype) {
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

}
