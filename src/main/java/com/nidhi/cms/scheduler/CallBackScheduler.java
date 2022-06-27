package com.nidhi.cms.scheduler;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nidhi.cms.service.UpiTxnService;

@Component
public class CallBackScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpiListScheduler.class);

	@Autowired
	private UpiTxnService upiTxnService;
	
	@Scheduled(cron = "0 0/120 * * * ?")
	public void callBackScheduler() {
		LOGGER.info("Call Back Scheduler has been started at '{}'", LocalDateTime.now());
		try {
			upiTxnService.callBackScheduler();
		} catch (final Exception exception) {
			exception.printStackTrace();
			LOGGER.error(" Call Back Scheduler An error occurred ", exception);
		} finally {
			LOGGER.info("Call Back Scheduler has been has been completed at '{}'", LocalDateTime.now());
		}
	}

	

}