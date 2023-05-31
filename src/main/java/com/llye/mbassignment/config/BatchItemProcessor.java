package com.llye.mbassignment.config;

import com.llye.mbassignment.model.RawData;
import org.springframework.batch.item.ItemProcessor;

public class BatchItemProcessor<T> implements ItemProcessor<RawData, RawData> {

    @Override
    public RawData process(RawData rawData) {
        return rawData;
    }
}
