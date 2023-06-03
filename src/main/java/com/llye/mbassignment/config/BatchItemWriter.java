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
import com.llye.mbassignment.util.DateConverter;
import com.llye.mbassignment.util.TimeConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemWriter;

import java.time.ZonedDateTime;
import java.util.*;

public class BatchItemWriter<T> implements ItemWriter<RawData>, StepExecutionListener {
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

    private Set<String> encounteredCustomerIds;
    private Set<String> encounteredAccountNumbers;

    @Override
    public void beforeStep(StepExecution stepExecution) {
        ExecutionContext jobExecutionContext = stepExecution.getJobExecution().getExecutionContext();
        encounteredCustomerIds = (Set<String>) jobExecutionContext.get("encounteredCustomerIds");
        encounteredAccountNumbers = (Set<String>) jobExecutionContext.get("encounteredAccountNumbers");
    }

    @Override
    public void write(List<? extends RawData> list) {
        logger.debug("writing items to database ...");
        list.forEach(data -> {
            String customerId = data.getCustomerId();
            String accountNumber = data.getAccountNumber();

            // Check if customer ID has been encountered before
            Customer customer;
            if (encounteredCustomerIds.contains(customerId)) {
                Optional<Customer> maybeCustomer = customerRepository.findById(formatCustomerId(customerId));
                customer = maybeCustomer.get();
            } else {
                customer = customerRepository.save(buildCustomer(data));
                encounteredCustomerIds.add(customerId);
            }

            // Check if account number has been encountered before
            Account account;
            if (encounteredAccountNumbers.contains(accountNumber)) {
                Optional<Account> maybeAccount = accountRepository.findByAccountNumber(accountNumber);
                account = maybeAccount.get();
            } else {
                Account newAccount = buildAccount(data);
                newAccount.setCustomer(customer);
                account = accountRepository.save(newAccount);
                encounteredAccountNumbers.add(accountNumber);
            }

            Transaction newTransaction = buildTransaction(data);
            newTransaction.setAccount(account);
            Transaction transaction = transactionRepository.save(newTransaction);

            CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent(customer);
            eventBus.publish(customerCreatedEvent);
            AccountCreatedEvent accountCreatedEvent = new AccountCreatedEvent(account);
            eventBus.publish(accountCreatedEvent);
            TransactionCreatedEvent transactionCreatedEvent = new TransactionCreatedEvent(transaction);
            eventBus.publish(transactionCreatedEvent);
        });
    }

    private Long formatCustomerId(String customerId) {
        return Objects.nonNull(customerId) ? Long.parseLong(customerId) : 999L;
    }

    private Customer buildCustomer(RawData rawData) {
        return Customer.builder()
                       .id(formatCustomerId(rawData.getCustomerId()))
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
                          .amount(rawData.getTrxAmount())
                          .description(rawData.getDescription())
                          .transactionDate(DateConverter.convertStringToDate(rawData.getTrxDate()))
                          .transactionTime(TimeConverter.convertStringToTime(rawData.getTrxTime()))
                          .createdAt(ZonedDateTime.now())
                          .updatedAt(ZonedDateTime.now())
                          .build();
    }

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        return null;
    }
}
