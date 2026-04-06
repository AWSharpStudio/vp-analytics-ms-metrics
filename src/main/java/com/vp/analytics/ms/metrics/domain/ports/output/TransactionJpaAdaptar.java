package com.vp.analytics.ms.metrics.domain.ports.output;

import com.vp.analytics.ms.metrics.domain.calculation.KpiData;
import com.vp.analytics.ms.metrics.domain.model.Transaction;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
public class TransactionJpaAdaptar implements TransactionQueryPort {

    @Override
    public List<Transaction> findByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return List.of();
    }

    @Override
    public BigDecimal sumRevenueByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return null;
    }

    @Override
    public BigDecimal sumAcquisitionExpensesByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return null;
    }

    @Override
    public long countNewCustomersByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return 0;
    }

    @Override
    public long countRevenueTransactionsByClientIdAndPeriod(final String clientId, final LocalDate referenceDate) {
        return 0;
    }

    @Override
    public Map<String, BigDecimal> revenueGroupedByCategoryAndPeriod(final String clientId,
                                                                     final LocalDate referenceDate) {
        return Map.of();
    }

    @Override
    public Map<String, BigDecimal> expenseGroupedByCategoryAndPeriod(final String clientId,
                                                                     final LocalDate referenceDate) {
        return Map.of();
    }

    @Override
    public KpiData fetchKpiData(final String clientId, final LocalDate referenceDate) {
        return null;
    }
}
