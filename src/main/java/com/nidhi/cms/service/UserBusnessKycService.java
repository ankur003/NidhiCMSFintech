package com.nidhi.cms.service;

import org.dozer.Mapper;

import com.nidhi.cms.constants.enums.KycStatus;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBusinessKyc;

public interface UserBusnessKycService {

	Boolean saveOrUpdateUserBusnessKyc(Mapper beanMapper, UserBusinessKyc userBusinessKyc);

	UserBusinessKyc getUserBusnessKyc(Long userId);

	void updateKycStatus(User user, KycStatus kycStatus);

}
