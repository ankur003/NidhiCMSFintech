package com.nidhi.cms.utils.indsind;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.nidhi.cms.constants.EmailTemplateConstants;
import com.nidhi.cms.constants.enums.PaymentMode;
import com.nidhi.cms.constants.enums.PaymentModeFeeType;
import com.nidhi.cms.constants.enums.SystemKey;
import com.nidhi.cms.domain.SystemConfig;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.UpiTxn;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserPaymentMode;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.domain.email.MailRequest;
import com.nidhi.cms.modal.request.IndsIndRequestModal;
import com.nidhi.cms.modal.request.PreAuthPayModel;
import com.nidhi.cms.modal.request.PreAuthPayRequestModel;
import com.nidhi.cms.modal.request.indusind.PaginationConfigModel;
import com.nidhi.cms.modal.request.indusind.RequestInfo;
import com.nidhi.cms.modal.request.indusind.UpiCollectTxRequestModel;
import com.nidhi.cms.modal.request.indusind.UpiDeActivateModel;
import com.nidhi.cms.modal.request.indusind.UpiListApiRequestModel;
import com.nidhi.cms.modal.request.indusind.UpiRefundApiRequestModel;
import com.nidhi.cms.modal.request.indusind.UpiTransactionStatusModel;
import com.nidhi.cms.modal.response.PreAuthPayResponseModel;
import com.nidhi.cms.repository.SystemConfigRepo;
import com.nidhi.cms.repository.UpiTxnRepo;
import com.nidhi.cms.repository.UserRepository;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserPaymentModeService;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.service.email.EmailService;
import com.nidhi.cms.utils.Utility;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class UPIHelper {
	
	private static final String UPI_PREFIX = "NIDHICMS.";
	
	private static final String UPI_POSTFIX = "@indus";
	
	private static final String APPLICATION_JSON  = "application/json";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UPIHelper.class);
	
	@Autowired
	private UserWalletService userWalletService;
	
	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private SystemConfigRepo systemConfigRepo;
	
	@Autowired
	private UpiTxnRepo upiTxnRepo;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserPaymentModeService userPaymentModeService;
	
	@Autowired
	private EmailService emailService;
	
	public String generateUPIAddress(String merchantId, String companyName) {
		LOGGER.info("merchant id {}", merchantId);
		String compName = companyName.split(" ")[0];
		if (StringUtils.isBlank(compName)) {
			LOGGER.error("compName is null here against {}", compName);
			return null;
		}
		String upiAddress = UPI_PREFIX + compName.trim() + merchantId.split("_")[1] + UPI_POSTFIX;
		LOGGER.info("upiAddress {}", upiAddress.toLowerCase());
		
		return getAndValidateUpiAddress(upiAddress, "R");
	}

	public String getAndValidateUpiAddress(String upiAddress, String vpaType) {
		OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(2, TimeUnit.MINUTES).build();
		MediaType mediaType = MediaType.parse(APPLICATION_JSON);
		Response response = null;
		try {
			//c0ff5a0e2000a62951400b3489fc41f2
		//	okhttp3.RequestBody body = okhttp3.RequestBody.create(Utility.getEncyptedReqBodyForUpiAddressValidation(upiAddress, applicationConfig.getIndBankKey(), applicationConfig.getPgMerchantId()), mediaType);
		
			okhttp3.RequestBody body = okhttp3.RequestBody.create(Utility.getEncyptedReqBodyForUpiAddressValidation(vpaType, upiAddress, 
					systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue()), mediaType);

			Request request = new Request.Builder()
					.url("https://apig.indusind.com/ibl/prod/upi/validateVPAWeb").method("POST", body)
					.addHeader("X-IBM-Client-Id", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_ID.name()).getValue())
					.addHeader("X-IBM-Client-Secret", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_SECRET.name()).getValue())
					.addHeader("Accept", APPLICATION_JSON)
					.addHeader("Content-Type", APPLICATION_JSON).build();
			 response = client.newCall(request).execute();
			String responseBody = response.body().string();
			LOGGER.info("getAndValidateUpiAddress responseBody {} ", responseBody);
			String decryptedResponse = Utility.decryptResponse(responseBody, "resp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
			LOGGER.info("decryptedResponse {} ", decryptedResponse);
			if (decryptedResponse != null) {
				String status = getJsonFromString(decryptedResponse).getString("status");
				if (status.equals("VN")) {
					return upiAddress; 
				}
				return null;
			} 
		} catch (Exception e) {
			LOGGER.error("upiAddress validate api  failed ", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	public String onBoardSubMerchant(IndsIndRequestModal indsIndRequestModal) {
		indsIndRequestModal.setPgMerchantId(systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(2, TimeUnit.MINUTES).build();
			MediaType mediaType = MediaType.parse(APPLICATION_JSON);
			okhttp3.RequestBody body = okhttp3.RequestBody.create(
					Utility.getEncyptedReqBody(indsIndRequestModal, systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue()), mediaType);
			Request request = new Request.Builder()
					.url("https://ibluatapig.indusind.com/app/uat/web/onBoardSubMerchant").method("POST", body)
					.addHeader("X-IBM-Client-Id", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_ID.name()).getValue())
					.addHeader("X-IBM-Client-Secret", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_SECRET.name()).getValue())
					.addHeader("Accept", APPLICATION_JSON).addHeader("Content-Type", APPLICATION_JSON).build();
			response = client.newCall(request).execute();
			String responseBody = response.body().string();
			String decryptedResponse = Utility.decryptResponse(responseBody, "resp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
			LOGGER.info("decryptedResponse onBoardSubMerchant {} ", decryptedResponse);
			return decryptedResponse;
		} catch (Exception e) {
			LOGGER.error("upiAddress validate api failed ", e);
			e.printStackTrace();
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	public static JSONObject getJsonFromString(String jsonString) {
		return new JSONObject(jsonString);
	}

	public String activateDeActivateUpi(UserWallet usrWallet, boolean isUpiActive) {
		if (BooleanUtils.isFalse(isUpiActive)) {
			try {
				String encyptedReqBody = Utility.getGenericEncyptedReqBody(getDeactivateModel(usrWallet), 
						systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), 
						systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
				String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/api/deActivateMerchant", "POST");
				String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, null, systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
				LOGGER.info("decryptedResponse activateDeActivateUpi {} ", decryptedResponse);
				JSONObject json = Utility.getJsonFromString(decryptedResponse);
				if (json.getString("statusCode").equalsIgnoreCase("S")) {
					return "success";
				}
			} catch (Exception e) {
				LOGGER.error("activate De Activate Upi api failed ", e);
			}
			
		}
		return "failed";
	}

	private String callAndGetUpiEncryptedResponse(String encyptedReqBody, String url, String method) {
		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(2, TimeUnit.MINUTES).build();
			okhttp3.RequestBody body = okhttp3.RequestBody.create(encyptedReqBody,  MediaType.parse(APPLICATION_JSON));
			Request request = new Request.Builder()
					.url(url).method(method, body)
					.addHeader("X-IBM-Client-Id", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_ID.name()).getValue())
					.addHeader("X-IBM-Client-Secret", systemConfigRepo.findBySystemKey(SystemKey.X_IBM_CLIENT_SECRET.name()).getValue())
					.addHeader("Accept", APPLICATION_JSON).addHeader("Content-Type", APPLICATION_JSON).build();
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			LOGGER.error("api failed ", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	private UpiDeActivateModel getDeactivateModel(UserWallet usrWallet) {
		UpiDeActivateModel upiDeActivateModel = new UpiDeActivateModel();
		upiDeActivateModel.setPgMerchantId(systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
		upiDeActivateModel.setSubMerchantId(usrWallet.getMerchantId());
		upiDeActivateModel.setSubMerVirtualAdd(usrWallet.getUpiVirtualAddress());
		upiDeActivateModel.setAction("D");
		upiDeActivateModel.setMerchantType("DIRMER");
		upiDeActivateModel.setRemarks("de- activation");
		return upiDeActivateModel;
	}

	public String getUpiTransactionStatus(String txVpaType, String txId) {
		try {
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(getUpiTransactionStatusModel(txVpaType, txId,  systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue()), 
					systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/upijson/meTranStatusQueryWeb", "POST");
			LOGGER.info("encryptedResponseBody getUpiTransactionStatus {} ", encryptedResponseBody);
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "resp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
			LOGGER.info("decryptedResponse getUpiTransactionStatus {} ", decryptedResponse);
			if (decryptedResponse == null) {
				return null;
			}
			JSONObject json = Utility.getJsonFromString(decryptedResponse);
			System.out.println(json);
			return json.toString();
		} catch (Exception e) {
			LOGGER.error("activateDeActivateUpi api failed ", e);
		}
		return null;
	}

	private UpiTransactionStatusModel getUpiTransactionStatusModel(String txVpaType, String txId, String pgMerchantId) {
		UpiTransactionStatusModel upiTransactionStatusModel = new UpiTransactionStatusModel();
		if (txVpaType.equals("Cust_Ref_No")) {
			upiTransactionStatusModel.setCustRefNo(txId);
		}
		if (txVpaType.equals("npci_Tran_Id")) {
			upiTransactionStatusModel.setNpciTranId(txId);
		}
		upiTransactionStatusModel.setRequestInfo(new RequestInfo(pgMerchantId, RandomStringUtils.random(30, true, false)));
		return upiTransactionStatusModel;
	}

	public void upiListApi() { 
		try {
			String pgMerchantId = systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue();
			
			JSONObject json = getUpiListResult(pgMerchantId, "1", "50"); 
			
			if (!json.has("transDetails")) {
				LOGGER.info(" upiListApi no data available  ");
				return;
			}
			if (Long.valueOf(json.getString("total_records")) > 50) {
				
				int noOfRecords = Integer.parseInt(json.getString("total_records"));
				int recordPerPage = 50;
				int fromIndex = 0;
				int toIndex = 0;
				
				
		        int noOfPages = (int)Math.ceil(noOfRecords * 1.0 / recordPerPage);
		        for(int i = 1 ; i<= noOfPages; i++) {
		        	if (i == 1) {
		        		fromIndex = 0;
		        	} else {
		        		fromIndex = toIndex;
		        	}
		        	toIndex = recordPerPage * i;
		        	
		        	getUpiListResult(pgMerchantId, String.valueOf(fromIndex), String.valueOf(toIndex)); 
		        	
		        }
				
			} else {
				processUPIData(json);   
			}
			
			 
			
			// callback passed
			// 
		} catch (Exception e) {
			LOGGER.error("activateDeActivateUpi api failed ", e); 
		}
		
	}

	private void processUPIData(JSONObject json) {
		JSONArray transDetails = json.getJSONArray("transDetails");
		for (int i = 0; i < transDetails.length(); i++) {
			JSONObject transDetail = transDetails.getJSONObject(i);
			processUpiListApi(transDetail);
		}
	}

	private JSONObject getUpiListResult(String pgMerchantId, String fromIndex, String toIndex) throws Exception {
		String encyptedReqBody = Utility.getGenericEncyptedReqBody(getUpiListApiModel(pgMerchantId, fromIndex, toIndex), systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), 
				systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
		String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/upijson/metransactionhistoryweb", "POST");
		LOGGER.info(" upiListApi encryptedResponseBody  {} ", encryptedResponseBody);
		String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "resp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
		LOGGER.info(" upiListApi decryptedResponse  {} ", decryptedResponse);
		return Utility.getJsonFromString(decryptedResponse);
	}
	
	private void processUpiListApi(JSONObject transDetail) {
		
		if (transDetail.has("txnAuthDate") && transDetail.has("npciTransId") && transDetail.has("custRefNo")) {
			List<UpiTxn> upiDetails = upiTxnRepo.findByTxnAuthDate(transDetail.getString("txnAuthDate"));
			if (CollectionUtils.isNotEmpty(upiDetails)) {
				for (UpiTxn upiTrans : upiDetails) {
					if (upiTrans.getNpciTransId() != null
							&& upiTrans.getNpciTransId().equals(transDetail.getString("npciTransId"))
							&& upiTrans.getCustRefNo() != null
							&& upiTrans.getCustRefNo().equals(transDetail.getString("custRefNo"))) {
						LOGGER.error("Upi transaction data already exist [List API].");
						return;
					}
				}
			}
		}
		
		if (transDetail.has("txnId")) {
			List<Transaction> transaction = transactionService.getTransactionsByUniqueId(transDetail.getString("txnId"));
			if (CollectionUtils.isNotEmpty(transaction)) {
				LOGGER.warn("upiListApi transaction already found in transaction table against  uniqueId {}", transDetail.getString("txnId")); 
				return;
			}
		}
		
		saveUpiTransaction(transDetail);
		
	}

	private void saveUpiTransaction(JSONObject transDetail) {
		UpiTxn upiTxn = new UpiTxn();
		upiTxn.setAmount(transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount"));
		upiTxn.setCurrentStatusDesc((transDetail.has("reason_desc") ? transDetail.getString("reason_desc") : transDetail.getString("currentStatusDesc")));
		upiTxn.setCustRefNo(transDetail.getString("custRefNo"));
		upiTxn.setPayeeVPA(transDetail.has("payeeVirtualAddress") ? transDetail.getString("payeeVirtualAddress") : transDetail.getString("payeeVPA"));
		upiTxn.setPayerVPA(transDetail.has("payerVirtualAddress") ? transDetail.getString("payerVirtualAddress") : transDetail.getString("payerVPA"));
		upiTxn.setResponseCode(transDetail.has("response_code") ? transDetail.getString("response_code") : transDetail.getString("responseCode"));
		upiTxn.setStatus(transDetail.has("txnStatus") ? transDetail.getString("txnStatus") : transDetail.getString("status"));
		upiTxn.setTxnAuthDate(transDetail.has("trnDate") ? transDetail.getString("trnDate") : transDetail.getString("txnAuthDate"));
		upiTxn.setTxnNote(transDetail.getString("txnNote"));
		upiTxn.setTxnType(transDetail.getString("txnType"));
		upiTxn.setUpiTransRefNo(transDetail.has("trnRefNo") ? transDetail.get("trnRefNo").toString() : transDetail.get("upiTransRefNo").toString());
		
		
		upiTxn.setAddInfo2(transDetail.getString("addiInfo2"));
		upiTxn.setAddInfo3(transDetail.getString("addiInfo3"));
		
		UpiTxn savedUpiTxn = upiTxnRepo.save(upiTxn);
		
		UserWallet wallet = userWalletService.findByUpiVirtualAddress(transDetail.has("payeeVirtualAddress") ? transDetail.getString("payeeVirtualAddress") : transDetail.getString("payeeVPA"));
		if (wallet == null || BooleanUtils.isFalse(wallet.getIsUpiActive())) {
			LOGGER.error("payeeVPA {} not found on our DB or upi not active", transDetail.has("payeeVirtualAddress") ? transDetail.getString("payeeVirtualAddress") : transDetail.getString("payeeVPA"));
			return;
		}
		
		savedUpiTxn.setUserId(wallet.getUserId());
		upiTxnRepo.save(savedUpiTxn);
		
		saveTransaction(transDetail, wallet, wallet.getAmount() + (transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount")));
		
		wallet.setAmount(wallet.getAmount() + (transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount")));
		UserWallet savedWallet = userWalletService.save(wallet);
		
		User user = userRepository.findByUserId(wallet.getUserId());
		
		Double fee = null;
		
		UserPaymentMode userPaymentMode = userPaymentModeService.getUserPaymentMode(user, PaymentMode.UPI_CREDIT);
		if (userPaymentMode != null) {
			if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.PERCENTAGE)) {
				fee = getFee(userPaymentMode.getFee(), transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount"));
				savedWallet.setAmount(savedWallet.getAmount() - fee);
				savedWallet = userWalletService.save(savedWallet);
				
			} 
			if (userPaymentMode.getPaymentModeFeeType().equals(PaymentModeFeeType.FLAT)) {
				fee = userPaymentMode.getFee();
				savedWallet.setAmount(savedWallet.getAmount() - fee);
				savedWallet = userWalletService.save(savedWallet);
				
			}
			Transaction transaction = new Transaction();
			transaction.setAmount(fee);
			transaction.setAmountPlusfee(fee);
			transaction.setCreatedAt(LocalDateTime.now());
			transaction.setCreditTime(LocalDateTime.now());
			transaction.setCurrency("INR");
			transaction.setFee(fee);
			transaction.setIsFeeTx(true);
			transaction.setMerchantId(savedWallet.getMerchantId());
			transaction.setStatus(transDetail.has("txnStatus") ? transDetail.getString("txnStatus") : transDetail.getString("status"));
			transaction.setRemarks(transDetail.has("reason_desc") ? transDetail.getString("reason_desc") : transDetail.getString("currentStatusDesc"));
			transaction.setTxDate(LocalDate.now());
			transaction.setTxType("Dr.");
			transaction.setUserId(savedWallet.getUserId());//
			transaction.setUniqueId(transDetail.has("txnId") ? transDetail.getString("txnId") : transDetail.getString("npciTransId"));
			transaction.setPayeeName(transDetail.has("payeeName") ? transDetail.getString("payeeName") : null);
			transaction.setAmt(savedWallet.getAmount());
			transactionService.save(transaction);
			
		} 
		
		triggerCreditMail(wallet.getUserId(), transDetail, savedWallet);
		
		if (StringUtils.isNotBlank(wallet.getMerchantCallBackUrl())) {
			callbackInitaite(upiTxn, wallet);
		}
		
	}
	
	private Double getFee(Double feePercent, Double amount) {
		BigDecimal amt = BigDecimal.valueOf(amount).setScale(2, RoundingMode.HALF_DOWN);
		BigDecimal feePer = BigDecimal.valueOf(feePercent).setScale(2, RoundingMode.HALF_DOWN);
		return BigDecimal.valueOf((amt.doubleValue() * feePer.doubleValue()) / 100).setScale(2, RoundingMode.HALF_DOWN).doubleValue();
	}
	
	private void saveTransaction(JSONObject transDetail, UserWallet wallet, Double amt) {
		Transaction transaction = new Transaction();
		transaction.setAmount(transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount"));
		transaction.setAmountPlusfee(transDetail.has("txnAmount") ? transDetail.getDouble("txnAmount") : transDetail.getDouble("amount"));
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setCreditTime(Utility.getDateTime(transDetail.has("trnDate") ? transDetail.getString("trnDate") : transDetail.getString("txnAuthDate"), "dd:MM:yyyy HH:mm:ss"));
		transaction.setCurrency("INR");
		transaction.setFee(0.0);
		transaction.setIsFeeTx(false);
		transaction.setMerchantId(wallet.getMerchantId());
		transaction.setStatus(transDetail.has("txnStatus") ? transDetail.getString("txnStatus") : transDetail.getString("status"));
		transaction.setRemarks(transDetail.getString("txnNote"));//
		transaction.setTxDate(Utility.getDateTime(transDetail.has("trnDate") ? transDetail.getString("trnDate") : transDetail.getString("txnAuthDate"), "dd:MM:yyyy HH:mm:ss").toLocalDate());
		transaction.setTxnType(transDetail.getString("txnType"));
		transaction.setTxType("Cr.");
		transaction.setUniqueId(transDetail.has("txnId") ? transDetail.getString("txnId") : transDetail.getString("npciTransId"));
		transaction.setUserId(wallet.getUserId());
		transaction.setPayeeName(transDetail.has("payeeName") ? transDetail.getString("payeeName") : null);
		transaction.setAmt(amt);
		transactionService.save(transaction);
	}
	
	private void triggerCreditMail(Long userId, JSONObject decryptedJson, UserWallet savedWallet) {
		
		User user = userRepository.findByUserId(userId);
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Your NidhiCMS Account credited with Rs." + (decryptedJson.has("txnAmount") ? decryptedJson.getDouble("txnAmount") : decryptedJson.getDouble("amount")));
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getFullName());
		model.put("txAmt", decryptedJson.has("txnAmount") ? decryptedJson.getDouble("txnAmount") : decryptedJson.getDouble("amount"));
		model.put("vAcc", savedWallet.getWalletUuid());
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", savedWallet.getAmount());
		emailService.sendMailAsync(request, model, null, EmailTemplateConstants.CREDIT_ACC);
	}
	
	private void callbackInitaite(UpiTxn upiTxn, UserWallet wallet) {
		try {
			RestTemplate restTemplate = new RestTemplate();
			
			User user = userRepository.findByUserId(wallet.getUserId());
			
			HttpHeaders headers = new HttpHeaders();
			headers.set("apiKey", user.getApiKey());      

			HttpEntity<UpiTxn> request = new HttpEntity<>(upiTxn, headers);
			
			LOGGER.info("callback request {}, with apikey {} and user id {} ", upiTxn, user.getApiKey(), user.getUserId());
			
			Object map = restTemplate.postForObject(wallet.getMerchantCallBackUrl(), request, Object.class);
			LOGGER.info("callback response {}", map);
			
			LOGGER.info("callback recieved");
			upiTxn.setDoesCallbackSuccess(Boolean.TRUE);
			upiTxnRepo.save(upiTxn);
			LOGGER.info("Does Call back Success -- TRUE Updated");
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("An error occured while callback ", e);
			upiTxn.setDoesCallbackSuccess(Boolean.FALSE);
			upiTxnRepo.save(upiTxn);
			LOGGER.info("Does Call back failed -- FALSE Updated");
		}
	}

//	private String getListAPiStartTime() {
//		SystemConfig systemConfig = systemConfigRepo.findBySystemKey(SystemKey.LIST_API_LAST_RUN_TIME.name());
//		if (systemConfig == null) {
//			return LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"));
//		}
//		return systemConfig.getValue();
//	}

//	private void updateListAPiStartTime() {
//		SystemConfig systemConfig = systemConfigRepo.findBySystemKey(SystemKey.LIST_API_LAST_RUN_TIME.name());
//		if (systemConfig == null) {
//			systemConfig = new SystemConfig();
//			systemConfig.setSystemKey(SystemKey.LIST_API_LAST_RUN_TIME.name());
//		}
//		systemConfig.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")));
//		systemConfigRepo.save(systemConfig);
//	}

	private UpiListApiRequestModel getUpiListApiModel(String pgMerchantId, String fromIndex, String toIndex) {
		UpiListApiRequestModel upiListApiRequestModel = new UpiListApiRequestModel();
		upiListApiRequestModel.setPgMerchantId(pgMerchantId);
		upiListApiRequestModel.setPaginationConfig(new PaginationConfigModel(
				LocalDateTime.of(LocalDate.now(), LocalTime.MIDNIGHT).plusSeconds(1).format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), 
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), fromIndex, toIndex));
		return upiListApiRequestModel;
	}

	public JSONObject refundJsonApi(UpiRefundApiRequestModel upiRefundApiRequestModel, UserWallet usrWallet, User user) {
		try {
			upiRefundApiRequestModel.setPayType("P2P");
			upiRefundApiRequestModel.setPgMerchantId(systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
			upiRefundApiRequestModel.setTxnType("PAY");
			upiRefundApiRequestModel.setCurrencyCode("INR");
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(upiRefundApiRequestModel, systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), 
					systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/upirfd/meRefundJsonService", "POST");
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "apiResp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
			LOGGER.info(" refundJsonApi decryptedResponse  {} ", decryptedResponse);
			JSONObject json = Utility.getJsonFromString(decryptedResponse);
			LOGGER.info(" refund json {} ", json);
			if (json.getString("status").equals("S")) {
				debitTransaction(upiRefundApiRequestModel, usrWallet, json.getString("txnId"));
				UserWallet updatedWallet = updateWallet(usrWallet, upiRefundApiRequestModel.getTxnAmount());
				triggerDebitAccountNotification(user, Double.valueOf(upiRefundApiRequestModel.getTxnAmount()), updatedWallet);
			}
			
			return json;
		} catch (Exception e) {
			e.printStackTrace();
			LOGGER.error("refundJsonApi api failed ", e);
		}
		
		return null;
		
	}
	
	private UserWallet updateWallet(UserWallet usrWallet, String txnAmount) {
		usrWallet.setAmount(usrWallet.getAmount() - Double.valueOf(txnAmount));
		return userWalletService.save(usrWallet);
	}

	private void debitTransaction(UpiRefundApiRequestModel upiRefundApiRequestModel, UserWallet wallet, String txnId) {
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(upiRefundApiRequestModel.getTxnAmount()));
		transaction.setAmountPlusfee(Double.valueOf(upiRefundApiRequestModel.getTxnAmount()));
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setCreditTime(LocalDateTime.now());
		transaction.setCurrency("INR");
		transaction.setFee(0.0);
		transaction.setIsFeeTx(false);
		transaction.setMerchantId(wallet.getMerchantId());
		transaction.setStatus("S");
		transaction.setRemarks("Refund transaction");
		transaction.setTxDate(LocalDate.now());
		transaction.setTxnType(upiRefundApiRequestModel.getTxnType());
		transaction.setTxType("Dr.");
		transaction.setUserId(wallet.getUserId());
		transaction.setUniqueId(txnId);
		transaction.setUtrNumber(null);
		transaction.setPayeeName(null);
		transaction.setAmt(wallet.getAmount());
		transactionService.save(transaction);
		
	}

	public PreAuthPayResponseModel preAuthApy(PreAuthPayRequestModel preAuthPayRequestModel, UserWallet usrWallet, Double fee, User user) throws Exception {
			PreAuthPayModel preAuthModel = new PreAuthPayModel();
			String pgMerchantId = systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue();
			preAuthModel.setPgMerchantId(pgMerchantId);
			preAuthModel.setTxnType("PAY");
			preAuthModel.setCurrencyCode("INR");
			preAuthModel.setMcc("7392");
			preAuthModel.setPayerAccNo("201015240719");
			preAuthModel.setPayerIfsc("INDB0000824");
			
			preAuthModel.setAddInfo2(preAuthPayRequestModel.getAddInfo2());
			preAuthModel.setAddInfo3(preAuthPayRequestModel.getAddInfo3());
			preAuthModel.setOrderNo(preAuthPayRequestModel.getOrderNo());
			preAuthModel.setPayeeAccNo(preAuthPayRequestModel.getPayeeAccNo());
			preAuthModel.setPayeeEmail(preAuthPayRequestModel.getPayeeEmail());
			preAuthModel.setPayeeIfsc(preAuthPayRequestModel.getPayeeIfsc());
			preAuthModel.setPayeeMobNo(preAuthPayRequestModel.getPayeeMobNo());
			preAuthModel.setPayeeVpa(preAuthPayRequestModel.getPayeeVpa());
			preAuthModel.setPayeeVPAType(preAuthPayRequestModel.getPayeeVPAType());
			preAuthModel.setPaymentType(preAuthPayRequestModel.getPaymentType());
			preAuthModel.setTxnAmount(preAuthPayRequestModel.getTxnAmount());
			preAuthModel.setTxnNote(preAuthPayRequestModel.getTxnNote());
			preAuthModel.setPayeeName(preAuthPayRequestModel.getPayeeName());
			preAuthModel.setPayeeAadhar(preAuthPayRequestModel.getPayeeAadhar());
			
			LOGGER.info(" pre auth Api response to bank  {} ", preAuthModel);
			SystemConfig bankKey = systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name());
			
			LocalDateTime beforCall = LocalDateTime.now();
			LOGGER.info(" befor call  {} ", beforCall);
			
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(preAuthModel, bankKey.getValue(), pgMerchantId);
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/upijson/mePayServerApi", "POST");
			LOGGER.info("encryptedResponseBody  {} ", encryptedResponseBody);
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "apiResp", bankKey.getValue());
			LOGGER.info(" pre auth Api decryptedResponse  {} ", decryptedResponse);
			JSONObject json = Utility.getJsonFromString(decryptedResponse);
			
			LocalDateTime afterCall = LocalDateTime.now();
			LOGGER.info(" afterCall  {} ", afterCall);
			
			LOGGER.info(" time taken  {} ", ChronoUnit.SECONDS.between(beforCall, afterCall));
			
			if (json.getString("status").equals("S")) {
				PreAuthPayResponseModel preAuthPayResponseModel = Utility.getJavaObject(json.toString(), PreAuthPayResponseModel.class);
				
				processPreAuthPay(preAuthModel, preAuthPayResponseModel, usrWallet, fee, user);
				
				return preAuthPayResponseModel;
			} else {
				LOGGER.warn("pre auth Api failed  {} ", decryptedResponse);
				return Utility.getJavaObject(json.toString(), PreAuthPayResponseModel.class);
			}
	}

	private void processPreAuthPay(PreAuthPayModel preAuthPayRequestModel, PreAuthPayResponseModel preAuthPayResponseModel, UserWallet usrWallet, Double fee, User user) {
		debitTransactionForPreAuth(preAuthPayRequestModel, preAuthPayResponseModel, usrWallet);
		UserWallet updatedWallet = updateWalletForPreAuth(usrWallet, preAuthPayResponseModel.getTxnAmount(), fee);
		if (fee != null) {
			feeTransactionForPreAuth(preAuthPayResponseModel, usrWallet, fee, updatedWallet.getAmount());
		} 
		triggerDebitAccountNotification(user, Double.valueOf(preAuthPayRequestModel.getTxnAmount()), updatedWallet);
	}
	
	private void triggerDebitAccountNotification(User user, Double txnAmount, UserWallet userWallet) {
		if (StringUtils.isBlank(user.getUserEmail())) {
			LOGGER.error("[UserServiceImpl.triggerDebitAccountNotification] user email is blank - {}", user.getUserEmail());
			return;
		}
		MailRequest request = new MailRequest();
		request.setName(user.getFullName());
		request.setSubject("Your NidhiCMS Account Debited with Rs." +txnAmount);
		request.setTo(new String[] { user.getUserEmail() });
		Map<String, Object> model = new HashMap<>();
		model.put("name", user.getFullName());
		model.put("txAmt", txnAmount);
		model.put("accNo", userWallet.getWalletUuid());
		model.put("createdAt", LocalDateTime.now().toString().replace("T", " "));
		model.put("amt", userWallet.getAmount());
		emailService.sendMailAsync(request, model, null, EmailTemplateConstants.DEBIT_ACC);
		
	}
	
	private void feeTransactionForPreAuth(
			PreAuthPayResponseModel preAuthPayResponseModel, UserWallet usrWallet, Double fee, Double amt) {
		Transaction transaction = new Transaction();
		transaction.setAmount(fee);
		transaction.setAmountPlusfee(fee);
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setCreditTime(LocalDateTime.now());
		transaction.setCurrency("INR");
		transaction.setFee(fee);
		transaction.setIsFeeTx(true);
		transaction.setMerchantId(usrWallet.getMerchantId());
		transaction.setStatus(preAuthPayResponseModel.getStatus());
		transaction.setRemarks(preAuthPayResponseModel.getStatusDesc());
		transaction.setTxDate(LocalDate.now());
		transaction.setTxType("Dr.");
		transaction.setUserId(usrWallet.getUserId());
		transaction.setUniqueId(preAuthPayResponseModel.getTxnId());
		transaction.setPayeeName(preAuthPayResponseModel.getPayeeAccName());
		transaction.setAmt(amt);
		transactionService.save(transaction);
		
	}

	private UserWallet updateWalletForPreAuth(UserWallet usrWallet, String txnAmount, Double fee) {
		Double amt = usrWallet.getAmount() - Double.valueOf(txnAmount);
		if (fee != null) {
			usrWallet.setAmount(amt - fee);
		} else {
			usrWallet.setAmount(amt);
		}
		return userWalletService.save(usrWallet);
		
	}

	private void debitTransactionForPreAuth(PreAuthPayModel preAuthPayRequestModel, PreAuthPayResponseModel preAuthPayResponseModel, UserWallet usrWallet) {
		Transaction transaction = new Transaction();
		transaction.setAmount(Double.valueOf(preAuthPayRequestModel.getTxnAmount()));
		transaction.setAmountPlusfee(Double.valueOf(preAuthPayRequestModel.getTxnAmount()));
		transaction.setCreatedAt(LocalDateTime.now());
		transaction.setCreditTime(LocalDateTime.now());
		transaction.setCurrency("INR");
		transaction.setFee(0.0);
		transaction.setIsFeeTx(false);
		transaction.setMerchantId(usrWallet.getMerchantId());
		transaction.setStatus(preAuthPayResponseModel.getStatus());
		transaction.setRemarks(preAuthPayResponseModel.getStatusDesc());
		transaction.setTxDate(LocalDate.now());
		transaction.setTxType("Dr.");
		transaction.setUserId(usrWallet.getUserId());
		transaction.setUniqueId(preAuthPayResponseModel.getTxnId());
		transaction.setPayeeName(preAuthPayResponseModel.getPayeeAccName());
		transaction.setAmt(usrWallet.getAmount());
		transactionService.save(transaction);
	}

	public void upiCollectTx(UpiCollectTxRequestModel upiCollectTxRequestModel, UserWallet usrWallet, User user) throws Exception {
		String encyptedReqBody = Utility.getGenericEncyptedReqBody(upiCollectTxRequestModel, 
				systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue(), 
				systemConfigRepo.findBySystemKey(SystemKey.INDUS_PGMERCHANTID.name()).getValue());
		String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://apig.indusind.com/ibl/prod/upijson/meCollectInitiateWeb", "POST");
		LOGGER.info("encryptedResponseBody  {} ", encryptedResponseBody);
		String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "apiResp", systemConfigRepo.findBySystemKey(SystemKey.INDS_IND_BANK_KEY.name()).getValue());
		LOGGER.info(" upiCollectTx decryptedResponse  {} ", decryptedResponse);
		JSONObject json = Utility.getJsonFromString(decryptedResponse);
			
	}

}
