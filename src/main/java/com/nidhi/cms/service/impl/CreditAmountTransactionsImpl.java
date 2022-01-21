package com.nidhi.cms.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.nidhi.cms.domain.VirtualTxn;
import com.nidhi.cms.repository.CreditAmountTransactionsRepo;
import com.nidhi.cms.service.CreditAmountTransactionsService;

@Service
public class CreditAmountTransactionsImpl implements CreditAmountTransactionsService{
	
	@Autowired
	private CreditAmountTransactionsRepo creditAmountTransactionsRepo;

	@Override
	public void save(Document docWithContent, int i, String status) {
		VirtualTxn virtualTxn = new VirtualTxn();
		virtualTxn.setAmount(docWithContent.getElementsByTagName("Amount").item(i).getTextContent());
		virtualTxn.setChallanCode(docWithContent.getElementsByTagName("CHALLAN_CODE").item(i).getTextContent());
		virtualTxn.setChallanNo(docWithContent.getElementsByTagName("CHALLAN_NO").item(i).getTextContent());
		virtualTxn.setClientAccountNo(docWithContent.getElementsByTagName("Client_AccountNo").item(i).getTextContent());
		virtualTxn.setClientName(docWithContent.getElementsByTagName("Client_Name").item(i).getTextContent());
		virtualTxn.setCreatedAt(LocalDateTime.now());
		virtualTxn.setCreditAccountNo(docWithContent.getElementsByTagName("Credit_AccountNo").item(i).getTextContent());
		virtualTxn.setCreditTime(docWithContent.getElementsByTagName("Credit_Time").item(i).getTextContent());
		virtualTxn.setInwardRefNum(docWithContent.getElementsByTagName("Inward_Ref_Num").item(i).getTextContent());
		virtualTxn.setPayMethod(docWithContent.getElementsByTagName("Pay_Method").item(i).getTextContent());
		virtualTxn.setRemitterAccountNo(docWithContent.getElementsByTagName("Remitter_AccountNo").item(i).getTextContent());
		virtualTxn.setRemitterBank(docWithContent.getElementsByTagName("Remitter_Bank").item(i).getTextContent());
		virtualTxn.setRemitterBranch(docWithContent.getElementsByTagName("Remitter_Branch").item(i).getTextContent());
		virtualTxn.setRemitterIFSC(docWithContent.getElementsByTagName("Remitter_IFSC").item(i).getTextContent());
		virtualTxn.setRemitterName(docWithContent.getElementsByTagName("Remitter_Name").item(i).getTextContent());
		virtualTxn.setRemitterUTR(docWithContent.getElementsByTagName("Remitter_UTR").item(i).getTextContent());
		virtualTxn.setRequestId(docWithContent.getElementsByTagName("Request_ID").item(i).getTextContent());
		virtualTxn.setStatus(status);
		creditAmountTransactionsRepo.save(virtualTxn);
		
	}

}
