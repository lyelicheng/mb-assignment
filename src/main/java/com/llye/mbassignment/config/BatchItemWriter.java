package com.llye.mbassignment.config;

import com.llye.mbassignment.model.RawData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.util.List;

public class BatchItemWriter <T> implements ItemWriter<RawData> {
    private static final Logger logger = LoggerFactory.getLogger(BatchItemWriter.class);

    @Override
    public void write(List<? extends RawData> list) throws Exception {
        //TODO: process loaded Java objects, e.g., persist to a database or perform other actions
    }
}
