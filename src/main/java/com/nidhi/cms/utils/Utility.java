package com.nidhi.cms.utils;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;

public class Utility {

	private Utility() {
		// Utility
	}

	public static final String getUniqueUuid() {
		return UUID.randomUUID().toString();
	}

	public static Set<Role> getRole(RoleEum roleEum) {
		Role role = new Role();
		role.setName(roleEum);
		return Collections.singleton(role);
	}

}
