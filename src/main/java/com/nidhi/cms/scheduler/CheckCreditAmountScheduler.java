package com.nidhi.cms.scheduler;

import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDateTime;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.nidhi.cms.config.ApplicationConfig;
import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.service.CreditAmountTransactionsService;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.UserWalletService;
import com.nidhi.cms.utils.Utility;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Component
public class CheckCreditAmountScheduler {

    private static final Logger LOGGER = LoggerFactory.getLogger(CheckCreditAmountScheduler.class);

    @Autowired
    private TransactionService txService;
    
    @Autowired
    private UserWalletService userWalletService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private ApplicationConfig applicationConfig;
    
    @Autowired
    private  CreditAmountTransactionsService creditAmountTransactionsService;

    @Scheduled(cron = "0 0/10 * * * ?")
    public void checkCreditAmountScheduler() {
        LOGGER.info("check Credit Amount Scheduler has been started at '{}'", LocalDateTime.now());
        try {
    		OkHttpClient client = new OkHttpClient().newBuilder().build();
    		MediaType mediaType = MediaType.parse("text/xml");
    		RequestBody body = RequestBody.create(
    				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" "
    				+ "xmlns:tem=\"http://tempuri.org/\" xmlns:eten=\"http://schemas.datacontract.org/2004/07/ETender_Pull\">\n    "
    				+ "<soapenv:Header/>\n    <soapenv:Body>\n        <tem:FetchIecData>\n            <!--Optional:-->\n            <tem:RequestData>\n               "
    				+ "<!--Optional:-->\n                <eten:CustomerTenderId>NIDCM</eten:CustomerTenderId>\n            </tem:RequestData>\n        </tem:FetchIecData>\n   "
    				+ " </soapenv:Body>\n</soapenv:Envelope>",
    				mediaType);
    		Request request = new Request.Builder().url("https://apig.indusind.com/ibl/prod/IBLeTender")
    				.method("POST", body)
					.addHeader("X-IBM-CLIENT-ID", applicationConfig.getxIBMClientId())
					.addHeader("X-IBM-CLIENT-SECRET", applicationConfig.getxIBMClientSecret())
    				.addHeader("SOAPAction", "http://tempuri.org/IIBLeTender/FetchIecData")
    				.addHeader("Content-Type", "text/xml").build();
			Response response = client.newCall(request).execute();
			String responseXmlString = response.body().string();
			LOGGER.info("Response as string -  '{}'",responseXmlString);
			if (StringUtils.isBlank(responseXmlString) || responseXmlString.contains("errorResponse")) {
				LOGGER.error("An error occurred {} ", responseXmlString);
				return;
			}
			Document doc = convertStringToXMLDocument(responseXmlString.replaceAll("&lt;", "<"));
			parseXmlDoc(doc);
        } catch (final Exception exception) {
            LOGGER.error(" check Credit Amount Scheduler An error occurred ", exception);
        } finally {
            LOGGER.info("Check Credit Amount Scheduler has been has been completed at '{}'", LocalDateTime.now());
        }
    }
    
	private void parseXmlDoc(Document doc)  {
		doc.getDocumentElement().normalize();
		String value = doc.getElementsByTagName("a:ResponseIecData").item(0).getFirstChild().getTextContent();
		if (value != null && value.contains("NODATA")) {
			LOGGER.info("value =  '{}'", value);
			return;
		} 
		parseXmlDocWithContent(doc);
	}

	private void parseXmlDocWithContent(Document docWithContent) {
		int count = docWithContent.getElementsByTagName("Transaction").getLength();
		LOGGER.info("Transaction count =  '{}'", count);
		if (count <= 0) {
			LOGGER.info("count =  '{}'", count);
			return;
		}
		for (int i= 0 ; i< count ; i++) {
			createTransactionAndUpdateBalance(docWithContent, i);
		}
	}

	private void createTransactionAndUpdateBalance(Document docWithContent, int i) {
		LocalDateTime creditTime = Utility.getDateTime(docWithContent.getElementsByTagName("Credit_Time").item(i).getTextContent());
		if (creditTime == null) {
			LOGGER.warn("creditTime is null against =  {}", docWithContent.getElementsByTagName("Credit_Time").item(i).getTextContent());
			return;
		}
		Transaction txn = txService.findByCreditTime(creditTime);
		if (txn != null 
				&& txn.getCreditTime() != null 
				&& txn.getCreditTime().isEqual(creditTime)
				&& txn.getUtrNumber() != null 
				&& txn.getUtrNumber().equals(docWithContent.getElementsByTagName("Remitter_UTR").item(i).getTextContent())) {
			LOGGER.warn("txn already credited  with Inward_Ref_Num - {} and created date time - {} ", txn.getVirtualTxId(), txn.getCreditTime());
			return;
		}
		UserWallet userWallet = userWalletService.findByVirtualId(docWithContent.getElementsByTagName("Credit_AccountNo").item(i).getTextContent());
		if (userWallet == null) {
			LOGGER.warn("userWallet  =  {}", userWallet);
			creditAmountTransactionsService.save(docWithContent, i, "FAILED");
			updateStatusFailedCallBack(docWithContent, i);
			return;
		}
		User user = userService.findByUserId(userWallet.getUserId());
		txService.saveCreditTransaction(docWithContent, i, user, userWallet);
		userWalletService.updateBalance(userWallet, Double.valueOf(docWithContent.getElementsByTagName("Amount").item(i).getTextContent()));
		creditAmountTransactionsService.save(docWithContent, i, "SUCCESS");
		updateStatusSuccessCallBack(docWithContent, i);
		
	}

	private void updateStatusSuccessCallBack(Document docWithContent, int i) {
		try {
			String reqId = docWithContent.getElementsByTagName("Request_ID").item(i).getTextContent();
			if (StringUtils.isBlank(reqId)) {
				LOGGER.error("reqId is not valid   =  {}, escaping update client status SOAP CALL ", reqId);
				return;
			}
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/xml");
			RequestBody body = RequestBody.create(
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:eten=\"http://schemas.datacontract.org/2004/07/ETender_Pull\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <tem:UpdateClientResponse>\r\n         <!--Optional:-->\r\n         <tem:RequestData>\r\n            <!--Optional:-->\r\n            <eten:CustomerResponse>\r\n                <![CDATA[<IECResponse>\r\n                    <TRANSACTION>\r\n                        "
					+ "<RequestId>"+reqId+"</RequestId>\r\n                        "
							+ "<ResponseCode>000</ResponseCode>\r\n                        "
							+ "<ResponseDesc>success</ResponseDesc>\r\n                        "
							+ "<ResponseId>R000</ResponseId>\r\n                    </TRANSACTION>\r\n                </IECResponse>]]>\r\n            </eten:CustomerResponse>\r\n            <!--Optional:-->\r\n            <eten:CustomerTenderId>NIDCM</eten:CustomerTenderId>\r\n         </tem:RequestData>\r\n      </tem:UpdateClientResponse>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>",
					mediaType);
			Request request = new Request.Builder().url("https://apig.indusind.com/ibl/prod/IBLeTender")
					.method("POST", body).addHeader("Content-Type", "text/xml")
					.addHeader("X-IBM-CLIENT-ID", applicationConfig.getxIBMClientId())
					.addHeader("X-IBM-CLIENT-SECRET", applicationConfig.getxIBMClientSecret())
					.addHeader("SOAPAction", "http://tempuri.org/IIBLeTender/UpdateClientResponse").build();
			 client.newCall(request).execute();
			 LOGGER.info("successfully update for reqId ............................   =  {} ", reqId);
		} catch (Exception e) {
			LOGGER.error("error ocuured during client status update call, {} , ", e);
		}
		
	}
	
	private void updateStatusFailedCallBack(Document docWithContent, int i) {
		try {
			String reqId = docWithContent.getElementsByTagName("Request_ID").item(i).getTextContent();
			if (StringUtils.isBlank(reqId)) {
				LOGGER.error("reqId is not valid   =  {}, escaping update client status SOAP CALL ", reqId);
				return;
			}
			OkHttpClient client = new OkHttpClient().newBuilder().build();
			MediaType mediaType = MediaType.parse("text/xml");
			RequestBody body = RequestBody.create(
					"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:eten=\"http://schemas.datacontract.org/2004/07/ETender_Pull\">\r\n   <soapenv:Header/>\r\n   <soapenv:Body>\r\n      <tem:UpdateClientResponse>\r\n         <!--Optional:-->\r\n         <tem:RequestData>\r\n            <!--Optional:-->\r\n            <eten:CustomerResponse>\r\n                <![CDATA[<IECResponse>\r\n                    <TRANSACTION>\r\n                        "
					+ "<RequestId>"+reqId+"</RequestId>\r\n                        "
							+ "<ResponseCode>001</ResponseCode>\r\n                        "
							+ "<ResponseDesc>failed</ResponseDesc>\r\n                        "
							+ "<ResponseId>R006</ResponseId>\r\n                    </TRANSACTION>\r\n                </IECResponse>]]>\r\n            </eten:CustomerResponse>\r\n            <!--Optional:-->\r\n            <eten:CustomerTenderId>NIDCM</eten:CustomerTenderId>\r\n         </tem:RequestData>\r\n      </tem:UpdateClientResponse>\r\n   </soapenv:Body>\r\n</soapenv:Envelope>",
					mediaType);
			Request request = new Request.Builder().url("https://apig.indusind.com/ibl/prod/IBLeTender")
					.method("POST", body).addHeader("Content-Type", "text/xml")
					.addHeader("X-IBM-CLIENT-ID", applicationConfig.getxIBMClientId())
					.addHeader("X-IBM-CLIENT-SECRET", applicationConfig.getxIBMClientSecret())
					.addHeader("SOAPAction", "http://tempuri.org/IIBLeTender/UpdateClientResponse").build();
			 client.newCall(request).execute();
			 LOGGER.info("successfully update for reqId ............................   =  {} ", reqId);
		} catch (Exception e) {
			LOGGER.error("error ocuured during client status update call, {} , ", e);
		}
		
	}

	private static Document convertStringToXMLDocument(String xmlString)
			throws ParserConfigurationException, SAXException, IOException {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// API to obtain DOM Document instance
		DocumentBuilder builder = factory.newDocumentBuilder();
		// Parse the content to Document object
		return  builder.parse(new InputSource(new StringReader(xmlString)));
	}


}