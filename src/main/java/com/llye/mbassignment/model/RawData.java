package com.llye.mbassignment.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class RawData {
    private String accountNumber;
    private String trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;
}
