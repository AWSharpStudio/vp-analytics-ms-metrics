package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;

@FunctionalInterface
public interface KpiCalculator {
    BigDecimal calculate(KpiData data);
}
