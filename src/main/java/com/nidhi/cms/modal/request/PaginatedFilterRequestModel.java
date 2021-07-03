package com.nidhi.cms.modal.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class PaginatedFilterRequestModel {

	@ApiModelProperty(value = "Created Since Date  [yyyy-mm-dd]")
	private String createdSince;

	@ApiModelProperty(value = "Page Number")
	@Min(value = 1, message = "page: Min page is 1")
	private Integer page = 1;

	@ApiModelProperty(value = "Records Per Page Limit")
	@Max(value = 250, message = "limit : Max limit allowed is 250")
	private Integer limit = 50;

	public String getCreatedSince() {
		return createdSince;
	}

	public void setCreatedSince(final String createdSince) {
		this.createdSince = createdSince;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(final Integer page) {
		this.page = page;
	}

	public Integer getLimit() {
		return limit;
	}

	public void setLimit(final Integer limit) {
		this.limit = limit;
	}

}
