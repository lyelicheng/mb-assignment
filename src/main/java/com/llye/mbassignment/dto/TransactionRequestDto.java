package com.llye.mbassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRequestDto {
    private String description;
    private String trxDate;
    private String trxTime;
}
