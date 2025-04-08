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
public class PropagationDemoService {

    private final LogRepository logRepository;

    private boolean isTransactionActive() {
        return TransactionSynchronizationManager.isActualTransactionActive();
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void methodWithNotSupportedTransaction(String message) {
        logRepository.save(Log.builder()
                .message("(NOT_SUPPORTED)")
                .inTransaction(isTransactionActive())
                .build());
    }

    @Transactional(propagation = Propagation.NEVER)
    public void methodWithNeverTransaction(String message) {
        logRepository.save(Log.builder()
                .message("(NEVER)")
                .inTransaction(isTransactionActive())
                .build());
    }

    @Transactional(propagation = Propagation.NESTED)
    public void methodWithNestedTransaction(String message) {
        logRepository.save(Log.builder()
                .message("(NESTED)")
                .inTransaction(isTransactionActive())
                .build());
    }
}
