package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Transaction;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class TransactionCreatedEvent implements Event {
    private UUID id;
    private BigDecimal amount;
    private String description;
    private String transactionDate;
    private String transactionTime;
    private Long version;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime timestamp;
    private String eventType;
    private String eventSource;

    public TransactionCreatedEvent(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.description = transaction.getDescription();
        this.transactionDate = transaction.getTransactionDate();
        this.transactionTime = transaction.getTransactionTime();
        this.version = transaction.getVersion();
        this.createdAt = transaction.getCreatedAt();
        this.updatedAt = transaction.getUpdatedAt();
        this.timestamp = ZonedDateTime.now();
        this.eventType = "TransactionCreated";
        this.eventSource = "BatchItemWriter";
    }

    @Override
    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    @Override
    public String getEventType() {
        return eventType;
    }

    @Override
    public String getEventSource() {
        return eventSource;
    }
}
