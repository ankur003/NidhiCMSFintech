package com.nidhi.cms.modal.request.indusind;

public class PayerTypeDetails {
	
	private String virtualAddress;
	private Boolean isMerchant = false;
	private Boolean showMerchant = false;
	private Boolean defVPAStatus = false ;
	
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
	
	

}
