package com.nidhi.cms.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.UserDoc;

/**
 * 
 *
 * @author Ankur Bansala
 */

public interface DocService {

	UserDoc getUserDocByUserIdAndDocType(Long userId, DocType docType);

	Boolean saveOrUpdateUserDoc(UserDoc userDoc, Long userId, MultipartFile multiipartFile, DocType docType) throws IOException;

}
