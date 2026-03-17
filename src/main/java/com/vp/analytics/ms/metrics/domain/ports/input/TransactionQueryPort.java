package com.vp.analytics.ms.metrics.domain.ports.input;

import com.vp.analytics.ms.metrics.domain.model.Transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

public interface TransactionQueryPort {
    List<Transaction> findByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    BigDecimal sumRevenueByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    BigDecimal sumAcquisitionExpensesByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    long countNewCustomersByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    long countRevenueTransactionsByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    Map<String, BigDecimal> revenueGroupedByCategoryAndPeriod(String clientId, LocalDate referenceDate);

    Map<String, BigDecimal> expenseGroupedByCategoryAndPeriod(String clientId, LocalDate referenceDate);
}
