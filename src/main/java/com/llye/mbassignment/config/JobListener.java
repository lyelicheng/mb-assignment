package com.llye.mbassignment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class JobListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);
    public static final String ENCOUNTERED_CUSTOMER_IDS_KEY = "encounteredCustomerIds";
    public static final String ENCOUNTERED_ACCOUNT_NUMBERS_KEY = "encounteredAccountNumbers";

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.debug("job_started");
        Set<String> encounteredCustomerIds = new HashSet<>();
        jobExecution.getExecutionContext().put(ENCOUNTERED_CUSTOMER_IDS_KEY, encounteredCustomerIds);

        Set<String> encounteredAccountNumbers = new HashSet<>();
        jobExecution.getExecutionContext().put(ENCOUNTERED_ACCOUNT_NUMBERS_KEY, encounteredAccountNumbers);
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.debug("job_ended");
    }
}
