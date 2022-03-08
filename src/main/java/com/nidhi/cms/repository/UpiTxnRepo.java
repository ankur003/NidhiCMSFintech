package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UpiTxn;

@Repository
public interface UpiTxnRepo extends JpaRepository<UpiTxn, Long> , PagingAndSortingRepository<UpiTxn, Long>, JpaSpecificationExecutor<UpiTxn> {
	
	UpiTxn findByOrderNoOrNpciTransId(String orderNo, String ncpiTransId);

}
