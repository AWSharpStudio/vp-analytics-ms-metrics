package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;

// totalRevenue - totalExpenses
public class NetResultCalculator implements KpiCalculator {

    @Override
    public BigDecimal calculate(KpiData data) {
        return data.totalRevenue().subtract(data.totalExpenses());
    }
}
