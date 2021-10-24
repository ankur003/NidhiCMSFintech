package com.nidhi.cms.modal.request;

import javax.validation.constraints.NotBlank;

public class UserTxBaseModal {

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
