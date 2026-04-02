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
}
