package com.llye.mbassignment.controller;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.service.TransactionQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    private final TransactionQueryService transactionQueryService;

    public TransactionController(TransactionQueryService transactionQueryService) {
        this.transactionQueryService = transactionQueryService;
    }

    @GetMapping()
    public ResponseEntity<TransactionDto> getTransactions(@RequestParam(name = "pageNumber", required = false, defaultValue = "0") Integer pageNumber,
                                                          @RequestParam(name = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                          @RequestParam(name = "customerId", required = false, defaultValue = "222") Long customerId,
                                                          @RequestParam(name = "accountNumber", required = false, defaultValue = "8872838283") String accountNumber,
                                                          @RequestParam(name = "description", required = false, defaultValue = "FUND TRANSFER") String description) {
        TransactionDto transactionDto = transactionQueryService.getTransactions(pageNumber, pageSize, customerId, accountNumber, description);
        if (transactionDto != null) {
            return ResponseEntity.ok(transactionDto);
        } else {
            return ResponseEntity.notFound()
                                 .build();
        }
    }
}
