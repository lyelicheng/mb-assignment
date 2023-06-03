package com.llye.mbassignment.controller;

import com.llye.mbassignment.dto.TransactionDto;
import com.llye.mbassignment.dto.TransactionRequestDto;
import com.llye.mbassignment.service.TransactionQueryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

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
                                                          @RequestParam(name = "customerId", required = false) Long customerId,
                                                          @RequestParam(name = "accountNumber", required = false) String accountNumber,
                                                          @RequestParam(name = "description", required = false) String description) {
        TransactionDto transactionDto = transactionQueryService.getTransactions(pageNumber, pageSize, customerId, accountNumber, description);
        if (transactionDto != null) {
            return ResponseEntity.ok(transactionDto);
        } else {
            return ResponseEntity.notFound()
                                 .build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TransactionDto> updateTransaction(@RequestHeader(value = "api-token") String header,
                                                            @PathVariable("id") UUID id,
                                                            @RequestBody TransactionRequestDto transactionRequestDto) {
        TransactionDto transactionDto = transactionQueryService.updateTransaction(id, transactionRequestDto);
        if (transactionDto != null) {
            return ResponseEntity.ok(transactionDto);
        } else {
            return ResponseEntity.internalServerError()
                                 .build();
        }
    }
}
