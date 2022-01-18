package com.nidhi.cms.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.nidhi.cms.cipher.CheckNEFTjson;
import com.nidhi.cms.config.CmsConfig;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.NeftIncrementalStatus;
import com.nidhi.cms.modal.request.TransactionStatusInquiry;
import com.nidhi.cms.modal.request.WebhookRequest;
import com.nidhi.cms.repository.TxRepository;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.utils.Utility;

@Service
public class TransactionServiceImpl implements TransactionService{
	
	@Autowired
	private TxRepository txRepository;
	
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class);

	@Override
	public List<Transaction> getUserTransactions(Long userId) {
		return txRepository.findByUserId(userId);
	}
	
	@Override
	public List<Transaction> getUserTransactionsByDates(Long userId, LocalDate startDate, LocalDate endDate) {
		return txRepository.findByUserIdAndTxDateBetween(userId, startDate, endDate);
	}

	@Override
	public List<Transaction> getUserTransactionsByUniqueId(Long userId, String uniqueId) {
		return txRepository.findByUserIdAndUniqueId(userId, uniqueId);
	}
	
	@Override
	public List<Transaction> findByUserIdAndUniqueIdAndTxDateBetween(Long userId, String uniqueId, LocalDate startDate, LocalDate endDate) {
		return txRepository.findByUserIdAndUniqueIdAndTxDateBetween(userId, uniqueId, startDate, endDate);
	}

	@Override
	public List<Transaction> getUserTransactionsBytoadyDate(Long userId, LocalDate date) {
		return txRepository.findByUserIdAndTxDate(userId, date);
	}

	@Override
	public List<Transaction> findByMerchantIdAndTxDateBetween(String marchantId, LocalDate startDate,
			LocalDate endDate) {
		// List<Long> userIds = new ArrayList<>();
		List<Transaction> txns = txRepository.findByMerchantIdAndTxDateBetween(marchantId, startDate, endDate);
		if (CollectionUtils.isEmpty(txns)) {
			return Collections.emptyList();
		}
//			txns.forEach(tx -> userIds.add(tx.getUserId()));
//			
//			List<UserWallet> users= userWalletRepository.findByUserIdIn(userIds);
//			txns.forEach(txn -> users.forEach(user -> {
//	            if (txn.getUserId().equals(user.getUserId())) {
//	            	txn.setAmt(user.getAmount());
//	            }
//	        }));
		return txns;
	}

	@Override
	public List<Transaction> getAllTransactionsByDates(LocalDate startDate, LocalDate endDate) {
		return txRepository.findByTxDateBetween(startDate, endDate);
	}

	@Override
	public List<Transaction> getTransactionsByUniqueId(String uniqueId) {
		return txRepository.findByUniqueId(uniqueId);
	}

	@Override
	public Object getTransactionStatus(String uniqueId, PaymentMode paymentMode) {
		if (paymentMode == PaymentMode.RGS) {
			return neftIncrementalStatusAPi(uniqueId);
		} else if (paymentMode == PaymentMode.IFS || paymentMode == PaymentMode.RTG) {
			return transactionStatusInquiry(uniqueId);
		}
		return null;
	}

	@Override
	public Object transactionStatusInquiry(String uniqueId) {
		try {
			TransactionStatusInquiry transactionStatusInquiry = new TransactionStatusInquiry();
			transactionStatusInquiry.setAggrid(CmsConfig.CUST_ID);
			transactionStatusInquiry.setCorpid(CmsConfig.CORP_ID);
			transactionStatusInquiry.setUrn(CmsConfig.URN);
			transactionStatusInquiry.setUserid(CmsConfig.USER);
			transactionStatusInquiry.setUniqueid(uniqueId);
			String jsonAsString = Utility.createJsonRequestAsString(transactionStatusInquiry);
			LOGGER.info("[TransactionServiceImpl.transactionStatusInquiry] jsonAsString - {}", jsonAsString);

			byte[] ciphertextBytes = CheckNEFTjson.encryptJsonRequest(jsonAsString);
			String encryptedJsonResponse = CheckNEFTjson.sendThePostRequest(
					new String(org.bouncycastle.util.encoders.Base64.encode(ciphertextBytes)),
					"https://apibankingone.icicibank.com/api/Corporate/CIB/v1/TransactionInquiry", "POST");
			LOGGER.info("[TransactionServiceImpl.transactionStatusInquiry] msg - {}", encryptedJsonResponse);
			String response = CheckNEFTjson.deCryptResponse(encryptedJsonResponse);
			LOGGER.info("[TransactionServiceImpl.transactionStatusInquiry] response - {}", response);
			return response;
		} catch (Exception e) {
			LOGGER.error("[TransactionServiceImpl.transactionStatusInquiry] Exception - {}", e);
		}
		return null;
	}

	@Override
	public Object neftIncrementalStatusAPi(String utrNumber) {
		try {
			NeftIncrementalStatus neftIncrementalStatus = new NeftIncrementalStatus();
			neftIncrementalStatus.setAggrid(CmsConfig.CUST_ID);
			neftIncrementalStatus.setCorpid(CmsConfig.CORP_ID);
			neftIncrementalStatus.setUrn(CmsConfig.URN);
			neftIncrementalStatus.setUserid(CmsConfig.USER);
			neftIncrementalStatus.setUtrnumber(utrNumber);
			String jsonAsString = Utility.createJsonRequestAsString(neftIncrementalStatus);
			LOGGER.info("[TransactionServiceImpl.neftIncrementalStatusAPi] jsonAsString - {}", jsonAsString);

			byte[] ciphertextBytes = CheckNEFTjson.encryptJsonRequest(jsonAsString);
			String encryptedJsonResponse = CheckNEFTjson.sendThePostRequest(
					new String(org.bouncycastle.util.encoders.Base64.encode(ciphertextBytes)),
					"https://apibankingone.icicibank.com/api/v1/CIBNEFTStatus", "POST");
			LOGGER.info("[TransactionServiceImpl.neftIncrementalStatusAPi] msg - {}", encryptedJsonResponse);
			String response = CheckNEFTjson.deCryptResponse(encryptedJsonResponse);
			LOGGER.info("[TransactionServiceImpl.neftIncrementalStatusAPi] response - {}", response);
			return response;
		} catch (Exception e) {
			LOGGER.error("[TransactionServiceImpl.neftIncrementalStatusAPi] Exception - {}", e);
		}
		return null;
	}

	@Override
	public List<Transaction> findByUserIdAndUniqueId(Long userId, String uniqueid) {
		return txRepository.findByUserIdAndUniqueId(userId, uniqueid);
	}

	@Override
	public Transaction findByVirtualTxId(String txId) {
		return txRepository.findByVirtualTxId(txId);// "Dr."
	}

	@Override
	public Transaction saveVirtualTxId(Long userId, WebhookRequest webhookRequest) {
		return null;
	}

	@Override
	public void saveCreditTransaction(Document docWithContent, int i, User user, UserWallet userWallet) {
		Transaction transaction  =  new Transaction();
		transaction.setUserId(user.getUserId());// PaymentMode
		transaction.setReqId(docWithContent.getElementsByTagName("Request_ID").item(i).getTextContent());
		transaction.setVirtualTxId(docWithContent.getElementsByTagName("Inward_Ref_Num").item(i).getTextContent());
		transaction.setUniqueId(docWithContent.getElementsByTagName("Inward_Ref_Num").item(i).getTextContent());
		transaction.setCreditAcc(docWithContent.getElementsByTagName("Credit_AccountNo").item(i).getTextContent());
		transaction.setAmount(Double.valueOf(docWithContent.getElementsByTagName("Amount").item(i).getTextContent()));
		transaction.setAmountPlusfee(Double.valueOf(docWithContent.getElementsByTagName("Amount").item(i).getTextContent()));
		transaction.setFee(BigDecimal.valueOf(transaction.getAmountPlusfee() - transaction.getAmount()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
		transaction.setIfsc(docWithContent.getElementsByTagName("Remitter_IFSC").item(i).getTextContent());
		transaction.setPayeeName(docWithContent.getElementsByTagName("Remitter_Name").item(i).getTextContent());
		transaction.setUtrNumber(docWithContent.getElementsByTagName("Remitter_UTR").item(i).getTextContent());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setTxDate(LocalDate.now());
		transaction.setTxnType(docWithContent.getElementsByTagName("Pay_Method").item(i).getTextContent());
		transaction.setTxType("Cr.");
		transaction.setAmt(BigDecimal.valueOf(userWallet.getAmount() + transaction.getAmountPlusfee()).setScale(2, RoundingMode.HALF_DOWN).doubleValue());
		txRepository.save(transaction);
	}

}
