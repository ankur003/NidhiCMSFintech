package com.nidhi.cms.service;

import java.time.LocalDate;
import java.util.List;

import com.nidhi.cms.domain.Transaction;

public interface TransactionService {

	List<Transaction> getUserTransactions(Long userId);

	List<Transaction> getUserTransactionsByDates(Long userId, LocalDate startDate, LocalDate endDate);

	List<Transaction> getUserTransactionsByUniqueId(Long userId, String uniqueId);

	List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(Long userId, String uniqueId, LocalDate startDate,
			LocalDate endDate);

	List<Transaction> getUserTransactionsBytoadyDate(Long userId, LocalDate date);

}
