package com.nidhi.cms.service;

import java.util.List;

import org.dozer.Mapper;

import com.nidhi.cms.domain.UserBusinessKyc;

public interface UserBusnessKycService {

	Boolean saveOrUpdateUserBusnessKyc(Mapper beanMapper, UserBusinessKyc userBusinessKyc);

	UserBusinessKyc getUserBusnessKyc(Long userId);

}