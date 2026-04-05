package com.lokesh.finflow.service;

import com.lokesh.finflow.dto.repository.CategoryTotal;
import com.lokesh.finflow.dto.response.DashboardSummaryResponse;
import com.lokesh.finflow.model.RecordType;
import com.lokesh.finflow.repository.FinancialRecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final FinancialRecordRepository recordRepository;

    public DashboardSummaryResponse getDashboardSummary() {

        BigDecimal totalIncome = recordRepository.sumAmountByType(RecordType.INCOME);
        BigDecimal totalExpense = recordRepository.sumAmountByType(RecordType.EXPENSE);

        // Handles null values if the database is completely empty
        if(totalIncome == null) totalIncome = BigDecimal.ZERO;
        if(totalExpense == null) totalExpense = BigDecimal.ZERO;

        BigDecimal netBalance = totalIncome.subtract(totalExpense);
        List<CategoryTotal> expenseByCategory = recordRepository.sumAmountByTypeGroupedByCategory(RecordType.EXPENSE);

        return new DashboardSummaryResponse(totalIncome, totalExpense, netBalance, expenseByCategory);
    }
}
