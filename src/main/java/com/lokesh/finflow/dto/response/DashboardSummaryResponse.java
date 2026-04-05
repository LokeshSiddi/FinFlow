package com.lokesh.finflow.dto.response;

import com.lokesh.finflow.dto.repository.CategoryTotal;

import java.math.BigDecimal;
import java.util.List;

public record DashboardSummaryResponse(
        BigDecimal totalIncome,
        BigDecimal totalExpense,
        BigDecimal netBalance,
        List<CategoryTotal> expenseByCategory
) {}
