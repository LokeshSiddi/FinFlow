package com.lokesh.finflow.dto.request;

import com.lokesh.finflow.model.RecordType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RecordCreateRequest(

    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than zero")
    BigDecimal amount,

    @NotNull(message = "Type is required (INCOME or EXPENSE)")
    RecordType type,

    @NotBlank(message = "Category is required")
    String category,

    @NotNull(message = "Date is required")
    LocalDate date,

    String description
) {}
