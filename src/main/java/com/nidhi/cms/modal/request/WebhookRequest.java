package com.nidhi.cms.modal.request;

public class WebhookRequest {

	private String virtualWalletId;

	private String txId;

	private Double amount;

	public String getVirtualWalletId() {
		return virtualWalletId;
	}

	public void setVirtualWalletId(String virtualWalletId) {
		this.virtualWalletId = virtualWalletId;
	}

	public String getTxId() {
		return txId;
	}

	public void setTxId(String txId) {
		this.txId = txId;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

}
