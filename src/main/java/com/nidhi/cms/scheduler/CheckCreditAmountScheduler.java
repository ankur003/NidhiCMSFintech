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

import com.nidhi.cms.domain.Transaction;
import com.nidhi.cms.domain.User;
import com.nidhi.cms.domain.UserWallet;
import com.nidhi.cms.service.TransactionService;
import com.nidhi.cms.service.UserService;
import com.nidhi.cms.service.UserWalletService;

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

    @Scheduled(cron = "0 0/30 * * * ?")
    public void checkCreditAmountScheduler() {
        LOGGER.info("check Credit Amount Scheduler has been started at '{}'", LocalDateTime.now());
        try {
    		OkHttpClient client = new OkHttpClient().newBuilder().build();
    		MediaType mediaType = MediaType.parse("text/xml");
    		RequestBody body = RequestBody.create(
    				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:tem=\"http://tempuri.org/\" xmlns:eten=\"http://schemas.datacontract.org/2004/07/ETender_Pull\">\n    <soapenv:Header/>\n    <soapenv:Body>\n        <tem:FetchIecData>\n            <!--Optional:-->\n            <tem:RequestData>\n                <!--Optional:-->\n                <eten:CustomerTenderId>?</eten:CustomerTenderId>\n            </tem:RequestData>\n        </tem:FetchIecData>\n    </soapenv:Body>\n</soapenv:Envelope>",
    				mediaType);
    		Request request = new Request.Builder().url("https://ibluatapig.indusind.com/app/uat/IBLeTender")
    				.method("POST", body).addHeader("X-IBM-CLIENT-ID", "847b2573-6729-4129-ade9-e2f9aa439edc")
    				.addHeader("X-IBM-CLIENT-SECRET", "H1bQ3gP1iH4mS6wN8aJ7dH1mN8mC7bR5wJ1mC6qY5kO1lE6qY5")
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
    
	private void parseXmlDoc(Document doc) throws ParserConfigurationException, SAXException, IOException {
		doc.getDocumentElement().normalize();
		String value = doc.getElementsByTagName("a:ResponseIecData").item(0).getFirstChild().getTextContent();
		if (value != null && value.equalsIgnoreCase("NODATA")) {
			LOGGER.info("value =  '{}'", value);
			return;
		} 
		parseXmlDocWithContent(convertStringToXMLDocument(value));
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
		Transaction txn = txService.findByVirtualTxId(docWithContent.getElementsByTagName("Inward_Ref_Num").item(i).getTextContent());
		if (txn != null) {
			LOGGER.warn("txn already credited  =  '{}'", txn.getVirtualTxId());
			return;
		}
		UserWallet userWallet = userWalletService.findByVirtualId(docWithContent.getElementsByTagName("Credit_AccountNo").item(i).getTextContent());
		if (userWallet == null) {
			LOGGER.warn("userWallet  =  {}", userWallet);
			return;
		}
		User user = userService.findByUserId(userWallet.getUserId());
		txService.saveCreditTransaction(docWithContent, i, user, userWallet);
		userWalletService.updateBalance(userWallet, Double.valueOf(docWithContent.getElementsByTagName("Amount").item(i).getTextContent()));
	}

	private static Document convertStringToXMLDocument(String xmlString)
			throws ParserConfigurationException, SAXException, IOException {
		// Parser that produces DOM object trees from XML content
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		// API to obtain DOM Document instance
		DocumentBuilder builder = null;
		// Create DocumentBuilder with default configuration
		builder = factory.newDocumentBuilder();
		// Parse the content to Document object
		return  builder.parse(new InputSource(new StringReader(xmlString)));
	}


}