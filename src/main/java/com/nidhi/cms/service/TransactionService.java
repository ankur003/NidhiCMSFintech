package com.nidhi.cms.service;

import java.time.LocalDate;
import java.util.List;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.Transaction;

public interface TransactionService {

	List<Transaction> getUserTransactions(Long userId);

	List<Transaction> getUserTransactionsByDates(Long userId, LocalDate startDate, LocalDate endDate);

	List<Transaction> getUserTransactionsByUniqueId(Long userId, String uniqueId);

	List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(Long userId, String uniqueId, LocalDate startDate,
			LocalDate endDate);


	List<Transaction> getUserTransactionsBytoadyDate(Long userId, LocalDate date);

	List<Transaction> findByMerchantIdAndTxDateBetween(String marchantId, LocalDate startDate, LocalDate endDate);

	List<Transaction> getAllTransactionsByDates(LocalDate startDate, LocalDate endDate);

	List<Transaction> getTransactionsByUniqueId(String uniqueId);

	Object getTransactionStatus(String uniqueId, PaymentMode paymentMode);

	Object transactionStatusInquiry(String uniqueId);

	Object neftIncrementalStatusAPi(String utrNumber);

}
