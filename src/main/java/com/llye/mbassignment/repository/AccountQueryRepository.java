package com.llye.mbassignment.repository;

import com.llye.mbassignment.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AccountQueryRepository extends JpaRepository<Account, UUID> {
}
