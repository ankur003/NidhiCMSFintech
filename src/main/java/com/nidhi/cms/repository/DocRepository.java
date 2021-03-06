package com.nidhi.cms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.UserDoc;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Repository
public interface DocRepository extends JpaRepository<UserDoc, Long> , PagingAndSortingRepository<UserDoc, Long>, JpaSpecificationExecutor<UserDoc> {

	UserDoc findByUserIdAndDocType(Long userId, DocType docType);

	
}
