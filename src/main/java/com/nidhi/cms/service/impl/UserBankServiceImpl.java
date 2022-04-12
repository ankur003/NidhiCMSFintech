package com.nidhi.cms.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.modal.response.UserBankDetailModel;
import com.nidhi.cms.repository.UserBankDetailsRepo;
import com.nidhi.cms.service.UserBankService;

@Service
public class UserBankServiceImpl implements UserBankService {
	
	@Autowired
	private UserBankDetailsRepo userBankRepo;

	@Override
	public UserBankDetails getUserById(Long userId) {
		return userBankRepo.findByUserId(userId);
	}

	@Override
	public void saveOrUpdateUserBankDetails(User user, UserBankDetails userBankDetail, UserBankDetailModel userBankDetailModel) {
		if (userBankDetail == null) {
			userBankDetail = new UserBankDetails();
		}
		if (StringUtils.isNotBlank(userBankDetailModel.getAccountNumber())) {
			userBankDetail.setAccountNumber(userBankDetailModel.getAccountNumber());
		}
		
		if (StringUtils.isNotBlank(userBankDetailModel.getIfsc())) {
			userBankDetail.setIfsc(userBankDetailModel.getIfsc());
		}
		if (StringUtils.isNotBlank(userBankDetailModel.getBankAccHolderName())) {
			userBankDetail.setBankAccHolderName(userBankDetailModel.getBankAccHolderName());
		}
		if (StringUtils.isNotBlank(userBankDetailModel.getBankName())) {
			userBankDetail.setBankName(userBankDetailModel.getBankName());
		}
		
		userBankDetail.setUserId(user.getUserId());
		userBankRepo.save(userBankDetail);
	}

}
