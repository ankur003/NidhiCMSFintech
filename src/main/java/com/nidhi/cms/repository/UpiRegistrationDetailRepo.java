package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.UpiRegistrationDetail;

@Repository
public interface UpiRegistrationDetailRepo extends JpaRepository<UpiRegistrationDetail, Long> , PagingAndSortingRepository<UpiRegistrationDetail, Long>, JpaSpecificationExecutor<UpiRegistrationDetail> {

}
