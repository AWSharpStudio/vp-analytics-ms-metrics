package com.vp.analytics.ms.metrics.domain.ports.output;

import com.vp.analytics.ms.metrics.domain.calculation.KpiData;
import com.vp.analytics.ms.metrics.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Output port for reading financial transactions.
 *
 * <p>
 * This pure Java interface declares what the application needs from external systems.
 * Remaining framework-free abstracts the database implementation, ensuring
 * the domain remains isolated and technology-agnostic.
 */
public interface TransactionQueryPort {
    List<Transaction> findByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    BigDecimal sumRevenueByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    BigDecimal sumAcquisitionExpensesByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    long countNewCustomersByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    long countRevenueTransactionsByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    Map<String, BigDecimal> revenueGroupedByCategoryAndPeriod(String clientId, LocalDate referenceDate);

    Map<String, BigDecimal> expenseGroupedByCategoryAndPeriod(String clientId, LocalDate referenceDate);

    KpiData fetchKpiData(String clientId, LocalDate referenceDate);
}
