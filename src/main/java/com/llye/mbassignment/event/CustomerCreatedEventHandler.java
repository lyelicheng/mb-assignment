package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Customer;
import com.llye.mbassignment.repository.CustomerQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;

public class CustomerCreatedEventHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(CustomerCreatedEventHandler.class);

    private final CustomerQueryRepository customerQueryRepository;

    public CustomerCreatedEventHandler(CustomerQueryRepository customerQueryRepository) {
        this.customerQueryRepository = customerQueryRepository;
    }

    @Override
    public boolean canHandle(Event event) {
        return event instanceof CustomerCreatedEvent;
    }

    @Override
    public void handle(Event event) {
        logger.debug("Customer Created Event Handling ...");
        CustomerCreatedEvent customerCreatedEvent = (CustomerCreatedEvent) event;
        Long id = customerCreatedEvent.getId();
        String firstName = customerCreatedEvent.getFirstName();
        String lastName = customerCreatedEvent.getLastName();
        ZonedDateTime createdAt = customerCreatedEvent.getCreatedAt();
        ZonedDateTime updatedAt = customerCreatedEvent.getUpdatedAt();

        // Update the read model by creating a new customer entry
        Customer customer = Customer.builder()
                                  .id(id)
                                  .firstName(firstName)
                                  .lastName(lastName)
                                  .createdAt(createdAt)
                                  .updatedAt(updatedAt)
                                  .build();
        customerQueryRepository.save(customer);
    }
}
