package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class NEFTIncrementalStatusReqModal {

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

	@JsonIgnore
	private String uniqueid;

	@NotBlank(message = "merchantId : merchantId is invalid or missing")
	private String merchantId;

	@NotBlank(message = "utrnumber : utrnumber is invalid or missing")
	private String utrnumber;

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

	public String getUniqueid() {
		return uniqueid;
	}

	public void setUniqueid(String uniqueid) {
		this.uniqueid = uniqueid;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getUtrnumber() {
		return utrnumber;
	}

	public void setUtrnumber(String utrnumber) {
		this.utrnumber = utrnumber;
	}

}
