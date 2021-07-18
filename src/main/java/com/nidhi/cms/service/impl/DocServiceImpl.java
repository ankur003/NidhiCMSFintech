package com.nidhi.cms.service.impl;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.UserDoc;
import com.nidhi.cms.repository.DocRepository;
import com.nidhi.cms.service.DocService;
import com.nidhi.cms.utils.Utility;

/**
 * 
 *
 * @author Ankur Bansala
 */

@Service
public class DocServiceImpl implements DocService {
	
	@Autowired
	private DocRepository docRepository;

	@Override
	public UserDoc getUserDocByUserIdAndDocType(Long userId, DocType docType) {
		return docRepository.findByUserIdAndDocType(userId, docType);
	}

	@Override
	public Boolean saveOrUpdateUserDoc(UserDoc userDoc, Long userId, MultipartFile multiipartFile, DocType docType) throws IOException {
		if (userDoc == null) {
			userDoc = new UserDoc();
			userDoc.setUserId(userId);
			userDoc.setDocUuid(Utility.getUniqueUuid());
		}
		userDoc.setFileLength(multiipartFile.getSize());
		userDoc.setDocType(docType);
		userDoc.setContentType(multiipartFile.getContentType());
		userDoc.setFileName(StringUtils.cleanPath(multiipartFile.getOriginalFilename()));
		userDoc.setData(multiipartFile.getBytes());
		docRepository.save(userDoc);
		return Boolean.TRUE;
	}

}
