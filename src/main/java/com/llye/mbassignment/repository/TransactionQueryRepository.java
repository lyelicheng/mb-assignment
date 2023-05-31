package com.llye.mbassignment.repository;

import com.llye.mbassignment.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TransactionQueryRepository extends JpaRepository<Transaction, UUID> {
    Page<Transaction> findByDescription(String description, Pageable pageable);
}
