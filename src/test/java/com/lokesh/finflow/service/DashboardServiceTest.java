package com.lokesh.finflow.service;

import com.lokesh.finflow.dto.response.DashboardSummaryResponse;
import com.lokesh.finflow.model.RecordType;
import com.lokesh.finflow.repository.FinancialRecordRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DashboardServiceTest {

    @Mock
    private FinancialRecordRepository recordRepository;

    @InjectMocks
    private DashboardService dashboardService;

    @Test
    void getDashboardSummary_ShouldCalculateNetBalanceCorrectly() {
        // 1. Arrange: Mock the database responses
        when(recordRepository.sumAmountByType(RecordType.INCOME)).thenReturn(new BigDecimal("5000.00"));
        when(recordRepository.sumAmountByType(RecordType.EXPENSE)).thenReturn(new BigDecimal("1500.00"));

        // Mocking the category list (we can return an empty list for this math test)
        when(recordRepository.sumAmountByTypeGroupedByCategory(RecordType.EXPENSE)).thenReturn(List.of());

        // 2. Act: Call the service method
        DashboardSummaryResponse response = dashboardService.getDashboardSummary();

        // 3. Assert: Verify the math
        assertNotNull(response);
        assertEquals(new BigDecimal("5000.00"), response.totalIncome());
        assertEquals(new BigDecimal("1500.00"), response.totalExpense());
        assertEquals(new BigDecimal("3500.00"), response.netBalance()); // 5000 - 1500 = 3500
    }

    @Test
    void getDashboardSummary_ShouldHandleNullsIfDatabaseIsEmpty() {
        // Arrange: Database returns null when there are no records
        when(recordRepository.sumAmountByType(RecordType.INCOME)).thenReturn(null);
        when(recordRepository.sumAmountByType(RecordType.EXPENSE)).thenReturn(null);
        when(recordRepository.sumAmountByTypeGroupedByCategory(RecordType.EXPENSE)).thenReturn(List.of());

        // Act
        DashboardSummaryResponse response = dashboardService.getDashboardSummary();

        // Assert: The service should convert nulls to ZERO
        assertEquals(BigDecimal.ZERO, response.totalIncome());
        assertEquals(BigDecimal.ZERO, response.totalExpense());
        assertEquals(BigDecimal.ZERO, response.netBalance());
    }
}
