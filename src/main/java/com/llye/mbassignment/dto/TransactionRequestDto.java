package com.llye.mbassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Time;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionRequestDto {
    private String description;
    private Date transactionDate;
    private Time transactionTime;
}
