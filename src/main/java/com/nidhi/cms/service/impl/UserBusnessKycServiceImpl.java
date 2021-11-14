package com.nidhi.cms.service.impl;

import java.util.Optional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.repository.UserBusnessKycRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.UserBusnessKycService;

@Service
public class UserBusnessKycServiceImpl implements UserBusnessKycService {

	@Autowired
	private UserBusnessKycRepo userBusnessKycRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public Boolean saveOrUpdateUserBusnessKyc(Mapper beanMapper, UserBusinessKyc userBusinessKyc) {
		UserBusinessKyc businessKyc = userBusnessKycRepo.findByUserId(userBusinessKyc.getUserId());
		if (businessKyc == null) {
			userBusnessKycRepo.save(userBusinessKyc);
		} else {
			userBusinessKyc.setUserBusinessKycId(businessKyc.getUserBusinessKycId());
			userBusnessKycRepo.save(userBusinessKyc);
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

}
