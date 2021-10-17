package com.nidhi.cms.service.impl;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.UserBusinessKyc;
import com.nidhi.cms.repository.UserBusnessKycRepo;
import com.nidhi.cms.service.UserBusnessKycService;

@Service
public class UserBusnessKycServiceImpl implements UserBusnessKycService {

	@Autowired
	private UserBusnessKycRepo userBusnessKycRepo;
	
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
	public UserBusinessKyc getUserBusnessKyc(Long userId) {
		return userBusnessKycRepo.findByUserId(userId);
	}

}
