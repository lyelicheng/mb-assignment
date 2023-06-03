package com.llye.mbassignment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class TransactionDto {
    private final List<Transaction> transactions;

    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    @Data
    public static class Transaction {
        private UUID id;
        private BigDecimal amount;
        private String description;
        private Date transactionDate;
        private Time transactionTime;
        private String accountNumber;
        private String customerId;
        private ZonedDateTime createdAt;

        public Transaction(com.llye.mbassignment.model.Transaction transaction) {
            this.id = transaction.getId();
            this.amount = transaction.getAmount();
            this.description = transaction.getDescription();
            this.transactionDate = transaction.getTransactionDate();
            this.transactionTime = transaction.getTransactionTime();
            this.accountNumber = transaction.getAccount().getAccountNumber();
            this.customerId = transaction.getAccount().getCustomer().getId().toString();
            this.createdAt = transaction.getCreatedAt();
        }
    }
}
