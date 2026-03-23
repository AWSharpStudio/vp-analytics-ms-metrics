package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;

// (totalRevenue / revenueTransactionCount) * 12
public class LtvCalculator implements KpiCalculator {

    private final KpiCalculator averageTicketCalculator;

    public LtvCalculator(KpiCalculator averageTicketCalculator) {
        this.averageTicketCalculator = averageTicketCalculator;
    }

    @Override
    public BigDecimal calculate(KpiData data) {
        return averageTicketCalculator.calculate(data).multiply(BigDecimal.valueOf(12));
    }
}
