package com.vp.analytics.ms.metrics.domain.calculation;

import com.vp.analytics.ms.metrics.domain.model.EExpenseCategories;
import com.vp.analytics.ms.metrics.domain.model.ERevenueCategories;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

public record KpiData(
        String clientId,
        LocalDate referenceDate,
        BigDecimal totalRevenue,
        BigDecimal totalExpenses,
        BigDecimal totalAcquisitionExpenses,
        long revenueTransactionCount,
        long newCustomerCount,
        Map<ERevenueCategories, BigDecimal> revenueByCategory,
        Map<EExpenseCategories, BigDecimal> expenseByCategory
) {
    public KpiData(String clientId,
                   LocalDate referenceDate,
                   BigDecimal totalRevenue,
                   BigDecimal totalExpenses,
                   BigDecimal totalAcquisitionExpenses,
                   long revenueTransactionCount,
                   long newCustomerCount,
                   Map<ERevenueCategories, BigDecimal> revenueByCategory,
                   Map<EExpenseCategories, BigDecimal> expenseByCategory
    ) {
        this.clientId = clientId;
        this.referenceDate = referenceDate;
        this.totalRevenue = totalRevenue;
        this.totalExpenses = totalExpenses;
        this.totalAcquisitionExpenses = totalAcquisitionExpenses;
        this.revenueTransactionCount = revenueTransactionCount;
        this.newCustomerCount = newCustomerCount;
        this.revenueByCategory = revenueByCategory;
        this.expenseByCategory = expenseByCategory;
    }
}
