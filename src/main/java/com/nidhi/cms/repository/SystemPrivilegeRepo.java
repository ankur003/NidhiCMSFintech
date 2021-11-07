package com.nidhi.cms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.SystemPrivilege;

@Repository
public interface SystemPrivilegeRepo extends JpaRepository<SystemPrivilege, Long> , PagingAndSortingRepository<SystemPrivilege, Long>, JpaSpecificationExecutor<SystemPrivilege> {

	SystemPrivilege findByPrivilegeName(String privilegeName);
	
	List<SystemPrivilege> findByPrivilegeNameIn(List<String> privilegeName);
	
}