package com.llye.mbassignment.config;

import com.llye.mbassignment.model.RawData;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
@EnableBatchProcessing
public class BatchConfig {
    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory,
                       StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Value("${file.input}")
    private String fileInput;

    @Bean
    public Job importJob(JobListener listener) {
        return jobBuilderFactory.get("importJob")
                                .listener(listener)
                                .flow(importStep())
                                .end()
                                .build();
    }

    @Bean
    public Step importStep() {
        return stepBuilderFactory.get("importStep")
                                 .<RawData, RawData>chunk(10)
                                 .reader(reader())
                                 .processor(processor())
                                 .writer(writer())
                                 .build();
    }

    @Bean
    public FlatFileItemReader<RawData> reader() {
        FlatFileItemReader<RawData> reader = new FlatFileItemReader<>();
        reader.setResource(new ClassPathResource("data.csv")); // Set the input file path

        // Configure the line mapper to map each line to a Java object
        DefaultLineMapper<RawData> lineMapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|"); // Set the delimiter used in the file
        tokenizer.setNames(new String[]{"ACCOUNT_NUMBER", "TRX_AMOUNT", "DESCRIPTION", "TRX_DATE", "TRX_TIME", "CUSTOMER_ID"}); // Set the property names in the file

        BeanWrapperFieldSetMapper<RawData> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(RawData.class);

        lineMapper.setLineTokenizer(tokenizer);
        lineMapper.setFieldSetMapper(fieldSetMapper);

        reader.setLineMapper(lineMapper);

        return reader;
    }

    @Bean
    public BatchItemProcessor processor() {
        return new BatchItemProcessor();
    }

    @Bean
    public BatchItemWriter writer() {
        return new BatchItemWriter();
    }
}