package com.lokesh.finflow.repository;

import com.lokesh.finflow.dto.repository.CategoryTotal;
import com.lokesh.finflow.model.FinancialRecord;
import com.lokesh.finflow.model.RecordType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface FinancialRecordRepository extends JpaRepository<FinancialRecord, Long> {
    // Standard Filtering & Pagination (For Analysts/Viewers)

    // Allows searching records between two dates (e.g., this month's records)
    Page<FinancialRecord> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

    // Allows searching records by INCOME or EXPENSE type
    Page<FinancialRecord> findByType(RecordType type, Pageable pageable);

    // Get the most recent 10 records for quick access on the dashboard
    List<FinancialRecord> findTop10ByOrderByDateDesc();

    // Dashboard Aggregations (For Dashboard Metrics)

    // Calculate total income or expense
    @Query("SELECT SUM(fr.amount) FROM FinancialRecord fr WHERE fr.type = :type")
    BigDecimal sumAmountByType(@Param("type") RecordType type);

    // Calculate total income or expense within a specific date range (Monthly/Quarterly/Yearly)
    @Query("SELECT SUM(fr.amount) FROM FinancialRecord fr WHERE fr.type = :type AND fr.date BETWEEN :startDate AND :endDate")
    BigDecimal sumAmountByTypeAndDateRange(@Param("type") RecordType type, @Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);

    // Grouping totals by category (e.g., total spent on "Software Subscription" this month)
    @Query("SELECT new com.lokesh.finflow.dto.repository.CategoryTotal(fr.category, SUM(fr.amount)) FROM FinancialRecord fr WHERE fr.type = :type GROUP BY fr.category")
    List<CategoryTotal> sumAmountByTypeGroupedByCategory(@Param("type") RecordType type);
}
