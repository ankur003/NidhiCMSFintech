package com.nidhi.cms.service.impl;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.nidhi.cms.domain.DocType;
import com.nidhi.cms.domain.User;
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
		String base64EncodedImage = new String(Base64.encodeBase64(multiipartFile.getBytes()), StandardCharsets.US_ASCII);
		userDoc.setData(base64EncodedImage);
		docRepository.save(userDoc);
		return Boolean.TRUE;
	}

	@Override
	public List<UserDoc> getUserAllKyc(Long userId) {
		return docRepository.findByUserId(userId);
	}

	@Override
	public Boolean approveOrDisApproveKyc(User user, Boolean kycResponse, DocType docType, String kycRejectReason) {
		UserDoc doc = docRepository.findByUserIdAndDocType(user.getUserId(), docType);
		if (doc == null) {
			return Boolean.FALSE;
		}
		if (BooleanUtils.isNotTrue(kycResponse)) {
			doc.setRejectionReason(kycRejectReason);
		}
		doc.setIsVerifiedByAdmin(kycResponse);
		docRepository.save(doc);
		return Boolean.TRUE;
	}

}
