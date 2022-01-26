package com.nidhi.cms.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.w3c.dom.Document;

import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.WebhookRequest;

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

	String getTransactionStatus(String uniqueId, PaymentMode paymentMode);

	Object transactionStatusInquiry(String uniqueId);

	Object neftIncrementalStatusAPi(String utrNumber);

	List<Transaction> findByUserIdAndUniqueId(Long userId, String uniqueid);

	Transaction findByVirtualTxId(String txId);
	
	Transaction findByCreditTime(LocalDateTime creditTime);

	Transaction saveVirtualTxId(Long userId, WebhookRequest webhookRequest);

	void saveCreditTransaction(Document docWithContent, int i, User user, UserWallet userWallet);

	List<Transaction> findByUserIdAndUtrNumber(Long userId, String utrnumber);

}
