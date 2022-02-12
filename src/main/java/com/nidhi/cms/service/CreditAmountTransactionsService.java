package com.nidhi.cms.service;

import org.w3c.dom.Document;

import com.nidhi.cms.domain.VirtualTxn;

public interface CreditAmountTransactionsService {

	void save(Document docWithContent, int i, String status);

	VirtualTxn findByRemitterUTR(String remitterUtr);

}
