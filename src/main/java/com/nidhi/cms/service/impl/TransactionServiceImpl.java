package com.nidhi.cms.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.repository.TxRepository;
import com.nidhi.cms.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TxRepository txRepository;

	@Override
	public List<Transaction> getUserTransactions(Long userId) {
		return txRepository.findByUserId(userId);
	}
	
	@Override
	public List<Transaction> getUserTransactionsByDates(Long userId, LocalDate startDate, LocalDate endDate) {
		return txRepository.findByUserIdAndTxDateBetween(userId, startDate, endDate);
	}

	@Override
	public List<Transaction> getUserTransactionsByUniqueId(Long userId, String uniqueId) {
		return txRepository.findByUserIdAndUniqueId(userId, uniqueId);
	}
	
	@Override
	public List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(Long userId, String uniqueId, LocalDate startDate, LocalDate endDate) {
		return txRepository.findByUserIdAndUniqueIdAndTxDateBetween(userId, uniqueId, startDate, endDate);
	}


}
