package com.nidhi.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.modal.request.UserPaymentModeModal;

@Repository
public interface UserPaymentModeRepo extends JpaRepository<UserPaymentMode, Long>,
		PagingAndSortingRepository<UserPaymentMode, Long>, JpaSpecificationExecutor<UserPaymentMode> {

	List<UserPaymentMode> findByUserId(Long userId);
	
	UserPaymentMode findByUserIdAndPaymentMode(Long userId, PaymentMode paymentMode);
	
	

}
