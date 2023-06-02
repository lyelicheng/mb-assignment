package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Account;
import com.llye.mbassignment.repository.AccountQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZonedDateTime;
import java.util.UUID;

public class AccountCreatedEventHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(AccountCreatedEventHandler.class);

    private final AccountQueryRepository accountQueryRepository;

    public AccountCreatedEventHandler(AccountQueryRepository accountQueryRepository) {
        this.accountQueryRepository = accountQueryRepository;
    }

    @Override
    public boolean canHandle(Event event) {
        return event instanceof AccountCreatedEvent;
    }

    @Override
    public void handle(Event event) {
        logger.debug("Account Created Event Handling ...");
        AccountCreatedEvent accountCreatedEvent = (AccountCreatedEvent) event;
        UUID id = accountCreatedEvent.getId();
        String accountNumber = accountCreatedEvent.getAccountNumber();
        String accountType = accountCreatedEvent.getAccountType();
        ZonedDateTime createdAt = accountCreatedEvent.getCreatedAt();
        ZonedDateTime updatedAt = accountCreatedEvent.getUpdatedAt();

        // Update the read model by creating a new account entry
        Account account = Account.builder()
                                 .id(id)
                                 .accountNumber(accountNumber)
                                 .accountType(accountType)
                                 .createdAt(createdAt)
                                 .updatedAt(updatedAt)
                                 .build();
        accountQueryRepository.save(account);
    }
}
