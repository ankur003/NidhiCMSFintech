package com.nidhi.cms.scheduler;
import java.time.LocalDateTime;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nidhi.cms.utils.indsind.UPIHelper;

@Component
public class UpiListScheduler {

	private static final Logger LOGGER = LoggerFactory.getLogger(UpiListScheduler.class);

	@Autowired
	private UPIHelper upiHelper;
	
	//@Scheduled(cron = "0 0/15 * * * ?")
	// @PostConstruct
	public void upiListScheduler() {
		LOGGER.info("UPI list Scheduler has been started at '{}'", LocalDateTime.now());
		try {
			upiHelper.upiListApi();
		} catch (final Exception exception) {
			LOGGER.error(" UPI list Scheduler An error occurred ", exception);
		} finally {
			LOGGER.info("UPI list Scheduler Scheduler has been has been completed at '{}'", LocalDateTime.now());
		}
	}

	

}