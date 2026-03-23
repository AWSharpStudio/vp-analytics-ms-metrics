package com.vp.analytics.ms.metrics.domain.calculation;

import java.math.BigDecimal;

public enum EKpiFormula {
    AVERAGE_TICKET(new AverageTicketCalculator()),
    CAC(new CacCalculator()),
    LTV(new LtvCalculator(new AverageTicketCalculator())),
    NET_RESULT(new NetResultCalculator());

    private final KpiCalculator calculator;

    EKpiFormula(KpiCalculator calculator) {
        this.calculator = calculator;
    }

    public BigDecimal calculate(KpiData data) {
        return this.calculator.calculate(data);
    }
}
