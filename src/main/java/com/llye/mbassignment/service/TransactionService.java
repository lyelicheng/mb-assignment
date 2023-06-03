package com.llye.mbassignment.service;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.dto.TransactionRequestDto;
import com.llye.mbassignment.model.Transaction;
import com.llye.mbassignment.repository.TransactionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public TransactionDto updateTransaction(UUID id, TransactionRequestDto transactionRequestDto) {
        Optional<Transaction> maybeTransaction = transactionRepository.findById(id);
        if (maybeTransaction.isEmpty()) {
            return null;
        }
        Transaction transaction = maybeTransaction.get();
        Optional.ofNullable(transactionRequestDto.getDescription())
                .ifPresent(transaction::setDescription);
        Optional.ofNullable(transactionRequestDto.getTransactionDate())
                .ifPresent(transaction::setTransactionDate);
        Optional.ofNullable(transactionRequestDto.getTransactionTime())
                .ifPresent(transaction::setTransactionTime);
        Transaction updatedTransaction = transactionRepository.save(transaction);
        return TransactionDto.builder()
                             .transactions(List.of(new TransactionDto.Transaction(updatedTransaction)))
                             .build();
    }
}
