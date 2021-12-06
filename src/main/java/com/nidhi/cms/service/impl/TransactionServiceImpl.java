package com.nidhi.cms.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.repository.TxRepository;
import com.nidhi.cms.repository.UserWalletRepository;
import com.nidhi.cms.service.TransactionService;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TxRepository txRepository;
	
	@Autowired
	private UserWalletRepository userWalletRepository;

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

	@Override
	public List<Transaction> getUserTransactionsBytoadyDate(Long userId, LocalDate date) {
		return txRepository.findByUserIdAndTxDate(userId, date);
	}
		@Override
		public List<Transaction> findByMerchantIdAndTxDateBetween(String marchantId, LocalDate startDate,
			LocalDate endDate) {
	//		List<Long> userIds = new ArrayList<>();
			List<Transaction> txns =  txRepository.findByMerchantIdAndTxDateBetween(marchantId, startDate, endDate);
			if (CollectionUtils.isEmpty(txns)) {
				return Collections.emptyList();
			}
//			txns.forEach(tx -> userIds.add(tx.getUserId()));
//			
//			List<UserWallet> users= userWalletRepository.findByUserIdIn(userIds);
//			txns.forEach(txn -> users.forEach(user -> {
//	            if (txn.getUserId().equals(user.getUserId())) {
//	            	txn.setAmt(user.getAmount());
//	            }
//	        }));
			return txns;
	}

		@Override
		public List<Transaction> getAllTransactionsByDates(LocalDate startDate, LocalDate endDate) {
			return txRepository.findByTxDateBetween(startDate, endDate);
		}

		@Override
		public List<Transaction> getTransactionsByUniqueId(String uniqueId) {
			return txRepository.findByUniqueId(uniqueId);
		}


}
