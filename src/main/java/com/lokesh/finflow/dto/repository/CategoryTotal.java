package com.lokesh.finflow.dto.repository;

import java.math.BigDecimal;

public record CategoryTotal(
    String category,
    BigDecimal total
) {}
