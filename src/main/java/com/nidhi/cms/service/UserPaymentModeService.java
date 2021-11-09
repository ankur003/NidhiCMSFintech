package com.nidhi.cms.service;

import java.util.List;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.modal.request.UserPaymentModeModalReqModal;

public interface UserPaymentModeService {

	UserPaymentMode saveOrUpdateUserPaymentMode(User user,UserPaymentModeModalReqModal userPaymentModeModalReqModal);

	UserPaymentMode activateOrDeActivateUserPaymentMode(User user, PaymentMode paymentMode, Boolean isActivate);

	UserPaymentMode getUserPaymentMode(User user, PaymentMode paymentMode);

	List<UserPaymentMode> getUserAllPaymentMode(User user);

}
