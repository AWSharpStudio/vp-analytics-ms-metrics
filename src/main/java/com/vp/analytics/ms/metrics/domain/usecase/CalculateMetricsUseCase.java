package com.vp.analytics.ms.metrics.domain.usecase;

import com.vp.analytics.ms.metrics.domain.calculation.EKpiFormula;
import com.vp.analytics.ms.metrics.domain.calculation.KpiData;
import com.vp.analytics.ms.metrics.domain.model.KpiReport;
import com.vp.analytics.ms.metrics.domain.model.KpiResult;
import com.vp.analytics.ms.metrics.domain.model.TransactionIngestedEvent;
import com.vp.analytics.ms.metrics.domain.ports.input.CalculateMetricsInputPort;
import com.vp.analytics.ms.metrics.domain.ports.output.MetricsRepositoryPort;
import com.vp.analytics.ms.metrics.domain.ports.output.NotificationPort;
import com.vp.analytics.ms.metrics.domain.ports.output.TransactionQueryPort;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Orchestrates KPI calculation for a given client and reference period
 */
public class CalculateMetricsUseCase implements CalculateMetricsInputPort {

    private final TransactionQueryPort queryPort;

    private final MetricsRepositoryPort metricsRepository;

    private final NotificationPort notificationPort;

    public CalculateMetricsUseCase(final TransactionQueryPort queryPort,
                                   final MetricsRepositoryPort metricsRepository,
                                   final NotificationPort notificationPort) {
        this.queryPort = queryPort;
        this.metricsRepository = metricsRepository;
        this.notificationPort = notificationPort;
    }

    @Override
    public void calculate(final TransactionIngestedEvent event) {
        final String clientId = event.clientId();
        final LocalDate referenceDate = LocalDate.now().withDayOfMonth(1);

        final KpiData kpiData = queryPort.fetchKpiData(clientId, referenceDate);
        final KpiResult result = buildKpiResult(kpiData);
        final KpiReport report = buildKpiReport(clientId, event, result, referenceDate);
        metricsRepository.save(report);
        notificationPort.sendReport(clientId, referenceDate, report);
    }

    private KpiResult buildKpiResult(final KpiData kpiData) {
        final Map<EKpiFormula, BigDecimal> values = Arrays.stream(EKpiFormula.values())
                .collect(Collectors.toMap(f -> f, f -> f.calculate(kpiData)));

        return new KpiResult(
                values.get(EKpiFormula.AVERAGE_TICKET),
                values.get(EKpiFormula.CAC),
                values.get(EKpiFormula.LTV),
                values.get(EKpiFormula.NET_RESULT),
                kpiData.revenueByCategory(),
                kpiData.expenseByCategory()
        );
    }

    private KpiReport buildKpiReport(final String clientId,
                                     final TransactionIngestedEvent event,
                                     final KpiResult result,
                                     final LocalDate referenceDate) {
        return new KpiReport(
                UUID.randomUUID().toString(),
                clientId,
                event.uploadId(),
                event.clientId(),
                LocalDateTime.now(),
                result,
                event.transactionCount(),
                referenceDate
        );
    }
}
