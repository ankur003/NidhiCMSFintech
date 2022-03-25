package com.nidhi.cms.scheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.SchedulerCustomInfo;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.service.email.EmailService;

@Component
public class TransactionInquiryScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionInquiryScheduler.class);

	@Autowired
	private TransactionService txService;

	@Autowired
	private UserWalletService userWalletService;
	
	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;

	@Scheduled(cron = "0 0/15 * * * ?")
	public void transactionInquiryScheduler() {
		LOGGER.info("transaction Inquiry Scheduler has been started at '{}'", LocalDateTime.now());
		try {
			List<Transaction> transactions = txService.findByStatusAndResponseAndSchedulerCustomInfoAndIsFeeTx("FAILED", "BACKEND_CONNECTION_TIMEOUT", SchedulerCustomInfo.FAILED_CHECK_AGAIN.name(), false);
			if (CollectionUtils.isEmpty(transactions)) {
				LOGGER.info("transaction Inquiry Scheduler transactions , no data found ");
			}
			for (Transaction transaction : transactions) {
				LOGGER.info("transaction Inquiry Scheduler transactions against txId {}, and userId {},  " , transaction.getTransactionId(), transaction.getUserId());
				LOGGER.info("uniqueId {} ," , transaction.getUniqueId());
				processTransactions(transaction);
			}
		} catch (final Exception exception) {
			LOGGER.error(" transaction Inquiry Scheduler An error occurred ", exception);
		} finally {
			LOGGER.info("transaction Inquiry Scheduler has been has been completed at '{}'", LocalDateTime.now());
		}
	}

	private void processTransactions(Transaction transaction) {
		String response = txService.transactionStatusInquiry(transaction.getUniqueId());
		LOGGER.info("[transactionInquiryScheduler] response {}", response);
		if (response == null) {
			return;
		}
		JSONObject jsonObject = new JSONObject(response);
		if (jsonObject.getString("STATUS").equalsIgnoreCase("SUCCESS") && jsonObject.getString("RESPONSE").equalsIgnoreCase("SUCCESS")) {
			transaction.setStatus(jsonObject.getString("STATUS"));
			transaction.setResponse(jsonObject.getString("RESPONSE"));
			transaction.setUtrNumber(jsonObject.getString("UTRNUMBER"));
			transaction.setSchedulerCustomInfo(SchedulerCustomInfo.SUCCESS_NO_CHECK_AGAIN.name());
			txService.save(transaction);
		} else {
			transaction.setStatus(jsonObject.getString("STATUS"));
			transaction.setResponse(jsonObject.getString("RESPONSE"));
			transaction.setSchedulerCustomInfo(SchedulerCustomInfo.BALANCE_REVERSED_NO_CHECK_AGAIN.name());
			txService.save(transaction);
			UserWallet userWallet = updateUserWallet(transaction);
			createReverseTx(transaction, userWallet);
		}
		
	}

	private void createReverseTx(Transaction reverseTransaction, UserWallet userWallet) {
		Transaction transaction  =  new Transaction();
		transaction.setUserId(reverseTransaction.getUserId());
		transaction.setUniqueId(reverseTransaction.getUniqueId());
		transaction.setCreditAcc(reverseTransaction.getCreditAcc());
		transaction.setAmount(reverseTransaction.getAmountPlusfee());
		transaction.setAmountPlusfee(reverseTransaction.getAmountPlusfee());
		transaction.setIfsc(reverseTransaction.getIfsc());
		transaction.setPayeeName(reverseTransaction.getPayeeName());
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setTxDate(LocalDate.now());
		transaction.setTxType("Cr.");
		transaction.setMerchantId(userWallet.getMerchantId());
		transaction.setAmt(userWallet.getAmount());
		txService.save(transaction);
		
	}

	private UserWallet updateUserWallet(Transaction transaction) {
		UserWallet userWallet = userWalletService.findByUserId(transaction.getUserId());
		userWallet.setAmount(userWallet.getAmount() + transaction.getAmountPlusfee());
		UserWallet upadtedWallet = userWalletService.save(userWallet);
		triggerCreditMail(transaction.getUserId(), transaction.getAmountPlusfee(), upadtedWallet);
		return upadtedWallet;
	}
	
	
	private void triggerCreditMail(Long userId, Double amt, UserWallet savedWallet) {
		User user = userRepository.findByUserId(userId);
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Your NidhiCMS Account credited with Rs." + amt);
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getFullName());
		model.put("txAmt", amt);
		model.put("vAcc", savedWallet.getWalletUuid());
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", savedWallet.getAmount());
		emailService.sendMailAsync(request, model, null, EmailTemplateConstants.CREDIT_ACC);
	}

}