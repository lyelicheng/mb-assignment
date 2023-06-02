package com.llye.mbassignment.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RawData {
    private String accountNumber;
    private BigDecimal trxAmount;
    private String description;
    private String trxDate;
    private String trxTime;
    private String customerId;
}
