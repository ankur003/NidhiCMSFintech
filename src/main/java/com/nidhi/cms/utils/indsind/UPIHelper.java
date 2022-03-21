package com.nidhi.cms.utils.indsind;

import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.modal.request.IndsIndRequestModal;
import com.nidhi.cms.modal.request.indusind.PaginationConfigModel;
import com.nidhi.cms.modal.request.indusind.RequestInfo;
import com.nidhi.cms.modal.request.indusind.UpiDeActivateModel;
import com.nidhi.cms.modal.request.indusind.UpiListApiRequestModel;
import com.nidhi.cms.modal.request.indusind.UpiRefundApiRequestModel;
import com.nidhi.cms.modal.request.indusind.UpiTransactionStatusModel;
import com.nidhi.cms.utils.Utility;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class UPIHelper {
	
	private static String UPI_PREFIX = "NIDHICMS";
	
	private static String UPI_POSTFIX = "@indus";
	
	private static final String APPLICATION_JSON  = "application/json";
	
	private static final Logger LOGGER = LoggerFactory.getLogger(UPIHelper.class);
	
	@Autowired
	private ApplicationConfig applicationConfig;

	public String generateUPIAddress(String merchantId, String companyName) {
		LOGGER.info("merchant id {}", merchantId);
		String compName = companyName.split(" ")[0];
		if (StringUtils.isBlank(compName)) {
			LOGGER.error("compName is null here against {}", compName);
			return null;
		}
		String upiAddress = UPI_PREFIX + merchantId.split("_")[1] + compName.trim() + UPI_POSTFIX;
		LOGGER.info("upiAddress {}", upiAddress.toLowerCase());
		
		return getAndValidateUpiAddress(upiAddress);
	}

	public String getAndValidateUpiAddress(String upiAddress) {
		OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(2, TimeUnit.MINUTES).build();
		MediaType mediaType = MediaType.parse(APPLICATION_JSON);
		Response response = null;
		try {
			okhttp3.RequestBody body = okhttp3.RequestBody.create(Utility.getEncyptedReqBodyForUpiAddressValidation(upiAddress, applicationConfig.getIndBankKey(), applicationConfig.getPgMerchantId()), mediaType);
			Request request = new Request.Builder()
					.url("https://ibluatapig.indusind.com/app/uat/web/validateVPAWeb").method("POST", body)
					.addHeader("X-IBM-Client-Id", applicationConfig.getxIBMClientIdUAT())
					.addHeader("X-IBM-Client-Secret", applicationConfig.getxIBMClientSecretUAT())
					.addHeader("Accept", APPLICATION_JSON)
					.addHeader("Content-Type", APPLICATION_JSON).build();
			 response = client.newCall(request).execute();
			String responseBody = response.body().string();
			LOGGER.info("getAndValidateUpiAddress responseBody {} ", responseBody);
			String decryptedResponse = Utility.decryptResponse(responseBody, "resp", applicationConfig.getIndBankKey());
			LOGGER.info("decryptedResponse {} ", decryptedResponse);
			if (decryptedResponse != null) {
				String status = getJsonFromString(decryptedResponse).getString("status");
				if (status.equals("VN")) {
					return upiAddress; 
				}
				return null;
			} 
		} catch (Exception e) {
			LOGGER.error("upiAddress validate api  failed {}", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}
	
	public String onBoardSubMerchant(IndsIndRequestModal indsIndRequestModal) {
		indsIndRequestModal.setPgMerchantId(applicationConfig.getPgMerchantId());
		Response response = null;
		try {
			OkHttpClient client = new OkHttpClient().newBuilder().callTimeout(2, TimeUnit.MINUTES).build();
			MediaType mediaType = MediaType.parse(APPLICATION_JSON);
			okhttp3.RequestBody body = okhttp3.RequestBody.create(
					Utility.getEncyptedReqBody(indsIndRequestModal, applicationConfig.getIndBankKey(), applicationConfig.getPgMerchantId()), mediaType);
			Request request = new Request.Builder()
					.url("https://ibluatapig.indusind.com/app/uat/web/onBoardSubMerchant").method("POST", body)
					.addHeader("X-IBM-Client-Id", applicationConfig.getxIBMClientIdUAT())
					.addHeader("X-IBM-Client-Secret", applicationConfig.getxIBMClientSecretUAT())
					.addHeader("Accept", APPLICATION_JSON).addHeader("Content-Type", APPLICATION_JSON).build();
			response = client.newCall(request).execute();
			String responseBody = response.body().string();
			String decryptedResponse = Utility.decryptResponse(responseBody, "resp", applicationConfig.getIndBankKey());
			LOGGER.info("decryptedResponse onBoardSubMerchant {} ", decryptedResponse);
			return decryptedResponse;
		} catch (Exception e) {
			LOGGER.error("upiAddress validate api failed {}", e);
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
				String encyptedReqBody = Utility.getGenericEncyptedReqBody(getDeactivateModel(usrWallet), applicationConfig.getIndBankKey(), "INDB000000003196");
				String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://indusupiuat.indusind.com:9043/upi/web/deActivateMerchant", "POST");
				String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, null, applicationConfig.getIndBankKey());
				LOGGER.info("decryptedResponse activateDeActivateUpi {} ", decryptedResponse);
				JSONObject json = Utility.getJsonFromString(decryptedResponse);
				if (json.getString("statusCode").equalsIgnoreCase("S")) {
					return "success";
				}
			} catch (Exception e) {
				LOGGER.error("activateDeActivateUpi api failed  {}", e);
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
					.addHeader("X-IBM-Client-Id", applicationConfig.getxIBMClientIdUAT())
					.addHeader("X-IBM-Client-Secret", applicationConfig.getxIBMClientSecretUAT())
					.addHeader("Accept", APPLICATION_JSON).addHeader("Content-Type", APPLICATION_JSON).build();
			response = client.newCall(request).execute();
			return response.body().string();
		} catch (Exception e) {
			LOGGER.error("upiAddress validate api failed {}", e);
		} finally {
			if (response != null) {
				response.close();
			}
		}
		return null;
	}

	private UpiDeActivateModel getDeactivateModel(UserWallet usrWallet) {
		UpiDeActivateModel upiDeActivateModel = new UpiDeActivateModel();
		upiDeActivateModel.setPgMerchantId("INDB000000003196");
		upiDeActivateModel.setSubMerchantId("INDB000000003197");
		upiDeActivateModel.setSubMerVirtualAdd(usrWallet.getUpiVirtualAddress());
		upiDeActivateModel.setAction("D");
		upiDeActivateModel.setMerchantType("DIRMER");
		upiDeActivateModel.setRemarks("de- activation");
		return upiDeActivateModel;
	}

	public String getUpiTransactionStatus(String custRefNo) {
		try {
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(getUpiTransactionStatusModel(custRefNo, applicationConfig.getPgMerchantId()), applicationConfig.getIndBankKey(), applicationConfig.getPgMerchantId());
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://ibluatapig.indusind.com/app/uat/web/meTranStatusQueryWeb", "POST");
			LOGGER.info("encryptedResponseBody getUpiTransactionStatus {} ", encryptedResponseBody);
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "resp", applicationConfig.getIndBankKey());
			LOGGER.info("decryptedResponse getUpiTransactionStatus {} ", decryptedResponse);
			JSONObject json = Utility.getJsonFromString(decryptedResponse);
			System.out.println(json);
		} catch (Exception e) {
			LOGGER.error("activateDeActivateUpi api failed {}", e);
		}
		return "failed";
	}

	private UpiTransactionStatusModel getUpiTransactionStatusModel(String custRefNo, String pgMerchantId) {
		UpiTransactionStatusModel upiTransactionStatusModel = new UpiTransactionStatusModel();
		upiTransactionStatusModel.setCustRefNo(custRefNo);
		upiTransactionStatusModel.setRequestInfo(new RequestInfo(pgMerchantId, RandomStringUtils.random(30, true, false)));
		return upiTransactionStatusModel;
	}

	public void upiListApi() { 
		try {
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(getUpiListApiModel(applicationConfig.getPgMerchantId()), applicationConfig.getIndBankKey(), applicationConfig.getPgMerchantId());
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://ibluatapig.indusind.com/app/uat/web/meTransactionHistoryWeb", "POST");
			LOGGER.info(" upiListApi encryptedResponseBody  {} ", encryptedResponseBody);
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "resp", applicationConfig.getIndBankKey());
			LOGGER.info(" upiListApi decryptedResponse  {} ", decryptedResponse);
			JSONObject json = Utility.getJsonFromString(decryptedResponse); 
			System.out.println(json);
		} catch (Exception e) {
			LOGGER.error("activateDeActivateUpi api failed {}", e); 
		}
		
	}

	private UpiListApiRequestModel getUpiListApiModel(String pgMerchantId) {
		UpiListApiRequestModel upiListApiRequestModel = new UpiListApiRequestModel();
		upiListApiRequestModel.setPgMerchantId(pgMerchantId);
		upiListApiRequestModel.setPaginationConfig(new PaginationConfigModel("01-01-2005 11:01:01", "01-01-2019 11:01:01", "1", "3"));
		return upiListApiRequestModel;
	}

	public void refundJsonApi() {
		try {
			String encyptedReqBody = Utility.getGenericEncyptedReqBody(getRefundApiModel(), applicationConfig.getIndBankKey(), "INDB000000003150");
			String encryptedResponseBody = callAndGetUpiEncryptedResponse(encyptedReqBody, "https://indusupiuat.indusind.com:9043/upi/web/meRefundJsonService", "POST");
			String decryptedResponse = Utility.decryptResponse(encryptedResponseBody, "apiResp", applicationConfig.getIndBankKey());
			LOGGER.info(" refundJsonApi decryptedResponse  {} ", decryptedResponse);
			JSONObject json = Utility.getJsonFromString(decryptedResponse);
			System.out.println(json);
		} catch (Exception e) {
			LOGGER.error("activateDeActivateUpi api failed {}", e);
		}
		
	}

	private UpiRefundApiRequestModel getRefundApiModel() {
		UpiRefundApiRequestModel upiRefundApiRequestModel = new UpiRefundApiRequestModel();
		upiRefundApiRequestModel.setCurrencyCode("");
		upiRefundApiRequestModel.setOrderNo("");
		upiRefundApiRequestModel.setOrgCustRefNo("");
		upiRefundApiRequestModel.setOrgINDrefNo("");
		upiRefundApiRequestModel.setOrgOrderNo("");
		upiRefundApiRequestModel.setPayType("");
		upiRefundApiRequestModel.setPgMerchantId("");
		upiRefundApiRequestModel.setTxnAmount("");
		upiRefundApiRequestModel.setTxnNote("");
		upiRefundApiRequestModel.setTxnType("");
		
		return upiRefundApiRequestModel;
	}

}
