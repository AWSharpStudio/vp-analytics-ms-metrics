package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;

public enum EKpiFormula {
    AVERAGE_TICKET(new AverageTicketCalculator()),
    CAC(new CacCalculator()),
    LTV(new LtvCalculator(new AverageTicketCalculator())),
    NET_RESULT(new NetResultCalculator());

    private final KpiCalculator calculator;

    EKpiFormula(final KpiCalculator calculator) {
        this.calculator = calculator;
    }

    public BigDecimal calculate(final KpiData data) {
        return this.calculator.calculate(data);
    }
}
