package com.nidhi.cms.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.nidhi.cms.utils.indsind.UPIHelper;

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
	
	@Autowired
	private UPIHelper upiHelper;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UpiTxnServiceImpl.class);

	@Override
	public void saveUpiTxn(JSONObject decryptedJson) {
		JSONObject decryptedJsonResp = decryptedJson.getJSONObject("apiResp");
		LOGGER.info("orderNo {}", decryptedJsonResp.getString("orderNo"));
		LOGGER.info("ncpiTransId {}", decryptedJsonResp.getString("npciTransId"));
		UpiTxn upiTxnData = upiTxnRepo.findByOrderNoOrNpciTransId(decryptedJsonResp.getString("orderNo"), decryptedJsonResp.getString("npciTransId"));
		if (upiTxnData != null) {
			LOGGER.error("Upi transaction data already exist.");
			return;
		}
		UpiTxn upiTxn = new UpiTxn();
		upiTxn.setAmount(decryptedJsonResp.getDouble("amount"));
		upiTxn.setApprovalNumber(decryptedJsonResp.getString("approvalNumber"));
		upiTxn.setCurrentStatusDesc(decryptedJsonResp.getString("currentStatusDesc"));
		upiTxn.setCustRefNo(decryptedJsonResp.getString("custRefNo"));
		upiTxn.setNpciTransId(decryptedJsonResp.getString("npciTransId"));
		upiTxn.setOrderNo(decryptedJsonResp.getString("orderNo"));
		upiTxn.setPayeeVPA(decryptedJsonResp.getString("payeeVPA"));
		upiTxn.setPayerVPA(decryptedJsonResp.getString("payerVPA"));
		upiTxn.setPspRefNo(decryptedJsonResp.has("pspRefNo") ? decryptedJsonResp.getString("pspRefNo") : null);
		upiTxn.setRefUrl(decryptedJsonResp.has("refUrl") ? decryptedJsonResp.getString("refUrl") : null);
		upiTxn.setResponseCode(decryptedJsonResp.getString("responseCode"));
		upiTxn.setStatus(decryptedJsonResp.getString("status"));
		upiTxn.setTxnAuthDate(decryptedJsonResp.getString("txnAuthDate"));
		upiTxn.setTxnNote(decryptedJsonResp.getString("txnNote"));
		upiTxn.setTxnType(decryptedJsonResp.getString("txnType"));
		upiTxn.setUpiTransRefNo(decryptedJsonResp.has("upiTransRefNo") ? decryptedJsonResp.get("upiTransRefNo").toString() : null);
		
		
		upiTxn.setAddInfo1(decryptedJsonResp.has("addInfo") ? decryptedJsonResp.getJSONObject("addInfo").getString("addInfo1") : null);
		upiTxn.setAddInfo2(decryptedJsonResp.has("addInfo") ? decryptedJsonResp.getJSONObject("addInfo").getString("addInfo2") : null);
		upiTxn.setAddInfo3(decryptedJsonResp.has("addInfo") ? decryptedJsonResp.getJSONObject("addInfo").getString("addInfo3") : null);
		
		UpiTxn savedUpiTxn = upiTxnRepo.save(upiTxn);
		
		if (BooleanUtils.isFalse(decryptedJsonResp.has("orderNo")) || StringUtils.isBlank(decryptedJsonResp.getString("orderNo"))) {
			LOGGER.warn("orderNo not Found refund api started against payeeVPA {} ", decryptedJsonResp.getString("payeeVPA"));
			upiHelper.refundJsonApi();
			return;
		}
		
		UserWallet wallet = userWalletService.findByUpiVirtualAddress(decryptedJsonResp.getString("payeeVPA"));
		if (wallet == null || BooleanUtils.isFalse(wallet.getIsUpiActive())) {
			LOGGER.error("payeeVPA {} not found on our DB or upi not active", decryptedJsonResp.getString("payeeVPA"));
			return;
		}
		
		savedUpiTxn.setUserId(wallet.getUserId());
		upiTxnRepo.save(savedUpiTxn);
		
		saveTransaction(decryptedJsonResp, wallet);
		
		wallet.setAmount(wallet.getAmount() + decryptedJsonResp.getDouble("amount"));
		UserWallet savedWallet = userWalletService.save(wallet);
		
		triggerCreditMail(wallet.getUserId(), decryptedJsonResp, savedWallet);
		
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
