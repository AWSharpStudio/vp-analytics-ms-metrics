package com.vp.analytics.ms.metrics.domain.calculation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class KpiFormulaTest {

    private static final KpiData DATA = new KpiData(
            "clientId",
            LocalDate.of(2026, 1, 10),
            new BigDecimal("25000"), // totalRevenue
            new BigDecimal("10000"), // totalExpenses
            new BigDecimal("5000"), // totalAcquisitionExpenses
            5,
            2,
            Map.of(),
            Map.of());

    private static final KpiData MISSING_DATA = new KpiData(
            "clientId",
            LocalDate.of(2026, 1, 10),
            new BigDecimal("25000"), // totalRevenue
            new BigDecimal("100000"), // totalExpenses
            new BigDecimal("5000"), // totalAcquisitionExpenses
            -1,
            0,
            Map.of(),
            Map.of());

    @Test
    void shouldCalculateCorrectly() {
        BigDecimal averageTicket = KpiFormula.AVERAGE_TICKET.calculate(DATA);
        assertEquals(new BigDecimal("5000.00"), averageTicket);

        BigDecimal netResult = KpiFormula.NET_RESULT.calculate(DATA);
        assertEquals(new BigDecimal("15000"), netResult);

        BigDecimal cac = KpiFormula.CAC.calculate(DATA);
        assertEquals(new BigDecimal("2500.00"), cac);

        BigDecimal ltv = KpiFormula.LTV.calculate(DATA);
        assertEquals(new BigDecimal("60000.00"), ltv);
    }

    @Test
    void shouldFailCalculationNecessaryDataNotGiven() {
        BigDecimal averageTicket = KpiFormula.AVERAGE_TICKET.calculate(MISSING_DATA);
        assertEquals(BigDecimal.ZERO, averageTicket);

        BigDecimal netResult = KpiFormula.NET_RESULT.calculate(MISSING_DATA);
        assertEquals(new BigDecimal("-75000"), netResult);

        BigDecimal cac = KpiFormula.CAC.calculate(MISSING_DATA);
        assertEquals(BigDecimal.ZERO, cac);

        BigDecimal ltv = KpiFormula.LTV.calculate(MISSING_DATA);
        assertEquals(BigDecimal.ZERO, ltv);
    }
}
