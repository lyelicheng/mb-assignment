package com.llye.mbassignment.event;

import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.TransactionQueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.ZonedDateTime;
import java.util.UUID;

public class TransactionCreatedEventHandler implements EventHandler {
    private static final Logger logger = LoggerFactory.getLogger(TransactionCreatedEventHandler.class);

    private final TransactionQueryRepository transactionQueryRepository;

    public TransactionCreatedEventHandler(TransactionQueryRepository transactionQueryRepository) {
        this.transactionQueryRepository = transactionQueryRepository;
    }

    @Override
    public boolean canHandle(Event event) {
        return event instanceof TransactionCreatedEvent;
    }

    @Override
    public void handle(Event event) {
        logger.debug("Transaction Created Event Handling ...");
        TransactionCreatedEvent transactionCreatedEvent = (TransactionCreatedEvent) event;
        UUID id = transactionCreatedEvent.getId();
        BigDecimal amount = transactionCreatedEvent.getAmount();
        String description = transactionCreatedEvent.getDescription();
        Date transactionDate = transactionCreatedEvent.getTransactionDate();
        Time transactionTime = transactionCreatedEvent.getTransactionTime();
        Long version = transactionCreatedEvent.getVersion();
        ZonedDateTime createdAt = transactionCreatedEvent.getCreatedAt();
        ZonedDateTime updatedAt = transactionCreatedEvent.getUpdatedAt();

        // Update the read model by creating a new transaction entry
        Transaction transaction = Transaction.builder()
                                             .id(id)
                                             .amount(amount)
                                             .description(description)
                                             .transactionDate(transactionDate)
                                             .transactionTime(transactionTime)
                                             .version(version)
                                             .createdAt(createdAt)
                                             .updatedAt(updatedAt)
                                             .build();
        transactionQueryRepository.save(transaction);
    }
}
