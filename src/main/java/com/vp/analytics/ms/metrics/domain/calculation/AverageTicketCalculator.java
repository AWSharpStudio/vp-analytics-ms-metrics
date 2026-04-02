package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

// totalRevenue / revenueTransactionCount
public class AverageTicketCalculator implements KpiCalculator {

    @Override
    public BigDecimal calculate(final KpiData data) {
        if (data.revenueTransactionCount() <= 0) return BigDecimal.ZERO;
        return data.totalRevenue().divide(
                BigDecimal.valueOf(data.revenueTransactionCount()), 2, RoundingMode.HALF_UP);
    }
}
