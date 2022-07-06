package com.nidhi.cms.service;

import org.json.JSONObject;

public interface UpiTxnService {

	void saveUpiTxn(JSONObject decryptedJson);

	void callBackScheduler();

}
