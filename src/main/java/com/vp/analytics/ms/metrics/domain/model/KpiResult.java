package com.vp.analytics.ms.metrics.domain.model;

import java.math.BigDecimal;
import java.util.Map;

public record KpiResult(
        BigDecimal averageTicket,
        BigDecimal customerAcquisitionCost,
        BigDecimal lifetimeValue,
        BigDecimal netResult,
        Map<ERevenueCategories, BigDecimal> revenueByCategory,
        Map<EExpenseCategories, BigDecimal> expenseByCategory
) {
}
