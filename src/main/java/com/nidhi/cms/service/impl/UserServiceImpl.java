package com.nidhi.cms.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.nidhi.cms.cipher.CheckNEFTjson;
import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.config.CmsConfig;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.constants.enums.RoleEum;
import com.nidhi.cms.constants.enums.SearchOperation;
import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.Otp;
import com.nidhi.cms.domain.SystemPrivilege;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.NEFTIncrementalStatusReqModal;
import com.nidhi.cms.modal.request.SubAdminCreateModal;
import com.nidhi.cms.modal.request.TransactionStatusInquiry;
import com.nidhi.cms.modal.request.TxStatusInquiry;
import com.nidhi.cms.modal.request.UserBankModal;
import com.nidhi.cms.modal.request.UserRequestFilterModel;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;
import com.nidhi.cms.modal.request.UserUpdateModal;
import com.nidhi.cms.queryfilter.GenericSpesification;
import com.nidhi.cms.queryfilter.SearchCriteria;
import com.nidhi.cms.repository.DocRepository;
import com.nidhi.cms.repository.SystemPrivilegeRepo;
import com.nidhi.cms.repository.TxRepository;
import com.nidhi.cms.repository.UserBankDetailsRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.service.OtpService;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.UserWalletService;
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

	@Autowired
	private UserWalletService userWalletService;

	@Autowired
	private UserWalletRepository userWalletRepository;

	@Autowired
	private DocRepository docRepository;

	@Autowired
	private UserBankDetailsRepo userBankDetailsRepo;

	@Autowired
	private SystemPrivilegeRepo systemPrivilegeRepo;
	
	@Autowired
	private TxRepository txRepository;
	
	@Autowired
	private TransactionService transactionService;
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

	public UserDetails loadUserByUsername(String username) {
		User user = getUserByUserEmailOrMobileNumber(username, username);
		if (user == null || BooleanUtils.isFalse(user.getIsActive())
				|| BooleanUtils.isFalse(user.getIsUserVerified())) {
			throw new UsernameNotFoundException("Invalid username or password.");
		}
		ApplicationConfig.loggedInUsers.put(user.getUserEmail(), user.getUserUuid());
		return new org.springframework.security.core.userdetails.User(user.getUserEmail(), user.getPassword(),
				getAuthority(user));
	}

	private Set<SimpleGrantedAuthority> getAuthority(User user) {
		Set<SimpleGrantedAuthority> authorities = new HashSet<>();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName())));
		return authorities;
	}

	@Override
	public Boolean createUser(User user, Boolean isCreatedByAdim) {
		user.setUserUuid(Utility.getUniqueUuid());
		user.setPassword(encoder.encode(user.getPassword()));
		user.setIsAdmin(false);
		user.setIsUserVerified(false);
		user.setRoles(Utility.getRole(RoleEum.USER));
		user.setIsUserCreatedByAdmin(isCreatedByAdim);
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
	public User getUserDetailByUserUuid(String userUuid) {
		return userRepository.findByUserUuid(userUuid);
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
		if (userRequestFilterModel.getIsSubAdmin() != null) {
			genericSpesification.add(
					new SearchCriteria("isSubAdmin", userRequestFilterModel.getIsSubAdmin(), SearchOperation.EQUAL));
		}
		if (userRequestFilterModel.getIsAdmin() != null) {
			genericSpesification
					.add(new SearchCriteria("isAdmin", userRequestFilterModel.getIsAdmin(), SearchOperation.EQUAL));
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

	@Override
	public Boolean changeEmailOrPassword(User user, String emailToChange, String passwordToChange) {
		if (StringUtils.isNotBlank(emailToChange)) {
			User emailToChangeUser = userRepository.findByUserEmail(emailToChange);
			if (emailToChangeUser == null || user.getUserId().equals(emailToChangeUser.getUserId())) {
				user.setUserEmail(emailToChange);
				if (StringUtils.isNotBlank(passwordToChange)) {
					user.setPassword(encoder.encode(passwordToChange));
				}
				userRepository.save(user);
				return Boolean.TRUE;
			}
		} else if (StringUtils.isNotBlank(passwordToChange)) {
			user.setPassword(encoder.encode(passwordToChange));
			userRepository.save(user);
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@Override
	public Boolean approveOrDisApproveKyc(User user, Boolean kycResponse, DocType docType, String kycRejectReason) {
		Boolean isDone = docService.approveOrDisApproveKyc(user, kycResponse, docType, kycRejectReason);
		if (BooleanUtils.isTrue(isDone)) {
			if (BooleanUtils.isTrue(kycResponse)) {
				List<UserDoc> doc = docRepository.findByUserIdAndIsVerifiedByAdmin(user.getUserId(), true);
				if (doc != null && doc.size() >= 3 && user.getKycStatus() != KycStatus.VERIFIED) {
					user.setKycStatus(KycStatus.VERIFIED);
					userRepository.save(user);
					createUserWallet(user);
				}
			}
			if (BooleanUtils.isFalse(kycResponse)) {
				user.setKycStatus(KycStatus.REJECTED);
				userRepository.save(user);
				return Boolean.TRUE;
			}

			return Boolean.TRUE;

		}
		return Boolean.FALSE;

	}

	private void createUserWallet(User user) {
		UserWallet userWallet = userWalletService.findByUserId(user.getUserId());
		if (userWallet == null) {
			userWallet = new UserWallet();
			userWallet.setUserId(user.getUserId());
			userWallet.setAmount(0.0);
			userWallet.setWalletUuid(Utility.getUniqueUuid());
			userWallet.setMerchantId(getMerchantId());
			userWalletRepository.save(userWallet);
		}
	}

	private String getMerchantId() {
		UserWallet usrWallet = userWalletRepository.findFirstByOrderByUserWalletIdDesc();
		if (usrWallet == null) {
			return "MID" + "_" + "00001";
		}
		String merchantId = usrWallet.getMerchantId();
		String[] splitMerchantId = merchantId.split("_");
		Long subStringMerchantId = Long.valueOf(splitMerchantId[1]);
		subStringMerchantId = subStringMerchantId + 1;
		return "MID" + "_" + String.format("%05d", subStringMerchantId);
	}

	@Override
	public Boolean userActivateOrDeactivate(User user, Boolean isActivate) {
		user.setIsActive(isActivate);
		userRepository.save(user);
		return Boolean.TRUE;
	}

	@Override
	public UserBankDetails saveOrUpdateUserBankDetails(User user, UserBankModal userBankModal) {
		UserBankDetails userBankDetails = userBankDetailsRepo.findByUserId(user.getUserId());
		if (user.getKycStatus().equals(KycStatus.VERIFIED)) {
			return userBankDetails;
		}
		if (userBankDetails == null) {
			userBankDetails = new UserBankDetails();
		}
		userBankDetails.setAccountNumber(userBankModal.getAccountNumber());
		userBankDetails.setBankAccHolderName(userBankModal.getBankAccHolderName());
		userBankDetails.setIfsc(userBankModal.getIfsc());
		userBankDetails.setUserId(user.getUserId());
		userBankDetails.setBankName(userBankModal.getBankName());
		return userBankDetailsRepo.save(userBankDetails);
	}

	@Override
	public UserBankDetails getUserBankDetails(User user) {
		return userBankDetailsRepo.findByUserId(user.getUserId());
	}

	@Override
	public Boolean apiWhiteListing(User user, String ip) {
		user.setWhiteListIp(ip.trim());
		userRepository.save(user);
		return Boolean.TRUE;
	}

	@Override
	public Object txWithoutOTP(User user, UserTxWoOtpReqModal userTxWoOtpReqModal, UserWallet userWallet) {
		try {
			userTxWoOtpReqModal.setAggrid(CmsConfig.CUST_ID);
			userTxWoOtpReqModal.setAggrname(CmsConfig.AGGR_NAME);
			userTxWoOtpReqModal.setCorpid(CmsConfig.CORP_ID);
			userTxWoOtpReqModal.setUrn(CmsConfig.URN);
			userTxWoOtpReqModal.setUserid(CmsConfig.USER);
			userTxWoOtpReqModal.setUniqueid(RandomStringUtils.randomAlphabetic(15));
			userTxWoOtpReqModal.setDebitacc(CmsConfig.DBIT_ACC);
			String jsonAsString = Utility.createJsonRequestAsString(userTxWoOtpReqModal);
			LOGGER.info("[UserServiceImpl.txWithoutOTP] jsonAsString - {}", jsonAsString);

			byte[] ciphertextBytes = CheckNEFTjson.encryptJsonRequest(jsonAsString);
			String encryptedJsonResponse = CheckNEFTjson.sendThePostRequest(
					new String(org.bouncycastle.util.encoders.Base64.encode(ciphertextBytes)),
					"https://apibankingone.icicibank.com/api/Corporate/CIB/v1/Transaction", "POST");
			LOGGER.info("[UserServiceImpl.txWithoutOTP] msg - {}", encryptedJsonResponse);
			String response = CheckNEFTjson.deCryptResponse(encryptedJsonResponse);
			LOGGER.info("[UserServiceImpl.txWithoutOTP] response - {}", response);
			if (response == null) {
				return null;
			}
			Boolean isValid = isResponseValid(response);
			if (BooleanUtils.isFalse(isValid)) {
				LOGGER.info("[UserServiceImpl.txWithoutOTP] isValid - {}", isValid);
				return response;
			}
			performPostAction(user, userTxWoOtpReqModal, response, userWallet);
			return createResponse(response, userTxWoOtpReqModal);
		} catch (Exception e) {
			LOGGER.error("[UserServiceImpl.txWithoutOTP] Exception - {}", e);
		}
		return null;
	}

	private Object createResponse(String response, UserTxWoOtpReqModal userTxWoOtpReqModal) {
		JSONObject jsonObject = new JSONObject(response);
		jsonObject.remove("CORP_ID");
		jsonObject.remove("USER_ID");
		jsonObject.remove("AGGR_ID");
		jsonObject.remove("AGGR_NAME");
		jsonObject.remove("URN");
		jsonObject.put("CURRENCY", userTxWoOtpReqModal.getCurrency());
		jsonObject.put("MERCHANT_ID", userTxWoOtpReqModal.getMerchantId());
		return jsonObject;
	}

	private void performPostAction(User user, UserTxWoOtpReqModal userTxWoOtpReqModal, String response, UserWallet userWallet) {
		
		Transaction txn = new Transaction();
		txn.setAggrId(userTxWoOtpReqModal.getAggrid());
		txn.setAggrName(userTxWoOtpReqModal.getAggrname());
		txn.setAmount(userTxWoOtpReqModal.getAmount());
		BigDecimal amountPlusfee = BigDecimal.valueOf(userTxWoOtpReqModal.getAmount() + userTxWoOtpReqModal.getFee()).setScale(2, RoundingMode.HALF_DOWN);
		txn.setAmountPlusfee(amountPlusfee.doubleValue());
		txn.setCorpId(userTxWoOtpReqModal.getCorpid());
		txn.setCreditAcc(userTxWoOtpReqModal.getCreditacc());
		txn.setCurrency(userTxWoOtpReqModal.getCurrency());
		txn.setDebitAcc(userTxWoOtpReqModal.getDebitacc());
		txn.setFee(userTxWoOtpReqModal.getFee());
		txn.setIfsc(userTxWoOtpReqModal.getIfsc());
		txn.setMerchantId(userTxWoOtpReqModal.getMerchantId());
		txn.setPayeeName(userTxWoOtpReqModal.getPayeename());
		txn.setTxnType(userTxWoOtpReqModal.getTxntype());
		setIciciResponse(txn, response);
		txn.setUniqueId(userTxWoOtpReqModal.getUniqueid());
		txn.setUrn(userTxWoOtpReqModal.getUrn());
		txn.setUserId(user.getUserId());
		txn.setTxType("Dr.");
		txn.setAmt(BigDecimal.valueOf(userWallet.getAmount() - txn.getAmountPlusfee()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
		txRepository.save(txn);
		LOGGER.info("[UserServiceImpl.performPostAction] ===============================TX saved ==================== ");
		updateBalance(txn.getAmountPlusfee(), userWallet);
	}

	private void updateBalance(Double amountPlusfee, UserWallet userWallet) {
		LOGGER.info("[UserServiceImpl.updateBalance] ===============================amt ==================== {} - ", userWallet.getAmount());
		LOGGER.info("[UserServiceImpl.updateBalance] ===============================amountPlusfee ==================== {} - ", amountPlusfee);
		BigDecimal amt = BigDecimal.valueOf(userWallet.getAmount() - amountPlusfee).setScale(2, RoundingMode.HALF_DOWN);
		userWallet.setAmount(amt.doubleValue());
		userWalletRepository.save(userWallet);
		LOGGER.info("[UserServiceImpl.updateBalance] ===============================balace updated ==================== {} - ", amt.doubleValue());
	}

	private void setIciciResponse(Transaction txn, String response) {
		try {
			JSONObject jsonObject = new JSONObject(response);
			if (jsonObject.has("REQID")) {
				txn.setReqId(jsonObject.getString("REQID"));
			}
			if (jsonObject.has("RESPONSE")) {
				txn.setResponse(jsonObject.getString("RESPONSE"));
			}
			if (jsonObject.has("STATUS")) {
				txn.setStatus(jsonObject.getString("STATUS"));
			}
			if (jsonObject.has("UTRNUMBER")) {
				txn.setUtrNumber(jsonObject.getString("UTRNUMBER"));
			} 
		} catch (Exception e) {
			LOGGER.error("[UserServiceImpl.setIciciResponse]  {} - ", e);
		}

	}

	private Boolean isResponseValid(String response) {
			JSONObject jsonObject = new JSONObject(response);
			LOGGER.info("[UserServiceImpl.isResponseValid]  {} - ", jsonObject);
			LOGGER.info("[UserServiceImpl.isResponseValid] jsonObject.has(\"success\") {} - ", jsonObject.has("success"));
			if (jsonObject.has("success") && BooleanUtils.isFalse(jsonObject.getBoolean("success"))) {
				LOGGER.info("[UserServiceImpl.isResponseValid] BooleanUtils.isFalse(jsonObject.getBoolean(\"success\")) {} " , BooleanUtils.isFalse(jsonObject.getBoolean("success")));
				return Boolean.FALSE;
			}
			if (jsonObject.has("RESPONSE") && jsonObject.getString("RESPONSE").equals("FAILURE")) {
				LOGGER.info("[UserServiceImpl.isResponseValid] BooleanUtils.isFalse(jsonObject.getBoolean(\"success\")) {} " , BooleanUtils.isFalse(jsonObject.getBoolean("success")));
				return Boolean.FALSE;
			}
			if (jsonObject.getString("RESPONSE").equals("SUCCESS")) {
				return Boolean.TRUE;
			}
		return Boolean.FALSE;
	}

	@Override
	public Object txStatusInquiry(User user, TxStatusInquiry txStatusInquiry) {
		
		return transactionService.transactionStatusInquiry(txStatusInquiry.getUniqueid());
	}

	@Override
	public Object txNEFTStatus(User user, NEFTIncrementalStatusReqModal nEFTIncrementalStatusReqModal) {
		return transactionService.neftIncrementalStatusAPi(nEFTIncrementalStatusReqModal.getUtrnumber());
	}

	@Override
	public User updateUserDetails(User user, UserUpdateModal userUpdateModal) {
		if (userUpdateModal.getFirstName() != null) {
			user.setFirstName(userUpdateModal.getFirstName());
		}

		if (userUpdateModal.getLastName() != null) {
			user.setLastName(userUpdateModal.getLastName());
		}

		if (userUpdateModal.getMiddleName() != null) {
			user.setMiddleName(userUpdateModal.getMiddleName());
		}

		if (userUpdateModal.getFullName() != null) {
			user.setFullName(userUpdateModal.getFullName());
		}

		if (userUpdateModal.getDob() != null) {
			user.setDob(Utility.stringToLocalDate(userUpdateModal.getDob()));
		}

		if ((userUpdateModal.getUserPrivileges() != null)) {
			user.setPrivilageNames(String.join(",", userUpdateModal.getUserPrivileges()));
		}
		return userRepository.save(user);
	}

	@Override
	public SystemPrivilege addAccessPrivilegesIntoSystem(String privilegeName) {
		SystemPrivilege systemPrivilege = systemPrivilegeRepo.findByPrivilegeName(privilegeName);
		if (systemPrivilege == null) {
			systemPrivilege = new SystemPrivilege();
		}
		systemPrivilege.setPrivilegeName(privilegeName);
		return systemPrivilegeRepo.save(systemPrivilege);
	}

	@Override
	public SystemPrivilege deleteAccessPrivilegesIntoSystem(String privilegeName) {
		SystemPrivilege systemPrivilege = systemPrivilegeRepo.findByPrivilegeName(privilegeName);
		if (systemPrivilege == null) {
			return systemPrivilege;
		}
		systemPrivilegeRepo.delete(systemPrivilege);
		systemPrivilege.setPrivilegeName(null);
		
		revokeUserPrivilege(privilegeName);
		
		return systemPrivilege;
	}

	private void revokeUserPrivilege(String privilegeName) {
		List<User> users = userRepository.findByPrivilageNamesContaining(privilegeName);
		if (CollectionUtils.isEmpty(users)) {
			return;
		}
		for (User user : users) {
			try {
				if (user.getPrivilageNames() != null && user.getPrivilageNames().contains(privilegeName)) {
					String removedPrivilege = StringUtils.remove(user.getPrivilageNames(), privilegeName);
					user.setPrivilageNames(StringUtils.removeEnd(removedPrivilege, ","));
					userRepository.save(user);
				}
			} catch (Exception e) {
				LOGGER.error("[UserServiceImpl.revokeUserPrivilege] erorr occured during revoking privileges , userId - {}",
						user.getUserId());
			}
		}
	}

	@Override
	public SystemPrivilege updateAccessPrivilegesIntoSystem(String oldPrivilegeName, String newPrivilegeName) {
		SystemPrivilege systemPrivilege = systemPrivilegeRepo.findByPrivilegeName(oldPrivilegeName);
		if (systemPrivilege == null) {
			return null;
		}
		systemPrivilege.setPrivilegeName(newPrivilegeName);
		return systemPrivilegeRepo.save(systemPrivilege);
	}

	@Override
	public SystemPrivilege findbyIdprivilege(Long id) {
		Optional<SystemPrivilege> systemPrivilege = systemPrivilegeRepo.findById(id);
		if (systemPrivilege.isPresent()) {
			return systemPrivilege.get();
		}
		return null;
	}

	@Override
	public User createSubAdmin(SubAdminCreateModal subAdminCreateModal) {
		List<SystemPrivilege> systemPrivileges = systemPrivilegeRepo
				.findByPrivilegeNameIn(subAdminCreateModal.getPrivilageNames());
		if (CollectionUtils.isEmpty(systemPrivileges)
				|| systemPrivileges.size() != subAdminCreateModal.getPrivilageNames().size()) {
			return null;
		}
		User user = userRepository.findByUserEmailOrMobileNumber(subAdminCreateModal.getUserEmail(),
				subAdminCreateModal.getMobileNumber());
		if (user != null) {
			return null;
		}
		user = new User();
		user.setUserEmail(subAdminCreateModal.getUserEmail());
		user.setMobileNumber(subAdminCreateModal.getMobileNumber());
		user.setFullName(subAdminCreateModal.getFullName());
		user.setPrivilageNames(String.join(",", subAdminCreateModal.getPrivilageNames()));
		user.setPassword(encoder.encode(subAdminCreateModal.getPassword()));
		user.setUserUuid(Utility.getUniqueUuid());
		user.setIsUserCreatedByAdmin(true);
		user.setIsSubAdmin(true);
		user.setRoles(Utility.getRole(RoleEum.USER));
		User savedUser = userRepository.save(user);
		otpService.sendingOtp(savedUser, subAdminCreateModal.getPassword());
		return savedUser;
	}

	@Override
	public List<User> getSubAdminList() {
		return userRepository.findByIsSubAdmin(Boolean.TRUE);
	}

	@Override
	public List<SystemPrivilege> getSystemPrivilegeList() {
		return systemPrivilegeRepo.findAll();
	}

	@Override
	public User findByUserEmail(String mobileOrEmail) {
		return userRepository.findByUserEmail(mobileOrEmail);
	}

	@Override
	public User findByUserMobileNumber(String mobileOrEmail) {
		return userRepository.findByMobileNumber(mobileOrEmail);
	}

	@Override
	public User findByApiKey(String apiKey) {
		return userRepository.findByApiKey(apiKey);
	}

	@Override
	public User findByUserId(Long userId) {
		return userRepository.findByUserId(userId);
	}

	@Override
	public List<User> getAllUsers() {
		return userRepository.findByIsSubAdminAndIsAdmin(false, false);
	}

}
