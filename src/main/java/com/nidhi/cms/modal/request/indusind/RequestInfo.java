package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class RequestInfo implements Serializable{

	private static final long serialVersionUID = 1519230090027731172L;

	private String pgMerchantId;

	private String pspRefNo;
	
	public RequestInfo() {
	}

	public RequestInfo(String pgMerchantId, String pspRefNo) {
		super();
		this.pgMerchantId = pgMerchantId;
		this.pspRefNo = pspRefNo;
	}

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}

	public String getPspRefNo() {
		return pspRefNo;
	}

	public void setPspRefNo(String pspRefNo) {
		this.pspRefNo = pspRefNo;
	}

}
