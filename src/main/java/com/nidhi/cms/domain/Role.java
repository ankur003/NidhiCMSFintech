package com.nidhi.cms.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.nidhi.cms.constants.enums.RoleEum;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Entity
public class Role extends BaseDomain {

	private static final long serialVersionUID = 7189961001551880900L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long roleId;

	@Enumerated(EnumType.STRING)
	private RoleEum name;

	@Column
	private String description;

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public RoleEum getName() {
		return name;
	}

	public void setName(RoleEum name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
