package com.nidhi.cms.service;

import javax.validation.Valid;

import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserBankDetails;
import com.nidhi.cms.modal.response.UserBankDetailModel;

public interface UserBankService {

	UserBankDetails getUserById(Long userId);

	void saveOrUpdateUserBankDetails(User user, UserBankDetails userBankDetail, @Valid UserBankDetailModel userBankDetailModel);

}
