package com.llye.mbassignment.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

@Component
public class JobListener extends JobExecutionListenerSupport {
    private static final Logger logger = LoggerFactory.getLogger(JobListener.class);

    @Override
    public void beforeJob(JobExecution jobExecution) {
        logger.debug("job_started");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        logger.debug("job_ended");
    }
}
