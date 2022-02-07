package com.nidhi.cms.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.service.email.EmailService;

@Service
public class UserWalletServiceImpl implements UserWalletService {
	
	@Autowired
	private UserWalletRepository userWalletRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWalletServiceImpl.class);


	@Override
	public UserWallet findByUserId(Long userId) {
		return userWalletRepo.findByUserId(userId);
	}

	@Override
	public Boolean allocateFund(Long userId, Double amount) {
		UserWallet userWallet = findByUserId(userId);
		if (userWallet == null || amount == null) {
			return Boolean.FALSE;
		}
		userWalletRepo.save(userWallet);
		return Boolean.TRUE;
	}

	@Override
	public UserWallet findByMerchantId(String marchantId) {
		return userWalletRepo.findByMerchantId(marchantId);
	}

	@Override
	public List<String> getUserNameByMarchantId(String marchantId) {
		List<UserWallet> userWallets = userWalletRepo.findByMerchantIdContaining(marchantId);
		if (CollectionUtils.isEmpty(userWallets)) {
			return Collections.emptyList();
		}
		List<String> list = new ArrayList<>();
		userWallets.forEach(userWallet -> {
			if (userWallet.getUserId() != null) {
				User user = userRepository.findByUserId(userWallet.getUserId());
				if (user != null) {
					list.add(userWallet.getMerchantId() + "-(" +user.getFullName()+ ")");
				}
			}
		});
		return list;
	}

	@Override
	public UserWallet findByVirtualId(String virtualWalletId) {
		return userWalletRepo.findByWalletUuid(virtualWalletId);
	}

	@Override
	public Boolean updateBalance(UserWallet userWallet, Double amt) {
		BigDecimal amount = new BigDecimal(amt).setScale(2, RoundingMode.HALF_DOWN);
		userWallet.setAmount(userWallet.getAmount() + amount.doubleValue());
		userWalletRepo.save(userWallet);
		triggerCreditAccountNotification(userWallet, amt);
		return Boolean.TRUE;
	}
	
	private void triggerCreditAccountNotification(UserWallet userWallet, Double amt) {
		
		User user = userRepository.findByUserId(userWallet.getUserId());
		
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[UserWalletServiceImpl.triggerCreditAccountNotification] user email is blank - {}", user.getUserEmail());
			return;
		}
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Your NidhiCMS Account credited with Rs." +amt);
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getFullName());
		model.put("txAmt", amt);
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", userWallet.getAmount());
		emailService.sendEmail(request, model, null, EmailTemplateConstants.CREDIT_ACC);
		
	}

	@Override
	public UserWallet save(UserWallet userWallet) {
		return userWalletRepo.save(userWallet);
		
	}

}
