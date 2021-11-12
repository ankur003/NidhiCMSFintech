package com.nidhi.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Entity
public class UserDoc extends BaseDomain {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userDocId;

	private Long userId;

	@Column(unique = true, nullable = false, updatable = false)
	private String docUuid;

	@Enumerated(EnumType.STRING)
	private DocType docType;

	private String fileName;

	private String contentType;

	private Long fileLength;

	@Lob
	private String data;

	private Boolean isVerifiedByAdmin = Boolean.FALSE;

	private String rejectionReason;

	public Long getUserDocId() {
		return userDocId;
	}

	public void setUserDocId(Long userDocId) {
		this.userDocId = userDocId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDocUuid() {
		return docUuid;
	}

	public void setDocUuid(String docUuid) {
		this.docUuid = docUuid;
	}

	public DocType getDocType() {
		return docType;
	}

	public void setDocType(DocType docType) {
		this.docType = docType;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Long getFileLength() {
		return fileLength;
	}

	public void setFileLength(Long fileLength) {
		this.fileLength = fileLength;
	}

	public Boolean getIsVerifiedByAdmin() {
		return isVerifiedByAdmin;
	}

	public void setIsVerifiedByAdmin(Boolean isVerifiedByAdmin) {
		this.isVerifiedByAdmin = isVerifiedByAdmin;
	}

	public String getRejectionReason() {
		return rejectionReason;
	}

	public void setRejectionReason(String rejectionReason) {
		this.rejectionReason = rejectionReason;
	}

}