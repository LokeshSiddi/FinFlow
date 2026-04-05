package com.lokesh.finflow.dto.response;

import com.lokesh.finflow.model.RecordType;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecordResponse(
        Long id,
        BigDecimal amount,
        RecordType type,
        String category,
        LocalDate date,
        String description,
        String createdByEmail
) {}
