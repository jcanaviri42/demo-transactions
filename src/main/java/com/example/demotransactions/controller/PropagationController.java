package com.example.demotransactions.controller;

import com.example.demotransactions.model.Log;
import com.example.demotransactions.repository.LogRepository;
import com.example.demotransactions.service.CallerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PropagationController {

    private final CallerService callerService;

    private final LogRepository logRepository;

    @GetMapping("/not-supported")
    public String notSupported() {
        callerService.notSupportedPropagation();
        return "Called methods with REQUIRES_NEW outer transaction. Check logs.";
    }

    @GetMapping("/never")
    public String never() {
        callerService.neverPropagation();
        return "Called methods without outer transaction. Check logs.";
    }

    @GetMapping("/nested")
    public String nested() {
        callerService.nestedPropagation();
        return "Called method with NESTED and simulated rollback. Check logs.";
    }

    @GetMapping("/logs")
    public Iterable<Log> getLogs() {
        return logRepository.findAll();
    }
}
