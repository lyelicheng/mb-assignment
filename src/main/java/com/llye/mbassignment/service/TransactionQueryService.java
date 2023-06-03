package com.llye.mbassignment.service;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.dto.TransactionRequestDto;
import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.TransactionQueryRepository;
import com.llye.mbassignment.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionQueryService {
    private final TransactionQueryRepository transactionQueryRepository;
    private final TransactionRepository transactionRepository;

    public TransactionQueryService(TransactionQueryRepository transactionQueryRepository,
                                   TransactionRepository transactionRepository) {
        this.transactionQueryRepository = transactionQueryRepository;
        this.transactionRepository = transactionRepository;
    }

    @Transactional(readOnly = true)
    public TransactionDto getTransactions(Integer pageNumber,
                                          Integer pageSize,
                                          Long customerId,
                                          String accountNumber,
                                          String description) {
        List<Transaction> transactions = transactionQueryRepository.findAll();
        List<Transaction> filteredTrasactions = transactions.stream()
                                                            .filter(transaction -> isFilterMatch(transaction, customerId, accountNumber, description))
                                                            .skip((long) pageSize * pageNumber)
                                                            .limit(pageSize)
                                                            .toList();
        return toTransactionDto(filteredTrasactions);
    }

    private boolean isFilterMatch(Transaction transaction, Long customerId, String accountNumber, String description) {
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

    public TransactionDto updateTransaction(UUID id, TransactionRequestDto transactionRequestDto) {
        Optional<Transaction> maybeTransaction = transactionQueryRepository.findById(id);
        if (maybeTransaction.isPresent()) {
            Transaction transaction = maybeTransaction.get();
            transaction.setDescription(transactionRequestDto.getDescription());
            transaction.setTransactionDate(transactionRequestDto.getTrxDate());
            transaction.setTransactionTime(transactionRequestDto.getTrxTime());
            transactionRepository.save(transaction);
        }
        return null;
    }

}
