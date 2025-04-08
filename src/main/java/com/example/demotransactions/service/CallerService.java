package com.example.demotransactions.service;

import com.example.demotransactions.model.Log;
import com.example.demotransactions.repository.LogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronizationManager;

@Service
@RequiredArgsConstructor
public class CallerService {

    private final PropagationDemoService propagationDemoService;

    private final LogRepository logRepository;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void notSupportedPropagation() {

        logRepository.save(Log.builder()
                .message("Transaction started")
                .inTransaction(isTransactionActive())
                .build());

        propagationDemoService.methodWithNotSupportedTransaction("Inside NOT_SUPPORTED");
        try {
            propagationDemoService.methodWithNeverTransaction("Inside NEVER");
        } catch (Exception e) {
            logRepository.save(Log.builder()
                    .message("Exception in NOT_SUPPORTED: " + e.getMessage())
                    .inTransaction(isTransactionActive())
                    .build());
        }
        propagationDemoService.methodWithNestedTransaction("Inside NESTED");
        logRepository.save(Log.builder()
                .message("transaction finished")
                .inTransaction(isTransactionActive())
                .build());
    }

    public void neverPropagation() {
        logRepository.save(Log.builder()
                .message("No outer transaction")
                .inTransaction(isTransactionActive())
                .build());

        propagationDemoService.methodWithNotSupportedTransaction("Inside NOT_SUPPORTED");
        try {
            propagationDemoService.methodWithNeverTransaction("Inside NEVER");
        } catch (Exception e) {
            logRepository.save(Log.builder()
                    .message("Exception in NEVER: " + e.getMessage())
                    .inTransaction(isTransactionActive())
                    .build());
        }
        propagationDemoService.methodWithNestedTransaction("Inside NESTED");
        logRepository.save(Log.builder()
                .message("Transaction finished")
                .inTransaction(isTransactionActive())
                .build());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void nestedPropagation() {
        logRepository.save(Log.builder()
                .message("Transaction started")
                .inTransaction(isTransactionActive())
                .build());
        try {
            propagationDemoService.methodWithNestedTransaction("Inside NESTED - will rollback");
            throw new RuntimeException("Simulating nested rollback");
        } catch (RuntimeException e) {
            logRepository.save(Log.builder()
                    .message("Exception in NESTED: " + e.getMessage())
                    .inTransaction(isTransactionActive())
                    .build());
        }
        logRepository.save(Log.builder()
                .message("Transaction finished")
                .inTransaction(isTransactionActive())
                .build());
    }

    private boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }
}
