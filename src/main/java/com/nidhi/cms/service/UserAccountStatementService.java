package com.nidhi.cms.service;

import java.time.LocalDate;
import java.util.List;

import com.nidhi.cms.domain.UserAccountStatement;

public interface UserAccountStatementService {
	
	List<UserAccountStatement> getUserAccountStatements(Long userId, LocalDate fromDate, LocalDate toDate);

}
