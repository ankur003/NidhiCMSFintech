package com.nidhi.cms.modal.request.indusind;

import java.io.Serializable;

public class PaginationConfigModel implements Serializable {

	private static final long serialVersionUID = 9015809080469931736L;

	private String from_date;
	
	private String to_date;
	
	private String from_index;
	
	private String to_index;
	
	public PaginationConfigModel() {
		//
	}
	
	public PaginationConfigModel(String from_date, String to_date, String from_index, String to_index) {
		super();
		this.from_date = from_date;
		this.to_date = to_date;
		this.from_index = from_index;
		this.to_index = to_index;
	}

	public String getFrom_date() {
		return from_date;
	}

	public void setFrom_date(String from_date) {
		this.from_date = from_date;
	}

	public String getTo_date() {
		return to_date;
	}

	public void setTo_date(String to_date) {
		this.to_date = to_date;
	}

	public String getFrom_index() {
		return from_index;
	}

	public void setFrom_index(String from_index) {
		this.from_index = from_index;
	}

	public String getTo_index() {
		return to_index;
	}

	public void setTo_index(String to_index) {
		this.to_index = to_index;
	}

}
