package com.nidhi.cms.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.UpiTxn;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.repository.UpiTxnRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.email.EmailService;
import com.nidhi.cms.utils.Utility;

@Service
public class UpiTxnServiceImpl implements UpiTxnService {
	
	@Autowired
	private UpiTxnRepo upiTxnRepo;
	
	@Autowired
	private UserWalletService userWalletService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpiTxnServiceImpl.class);

	@Override
	public void saveUpiTxn(JSONObject decryptedJson) {
		LOGGER.info("orderNo {}", decryptedJson.getString("orderNo"));
		LOGGER.info("ncpiTransId {}", decryptedJson.getString("npciTransId"));
		UpiTxn upiTxnData = upiTxnRepo.findByOrderNoOrNpciTransId(decryptedJson.getString("orderNo"), decryptedJson.getString("npciTransId"));
		if (upiTxnData != null) {
			LOGGER.error("Upi transaction data already exist.");
			return;
		}
		UpiTxn upiTxn = new UpiTxn();
		upiTxn.setAmount(decryptedJson.getDouble("amount"));
		upiTxn.setApprovalNumber(decryptedJson.getString("approvalNumber"));
		upiTxn.setCurrentStatusDesc(decryptedJson.getString("currentStatusDesc"));
		upiTxn.setCustRefNo(decryptedJson.getString("custRefNo"));
		upiTxn.setNpciTransId(decryptedJson.getString("npciTransId"));
		upiTxn.setOrderNo(decryptedJson.getString("orderNo"));
		upiTxn.setPayeeVPA(decryptedJson.getString("payeeVPA"));
		upiTxn.setPayerVPA(decryptedJson.getString("payerVPA"));
		upiTxn.setPspRefNo(decryptedJson.has("pspRefNo") ? decryptedJson.getString("pspRefNo") : null);
		upiTxn.setRefUrl(decryptedJson.has("refUrl") ? decryptedJson.getString("refUrl") : null);
		upiTxn.setResponseCode(decryptedJson.getString("responseCode"));
		upiTxn.setStatus(decryptedJson.getString("status"));
		upiTxn.setTxnAuthDate(decryptedJson.getString("txnAuthDate"));
		upiTxn.setTxnNote(decryptedJson.getString("txnNote"));
		upiTxn.setTxnType(decryptedJson.getString("txnType"));
		upiTxn.setUpiTransRefNo(decryptedJson.has("upiTransRefNo") ? decryptedJson.getString("upiTransRefNo") : null);
		
		
		upiTxn.setAddInfo1(decryptedJson.has("addInfo") ? decryptedJson.getJSONObject("addInfo").getString("addInfo1") : null);
		upiTxn.setAddInfo2(decryptedJson.has("addInfo") ? decryptedJson.getJSONObject("addInfo").getString("addInfo2") : null);
		upiTxn.setAddInfo3(decryptedJson.has("addInfo") ? decryptedJson.getJSONObject("addInfo").getString("addInfo3") : null);
		
		UpiTxn savedUpiTxn = upiTxnRepo.save(upiTxn);
		
		UserWallet wallet = userWalletService.findByUpiVirtualAddress(decryptedJson.getString("payeeVPA"));
		if (wallet == null || BooleanUtils.isFalse(wallet.getIsUpiActive())) {
			LOGGER.error("payeeVPA {} not found on our DB or upi not active {}", decryptedJson.getString("payeeVPA"), wallet.getIsUpiActive());
			return;
		}
		
		savedUpiTxn.setUserId(wallet.getUserId());
		upiTxnRepo.save(savedUpiTxn);
		
		saveTransaction(decryptedJson, wallet);
		
		wallet.setAmount(wallet.getAmount() + decryptedJson.getDouble("amount"));
		UserWallet savedWallet = userWalletService.save(wallet);
		
		triggerCreditMail(wallet.getUserId(), decryptedJson, savedWallet);
		
	}

	private void triggerCreditMail(Long userId, JSONObject decryptedJson, UserWallet savedWallet) {
		
		User user = userRepository.findByUserId(userId);
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Your NidhiCMS Account credited with Rs." +decryptedJson.getDouble("amount"));
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getFullName());
		model.put("txAmt", decryptedJson.getDouble("amount"));
		model.put("vAcc", savedWallet.getUpiVirtualAddress());
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", savedWallet.getAmount());
		emailService.sendMailAsync(request, model, null, EmailTemplateConstants.CREDIT_ACC);
	}

	private void saveTransaction(JSONObject decryptedJson, UserWallet wallet) {
		Transaction transaction = new Transaction();
		transaction.setAmount(decryptedJson.getDouble("amount"));
		transaction.setAmountPlusfee(decryptedJson.getDouble("amount"));
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setCreditTime(Utility.getDateTime(decryptedJson.getString("txnAuthDate"), "yyyy:MM:dd HH:mm:ss"));
		transaction.setCurrency("INR");
		transaction.setFee(0.0);
		transaction.setIsFeeTx(false);
		transaction.setMerchantId(wallet.getMerchantId());
		transaction.setStatus(decryptedJson.getString("status"));
		transaction.setRemarks(decryptedJson.getString("txnNote"));
		transaction.setTxDate(Utility.getDateTime(decryptedJson.getString("txnAuthDate"), "yyyy:MM:dd HH:mm:ss").toLocalDate());
		transaction.setTxnType(decryptedJson.getString("txnType"));
		transaction.setTxType("Cr.");
		transaction.setUniqueId(decryptedJson.getString("npciTransId"));
		transaction.setUserId(wallet.getUserId());
		transaction.setUtrNumber(decryptedJson.getString("npciTransId"));
		transaction.setPayeeName(decryptedJson.getString("payeeVPA"));
		transactionService.save(transaction);
	}

}
