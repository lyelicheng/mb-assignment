package com.llye.mbassignment.service;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.TransactionQueryRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TransactionQueryService {
    private final TransactionQueryRepository transactionQueryRepository;

    public TransactionQueryService(TransactionQueryRepository transactionQueryRepository) {
        this.transactionQueryRepository = transactionQueryRepository;
    }

    @Transactional(readOnly = true)
    public TransactionDto getTransactions(Integer pageNumber,
                                          Integer pageSize,
                                          Long customerId,
                                          String accountNumber,
                                          String description) {
        List<Transaction> transactions = transactionQueryRepository.findAll();
        List<Transaction> filteredTrasactions = transactions.stream()
                                                            .filter(transaction -> isFilterMatched(transaction, customerId, accountNumber, description))
                                                            .skip((long) pageSize * pageNumber)
                                                            .limit(pageSize)
                                                            .toList();
        return toTransactionDto(filteredTrasactions);
    }

    private boolean isFilterMatched(Transaction transaction, Long customerId, String accountNumber, String description) {
        boolean match = true;
        if (customerId != null) {
            match = customerId.compareTo(transaction.getAccount()
                                                    .getCustomer()
                                                    .getId()) == 0;
        }
        if (accountNumber != null) {
            match = match && accountNumber.equalsIgnoreCase(transaction.getAccount()
                                                                       .getAccountNumber());
        }
        if (description != null) {
            match = match && description.equalsIgnoreCase(transaction.getDescription());
        }
        return match;
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
