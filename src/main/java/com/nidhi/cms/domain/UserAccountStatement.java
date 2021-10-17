package com.nidhi.cms.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class UserAccountStatement extends BaseDomain {

	private static final long serialVersionUID = 7122283100499818652L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userAccountStatementId;

	private Long userId;

	private String cmsTxId;

	private String statementId;

	private LocalDate txDate;

	private String description;

	private String contact;
	
	private Double txAmount;

	public Long getUserAccountStatementId() {
		return userAccountStatementId;
	}

	public void setUserAccountStatementId(Long userAccountStatementId) {
		this.userAccountStatementId = userAccountStatementId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getCmsTxId() {
		return cmsTxId;
	}

	public void setCmsTxId(String cmsTxId) {
		this.cmsTxId = cmsTxId;
	}

	public String getStatementId() {
		return statementId;
	}

	public void setStatementId(String statementId) {
		this.statementId = statementId;
	}

	public LocalDate getTxDate() {
		return txDate;
	}

	public void setTxDate(LocalDate txDate) {
		this.txDate = txDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public Double getTxAmount() {
		return txAmount;
	}

	public void setTxAmount(Double txAmount) {
		this.txAmount = txAmount;
	}
	
}
