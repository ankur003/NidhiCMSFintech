package com.nidhi.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Entity
public class Otp extends BaseDomain {

	private static final long serialVersionUID = -1293783007980955204L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long otpId;

	private Long userId;

	private String emailOtp;

	private String mobileOtp;

	@Column(unique = true, nullable = false, updatable = false)
	private String otpUuid;

	public Long getOtpId() {
		return otpId;
	}

	public void setOtpId(Long otpId) {
		this.otpId = otpId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getEmailOtp() {
		return emailOtp;
	}

	public void setEmailOtp(String emailOtp) {
		this.emailOtp = emailOtp;
	}

	public String getMobileOtp() {
		return mobileOtp;
	}

	public void setMobileOtp(String mobileOtp) {
		this.mobileOtp = mobileOtp;
	}

	public String getOtpUuid() {
		return otpUuid;
	}

	public void setOtpUuid(String otpUuid) {
		this.otpUuid = otpUuid;
	}

}
