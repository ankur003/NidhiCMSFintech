package com.nidhi.cms.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.dozer.Mapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.modal.request.UserTxWoOtpReqModal;
import com.nidhi.cms.repository.UserBusnessKycRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.UserBusnessKycService;
import com.nidhi.cms.service.email.EmailService;

@Service
public class UserBusnessKycServiceImpl implements UserBusnessKycService {

	@Autowired
	private UserBusnessKycRepo userBusnessKycRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UserBusnessKycServiceImpl.class);

	
	@Override
	public Boolean saveOrUpdateUserBusnessKyc(Mapper beanMapper, UserBusinessKyc userBusinessKyc, Boolean isAdmin) {
		UserBusinessKyc businessKyc = userBusnessKycRepo.findByUserId(userBusinessKyc.getUserId());
		if (businessKyc == null) {
			userBusnessKycRepo.save(userBusinessKyc);
		} else {
			userBusinessKyc.setUserBusinessKycId(businessKyc.getUserBusinessKycId());
			userBusnessKycRepo.save(userBusinessKyc);
			if (BooleanUtils.isNotTrue(isAdmin)) {
				User user = userRepository.findByUserId(userBusinessKyc.getUserId());
				user.setKycStatus(KycStatus.UNDER_REVIEW);
				userRepository.save(user);
			}
		}
		return Boolean.TRUE;
	}
	
	@Override
	public void updateKycStatus(User user, KycStatus kycStatus) {
		if (user.getKycStatus().equals(kycStatus)) {
			return;
		}
			user.setKycStatus(kycStatus);
			userRepository.save(user);
	}

	@Override
	public UserBusinessKyc getUserBusnessKyc(Long userId) {
		return userBusnessKycRepo.findByUserId(userId);
	}
	
	@Override
	public UserBusinessKyc getUserBusnessKycByPan(String pan) {
		return userBusnessKycRepo.findByIndividualPan(pan);
	}

}
