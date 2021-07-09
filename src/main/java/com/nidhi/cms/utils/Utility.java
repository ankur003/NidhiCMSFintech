package com.nidhi.cms.utils;

import java.util.Collections;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.Role;

/**
 * 
 *
 * @author Ankur Bansala
 */

public class Utility {

	private Utility() {
		// Utility
	}

	public static final String getUniqueUuid() {
		return UUID.randomUUID().toString();
	}

	public static String getRandomNumberString() {
		return String.format("%06d", new Random().nextInt(999999));
	}

	public static Set<Role> getRole(RoleEum roleEum) {
		Role role = new Role();
		role.setName(roleEum);
		return Collections.singleton(role);
	}

}
