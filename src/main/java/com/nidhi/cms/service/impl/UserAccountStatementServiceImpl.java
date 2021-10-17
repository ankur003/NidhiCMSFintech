package com.nidhi.cms.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.UserAccountStatement;
import com.nidhi.cms.repository.UserAccountStatementRepo;
import com.nidhi.cms.service.UserAccountStatementService;

@Service
public class UserAccountStatementServiceImpl implements UserAccountStatementService {

	@Autowired
	private UserAccountStatementRepo userAccountStatementRepo;

	@Override
	public List<UserAccountStatement> getUserAccountStatements(Long userId, LocalDate fromDate, LocalDate toDate) {
		return userAccountStatementRepo.findAllByUserIdAndTxDateBetween(userId, fromDate, toDate);
	}

}
