package com.nidhi.cms.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.utils.Utility;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public UserDetails loadUserByUsername(String username) {
		User user = getUserByUserName(username, false);
		if (user == null) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> {
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
		});
		return authorities;
	}

	@Override
	public String createUser(User user) {
		user.setUserUuid(Utility.getUniqueUuid());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setIsAdmin(false);
		user.setRoles(Utility.getRole(RoleEum.USER));
		return userRepository.save(user).getUserUuid();
	}

	@Override
	public User getUserByUserName(String username, Boolean isBlocked) {
		return userRepository.findByUsernameAndIsBlocked(username, isBlocked);
	}

	@Override
	public User getUserByUserUuid(String userUuid) {
		return userRepository.findByUserUuidAndIsBlocked(userUuid, false);
	}

}
