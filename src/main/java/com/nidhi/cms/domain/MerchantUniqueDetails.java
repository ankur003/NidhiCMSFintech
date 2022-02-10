package com.nidhi.cms.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class MerchantUniqueDetails extends BaseDomain {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6316409965819130221L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long merchantUniqueDetailsId;

	private String merchantId;

	private String uniqueId;

	public Long getMerchantUniqueDetailsId() {
		return merchantUniqueDetailsId;
	}

	public void setMerchantUniqueDetailsId(Long merchantUniqueDetailsId) {
		this.merchantUniqueDetailsId = merchantUniqueDetailsId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

}
