package com.nidhi.cms.modal.response;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.nidhi.cms.domain.DocType;

public class UserDocModal {
	
	private String docUuid;

	@Enumerated(EnumType.STRING)
	private DocType docType;
	
	private String fileName;
	
	private String contentType;
	
	private Long fileLength;

	@JsonIgnore
	private String data;

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

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
