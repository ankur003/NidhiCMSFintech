package com.nidhi.cms.service.impl;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;

import com.nidhi.cms.domain.CreditAmountTransactions;
import com.nidhi.cms.repository.CreditAmountTransactionsRepo;
import com.nidhi.cms.service.CreditAmountTransactionsService;

@Service
public class CreditAmountTransactionsImpl implements CreditAmountTransactionsService{
	
	@Autowired
	private CreditAmountTransactionsRepo creditAmountTransactionsRepo;

	@Override
	public void save(Document docWithContent, int i, String status) {
		CreditAmountTransactions creditAmountTransactions = new CreditAmountTransactions();
		creditAmountTransactions.setAmount(docWithContent.getElementsByTagName("Amount").item(i).getTextContent());
		creditAmountTransactions.setChallanCode(docWithContent.getElementsByTagName("CHALLAN_CODE").item(i).getTextContent());
		creditAmountTransactions.setChallanNo(docWithContent.getElementsByTagName("CHALLAN_NO").item(i).getTextContent());
		creditAmountTransactions.setClientAccountNo(docWithContent.getElementsByTagName("Client_AccountNo").item(i).getTextContent());
		creditAmountTransactions.setClientName(docWithContent.getElementsByTagName("Client_Name").item(i).getTextContent());
		creditAmountTransactions.setCreatedAt(LocalDateTime.now());
		creditAmountTransactions.setCreditAccountNo(docWithContent.getElementsByTagName("Credit_AccountNo").item(i).getTextContent());
		creditAmountTransactions.setCreditTime(docWithContent.getElementsByTagName("Credit_Time").item(i).getTextContent());
		creditAmountTransactions.setInwardRefNum(docWithContent.getElementsByTagName("Inward_Ref_Num").item(i).getTextContent());
		creditAmountTransactions.setPayMethod(docWithContent.getElementsByTagName("Pay_Method").item(i).getTextContent());
		creditAmountTransactions.setRemitterAccountNo(docWithContent.getElementsByTagName("Remitter_AccountNo").item(i).getTextContent());
		creditAmountTransactions.setRemitterBank(docWithContent.getElementsByTagName("Remitter_Bank").item(i).getTextContent());
		creditAmountTransactions.setRemitterBranch(docWithContent.getElementsByTagName("Remitter_Branch").item(i).getTextContent());
		creditAmountTransactions.setRemitterIFSC(docWithContent.getElementsByTagName("Remitter_IFSC").item(i).getTextContent());
		creditAmountTransactions.setRemitterName(docWithContent.getElementsByTagName("Remitter_Name").item(i).getTextContent());
		creditAmountTransactions.setRemitterUTR(docWithContent.getElementsByTagName("Remitter_UTR").item(i).getTextContent());
		creditAmountTransactions.setRequestId(docWithContent.getElementsByTagName("Request_ID").item(i).getTextContent());
		creditAmountTransactions.setStatus(status);
		creditAmountTransactionsRepo.save(creditAmountTransactions);
		
	}

}
