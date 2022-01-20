package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.CreditAmountTransactions;

@Repository
public interface CreditAmountTransactionsRepo extends JpaRepository<CreditAmountTransactions, Long> , PagingAndSortingRepository<CreditAmountTransactions, Long>, JpaSpecificationExecutor<CreditAmountTransactions> {

}
