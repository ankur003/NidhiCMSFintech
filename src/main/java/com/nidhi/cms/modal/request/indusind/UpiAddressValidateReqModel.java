package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class UpiAddressValidateReqModel implements Serializable {

	private static final long serialVersionUID = -4489854152553456542L;

	private RequestInfo requestInfo;
	
	private String vAReqType;

	private PayeeType payeeType;

	public String getvAReqType() {
		return vAReqType;
	}

	public void setvAReqType(String vAReqType) {
		this.vAReqType = vAReqType;
	}

	public PayeeType getPayeeType() {
		return payeeType;
	}

	public void setPayeeType(PayeeType payeeType) {
		this.payeeType = payeeType;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
	

}
