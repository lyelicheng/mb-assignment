package com.llye.mbassignment.service;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.model.Account;
import com.llye.mbassignment.model.Customer;
import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.AccountQueryRepository;
import com.llye.mbassignment.repository.CustomerQueryRepository;
import com.llye.mbassignment.repository.TransactionQueryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionQueryService {
    private final CustomerQueryRepository customerQueryRepository;
    private final AccountQueryRepository accountQueryRepository;
    private final TransactionQueryRepository transactionQueryRepository;

    public TransactionQueryService(CustomerQueryRepository customerQueryRepository,
                                   AccountQueryRepository accountQueryRepository,
                                   TransactionQueryRepository transactionQueryRepository) {
        this.customerQueryRepository = customerQueryRepository;
        this.accountQueryRepository = accountQueryRepository;
        this.transactionQueryRepository = transactionQueryRepository;
    }

    public TransactionDto getTransactions(Integer pageNumber,
                                          Integer pageSize,
                                          Long customerId,
                                          String accountNumber,
                                          String description) {
        List<Transaction> transactions;
        if (customerId != null) {
            transactions = findAllByCustomerId(customerId, pageNumber, pageSize);
        } else if (accountNumber != null) {
            transactions = findAllByAccountNumber(accountNumber, pageNumber, pageSize);
        } else if (description != null) {
            transactions = findAllByDescription(description, pageNumber, pageSize);
        } else {
            transactions = findAll(pageNumber, pageSize);
        }
        return toTransactionDto(transactions);
    }

    private List<Transaction> findAllByCustomerId(Long customerId, Integer pageNumber, Integer pageSize) {
        Optional<Customer> maybeCustomer = customerQueryRepository.findById(customerId);
        if (maybeCustomer.isEmpty()) {
            return Collections.emptyList();
        }

        List<Account> accounts = maybeCustomer.get()
                                              .getAccounts();

        return accounts.stream()
                       .flatMap(account -> account.getTransactions()
                                                  .stream())
                       .skip((long) pageSize * pageNumber)
                       .limit(pageSize)
                       .toList();
    }

    private List<Transaction> findAllByAccountNumber(String accountNumber, Integer pageNumber, Integer pageSize) {
        Optional<Account> maybeAccount = accountQueryRepository.findByAccountNumber(accountNumber);
        if (maybeAccount.isEmpty()) {
            return Collections.emptyList();
        }

        return maybeAccount.get()
                           .getTransactions()
                           .stream()
                           .skip((long) pageSize * pageNumber)
                           .limit(pageSize)
                           .toList();

    }

    private List<Transaction> findAllByDescription(String description, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Transaction> transactionPage = transactionQueryRepository.findByDescription(description, pageable);
        return transactionPage.getContent();
    }

    private List<Transaction> findAll(Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<Transaction> transactionPage = transactionQueryRepository.findAll(pageable);
        return transactionPage.getContent();
    }

    private TransactionDto toTransactionDto(List<Transaction> transactions) {
        List<TransactionDto.Transaction> transactionDtos = transactions.stream()
                                                                       .map(TransactionDto.Transaction::new)
                                                                       .toList();
        return TransactionDto.builder()
                             .transactions(transactionDtos)
                             .build();
    }
}
