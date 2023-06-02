package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Account;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AccountCreatedEvent implements Event {
    private UUID id;
    private String accountNumber;
    private String accountType;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime timestamp;
    private String eventType;
    private String eventSource;

    public AccountCreatedEvent(Account account) {
        this.id = account.getId();
        this.accountNumber = account.getAccountNumber();
        this.accountType = account.getAccountType();
        this.createdAt = account.getCreatedAt();
        this.updatedAt = account.getUpdatedAt();
        this.timestamp = ZonedDateTime.now();
        this.eventType = "AccountCreated";
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
