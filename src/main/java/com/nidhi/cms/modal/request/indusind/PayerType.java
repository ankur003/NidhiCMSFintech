package com.nidhi.cms.modal.request.indusind;

public class PayerType {
	
	private String virtualAddress;
	
	private Boolean isMerchant = Boolean.FALSE;
	
	private Boolean showMerchant = Boolean.FALSE;
	
	private Boolean defVPAStatus = Boolean.FALSE;
	
	public PayerType() {
		
	} 

	public String getVirtualAddress() {
		return virtualAddress;
	}

	public void setVirtualAddress(String virtualAddress) {
		this.virtualAddress = virtualAddress;
	}

	public Boolean getIsMerchant() {
		return isMerchant;
	}

	public void setIsMerchant(Boolean isMerchant) {
		this.isMerchant = isMerchant;
	}

	public Boolean getShowMerchant() {
		return showMerchant;
	}

	public void setShowMerchant(Boolean showMerchant) {
		this.showMerchant = showMerchant;
	}

	public Boolean getDefVPAStatus() {
		return defVPAStatus;
	}

	public void setDefVPAStatus(Boolean defVPAStatus) {
		this.defVPAStatus = defVPAStatus;
	}

	public PayerType(String virtualAddress, Boolean isMerchant, Boolean showMerchant, Boolean defVPAStatus) {
		super();
		this.virtualAddress = virtualAddress;
		this.isMerchant = isMerchant;
		this.showMerchant = showMerchant;
		this.defVPAStatus = defVPAStatus;
	} 
	
	

}
