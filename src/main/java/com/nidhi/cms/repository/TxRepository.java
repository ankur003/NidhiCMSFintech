package com.nidhi.cms.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.Transaction;

@Repository
public interface TxRepository extends JpaRepository<Transaction, Long> , PagingAndSortingRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {

	List<Transaction> findByUserId(Long userId);
	
	List<Transaction> findByUserIdAndTxDateBetween(Long userId, LocalDate startDate, LocalDate endDate);

	List<Transaction> findByUserIdAndUniqueId(Long userId, String uniqueId);

	List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(Long userId, String uniqueId, LocalDate startDate,
			LocalDate endDate);

	List<Transaction> findByUserIdAndTxDate(Long userId, LocalDate date);
	List<Transaction> findByMerchantIdAndTxDateBetween(String marchantId, LocalDate startDate, LocalDate endDate);

	List<Transaction> findByTxDateBetween(LocalDate startDate, LocalDate endDate);

	List<Transaction> findByUniqueId(String uniqueId);

	Transaction findByVirtualTxId(String virtualTxId);

	Transaction findByCreditTime(LocalDateTime creditTime);

	List<Transaction> findByUserIdAndUtrNumber(Long userId, String utrnumber);

	List<Transaction> findByStatusAndResponse(String status, String resposne);

	List<Transaction> findByStatusAndResponseAndSchedulerCustomInfoAndIsFeeTx(String status, String response,
			String schedulerCustomInfo, boolean isFeeTx);

	Transaction findByUtrNumber(String utrNumber);

}