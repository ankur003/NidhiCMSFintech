package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class UpiListApiRequestModel implements Serializable{

	private static final long serialVersionUID = 581935114374122294L;
	
	private PaginationConfigModel paginationConfig;
	
	private String pgMerchantId;

	public PaginationConfigModel getPaginationConfig() {
		return paginationConfig;
	}

	public void setPaginationConfig(PaginationConfigModel paginationConfig) {
		this.paginationConfig = paginationConfig;
	}

	public String getPgMerchantId() {
		return pgMerchantId;
	}

	public void setPgMerchantId(String pgMerchantId) {
		this.pgMerchantId = pgMerchantId;
	}
	

}
