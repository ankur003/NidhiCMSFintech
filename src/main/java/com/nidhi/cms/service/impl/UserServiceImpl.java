package com.nidhi.cms.service.impl;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.constants.enums.SearchOperation;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.queryfilter.GenericSpesification;
import com.nidhi.cms.queryfilter.SearchCriteria;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
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
	private OtpService otpService;

	@Autowired
	private DocService docService;

	@Autowired
	private BCryptPasswordEncoder encoder;

	public UserDetails loadUserByUsername(String username) {
		User user = getUserByUserEmailOrMobileNumber(username, username);
		if (user == null || BooleanUtils.isFalse(user.getIsActive()) || BooleanUtils.isFalse(user.getIsUserVerified())) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		ApplicationConfig.loggedInUsers.put(user.getUserEmail(), user.getUserUuid());
		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(),
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
	public Boolean createUser(User user) {
		user.setUserUuid(Utility.getUniqueUuid());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setIsAdmin(false);
		user.setIsUserVerified(false);
		user.setRoles(Utility.getRole(RoleEum.USER));
		User savedUser = userRepository.save(user);
		return otpService.sendingOtp(savedUser);
	}

	@Override
	public User getUserByUserEmailOrMobileNumber(String email, String mobile) {
		return userRepository.findByUserEmailOrMobileNumber(email, mobile);
	}

	@Override
	public User getUserByUserUuid(String userUuid) {
		return userRepository.findByUserUuidAndIsUserVerified(userUuid, true);
	}

	@Override
	public Page<User> getAllUsers(UserRequestFilterModel userRequestFilterModel) {
		GenericSpesification<User> genericSpesification = new GenericSpesification<>();
		if (StringUtils.isNotBlank(userRequestFilterModel.getFirstName())) {
			genericSpesification
					.add(new SearchCriteria("firstName", userRequestFilterModel.getFirstName(), SearchOperation.EQUAL));
		}
		if (StringUtils.isNotBlank(userRequestFilterModel.getLastName())) {
			genericSpesification
					.add(new SearchCriteria("lastName", userRequestFilterModel.getLastName(), SearchOperation.EQUAL));
		}
		if (StringUtils.isNotBlank(userRequestFilterModel.getMiddleName())) {
			genericSpesification.add(
					new SearchCriteria("middleName", userRequestFilterModel.getMiddleName(), SearchOperation.EQUAL));
		}
		if (CollectionUtils.isNotEmpty(genericSpesification.getSearchCriteriaList())) {
			return userRepository.findAll(genericSpesification,
					PageRequest.of(userRequestFilterModel.getPage() - 1, userRequestFilterModel.getLimit()));
		}
		return userRepository
				.findAll(PageRequest.of(userRequestFilterModel.getPage() - 1, userRequestFilterModel.getLimit()));
	}

	@Override
	public void updateUserIsVerified(Otp otp) {
		User user = userRepository.getOne(otp.getUserId());
		user.setIsUserVerified(true);
		userRepository.save(user);
	}

	@Override
	public Boolean saveOrUpdateUserDoc(User user, MultipartFile multiipartFile, DocType docType) throws IOException {
		UserDoc userDoc = docService.getUserDocByUserIdAndDocType(user.getUserId(), docType);
		return docService.saveOrUpdateUserDoc(userDoc, user.getUserId(), multiipartFile, docType);
	}

}
