package com.llye.mbassignment.config;

import com.llye.mbassignment.event.AccountCreatedEvent;
import com.llye.mbassignment.event.CustomerCreatedEvent;
import com.llye.mbassignment.event.EventBus;
import com.llye.mbassignment.event.TransactionCreatedEvent;
import com.llye.mbassignment.model.Account;
import com.llye.mbassignment.model.Customer;
import com.llye.mbassignment.model.RawData;
import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.AccountRepository;
import com.llye.mbassignment.repository.CustomerRepository;
import com.llye.mbassignment.repository.TransactionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemWriter;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class BatchItemWriter<T> implements ItemWriter<RawData> {
    private static final Logger logger = LoggerFactory.getLogger(BatchItemWriter.class);

    private final EventBus eventBus;
    private final CustomerRepository customerRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;

    public BatchItemWriter(EventBus eventBus,
                           CustomerRepository customerRepository,
                           AccountRepository accountRepository,
                           TransactionRepository transactionRepository) {
        this.eventBus = eventBus;
        this.customerRepository = customerRepository;
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
    }

    @Override
    public void write(List<? extends RawData> list) {
        logger.debug("writing items to database ...");
        List<Customer> customers = new ArrayList<>();
        List<Account> accounts = new ArrayList<>();
        List<Transaction> transactions = new ArrayList<>();
        list.forEach(data -> {
            customers.add(buildCustomer(data));
            accounts.add(buildAccount(data));
            transactions.add(buildTransaction(data));
        });
        customerRepository.saveAll(customers);
        // Publish the CustomerCreatedEvent
        customers.forEach(customer -> {
            CustomerCreatedEvent event = new CustomerCreatedEvent(customer);
            eventBus.publish(event);
        });

        accountRepository.saveAll(accounts);
        // Publish the AccountCreatedEvent
        accounts.forEach(account -> {
            AccountCreatedEvent event = new AccountCreatedEvent(account);
            eventBus.publish(event);
        });

        transactionRepository.saveAll(transactions);
        // Publish the TransactionCreatedEvent
        transactions.forEach(transaction -> {
            TransactionCreatedEvent event = new TransactionCreatedEvent(transaction);
            eventBus.publish(event);
        });
    }

    private Customer buildCustomer(RawData rawData) {
        Long customerId = Objects.nonNull(rawData.getCustomerId()) ? Long.parseLong(rawData.getCustomerId()) : 999L;
        return Customer.builder()
                       .id(customerId)
                       .firstName("John")
                       .lastName("Smith")
                       .createdAt(ZonedDateTime.now())
                       .updatedAt(ZonedDateTime.now())
                       .build();
    }

    private Account buildAccount(RawData rawData) {
        return Account.builder()
                       .id(UUID.randomUUID())
                       .accountNumber(rawData.getAccountNumber())
                       .accountType("Saving")
                       .createdAt(ZonedDateTime.now())
                       .updatedAt(ZonedDateTime.now())
                       .build();
    }

    private Transaction buildTransaction(RawData rawData) {
        return Transaction.builder()
                          .id(UUID.randomUUID())
                          .amount(new BigDecimal(rawData.getTrxAmount()))
                          .description(rawData.getDescription())
                          .transactionDate(rawData.getTrxDate())
                          .transactionTime(rawData.getTrxTime())
                          .createdAt(ZonedDateTime.now())
                          .updatedAt(ZonedDateTime.now())
                          .build();
    }
}
