package com.llye.mbassignment.repository;

import com.llye.mbassignment.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerQueryRepository extends JpaRepository<Customer, Long> {
}
