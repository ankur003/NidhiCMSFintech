package com.nidhi.cms.modal.request.indusind;

public class PayeeType {
	
	private String virtualAddress;
	
	public PayeeType() {
	}
	

	public PayeeType(String virtualAddress) {
		super();
		this.virtualAddress = virtualAddress;
	}

	public String getVirtualAddress() {
		return virtualAddress;
	}

	public void setVirtualAddress(String virtualAddress) {
		this.virtualAddress = virtualAddress;
	}
	
}
