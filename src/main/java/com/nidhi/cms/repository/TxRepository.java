package com.nidhi.cms.repository;

import java.time.LocalDate;
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

}