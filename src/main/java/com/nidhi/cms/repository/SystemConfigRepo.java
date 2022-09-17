package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.SystemConfig;

@Repository
public interface SystemConfigRepo extends JpaRepository<SystemConfig, Long> , PagingAndSortingRepository<SystemConfig, Long>, JpaSpecificationExecutor<SystemConfig> {
	
	SystemConfig findBySystemKey(String systemKey);

}
