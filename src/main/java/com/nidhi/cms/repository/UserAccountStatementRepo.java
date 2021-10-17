package com.nidhi.cms.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UserAccountStatement;

@Repository
public interface UserAccountStatementRepo extends JpaRepository<UserAccountStatement, Long> , PagingAndSortingRepository<UserAccountStatement, Long>, JpaSpecificationExecutor<UserAccountStatement>{
	
	List<UserAccountStatement> findAllByUserIdAndTxDateBetween(Long userId, LocalDate fromDate,  LocalDate toDate);
}