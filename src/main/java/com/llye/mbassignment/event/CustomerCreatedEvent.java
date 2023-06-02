package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class CustomerCreatedEvent implements Event {
    private Long id;
    private String firstName;
    private String lastName;
    private ZonedDateTime createdAt;
    private ZonedDateTime updatedAt;
    private ZonedDateTime timestamp;
    private String eventType;
    private String eventSource;

    public CustomerCreatedEvent(Customer customer) {
        this.id = customer.getId();
        this.firstName = customer.getFirstName();
        this.lastName = customer.getLastName();
        this.createdAt = customer.getCreatedAt();
        this.updatedAt = customer.getUpdatedAt();
        this.timestamp = ZonedDateTime.now();
        this.eventType = "CustomerCreated";
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
