package com.llye.mbassignment.controller;

import com.llye.mbassignment.config.BatchConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private static final Logger logger = LoggerFactory.getLogger(JobController.class);

    private final JobLauncher jobLauncher;

    private final BatchConfig batchConfig;

    public JobController(JobLauncher jobLauncher,
                         BatchConfig batchConfig) {
        this.jobLauncher = jobLauncher;
        this.batchConfig = batchConfig;
    }

    @GetMapping("/import-job")
    public void importJob(@RequestHeader(value = "api-token") String header) {
        JobParameters jobParameters = new JobParametersBuilder().addLong("time", System.currentTimeMillis())
                                                                .toJobParameters();
        try {
            jobLauncher.run(batchConfig.importJob(null), jobParameters);
        } catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException | JobParametersInvalidException e) {
            logger.error("JobController.importJob: error={} ", e.getMessage());
        }
    }
}
