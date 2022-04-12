package com.nidhi.cms.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserDoc;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface DocService {

	UserDoc getUserDocByUserIdAndDocType(Long userId, DocType docType);
	
	UserDoc getUserDocByDocumentUuid(String documentUuid);

	String saveOrUpdateUserDoc(UserDoc userDoc, Long userId, MultipartFile multiipartFile, DocType docType) throws IOException;

	List<UserDoc> getUserAllKyc(Long userId);

	Boolean approveOrDisApproveKyc(User user, Boolean kycResponse, DocType docType, String kycRejectReason);

}
