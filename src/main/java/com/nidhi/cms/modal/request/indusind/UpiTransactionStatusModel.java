package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class UpiTransactionStatusModel implements Serializable {

	private static final long serialVersionUID = -1456445928527711269L;
	
	private String custRefNo;
	
	private String npciTranId;
	
	private RequestInfo requestInfo;

	public String getCustRefNo() {
		return custRefNo;
	}

	public void setCustRefNo(String custRefNo) {
		this.custRefNo = custRefNo;
	}

	public String getNpciTranId() {
		return npciTranId;
	}

	public void setNpciTranId(String npciTranId) {
		this.npciTranId = npciTranId;
	}

	public RequestInfo getRequestInfo() {
		return requestInfo;
	}

	public void setRequestInfo(RequestInfo requestInfo) {
		this.requestInfo = requestInfo;
	}
	
}
