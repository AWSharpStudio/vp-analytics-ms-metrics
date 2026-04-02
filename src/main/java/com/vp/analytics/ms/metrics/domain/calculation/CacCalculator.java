package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;
import java.math.RoundingMode;

// totalAcquisitionExpenses / newCustomerCount
public class CacCalculator implements KpiCalculator {

    @Override
    public BigDecimal calculate(final KpiData data) {
        if (data.newCustomerCount() <= 0) return BigDecimal.ZERO;
        return data.totalAcquisitionExpenses().divide(
                BigDecimal.valueOf(data.newCustomerCount()), 2, RoundingMode.HALF_UP);
    }
}
