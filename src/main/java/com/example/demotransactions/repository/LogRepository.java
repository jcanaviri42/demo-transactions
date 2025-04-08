package com.example.demotransactions.repository;

import com.example.demotransactions.model.Log;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LogRepository extends JpaRepository<Log, Long> {
}
