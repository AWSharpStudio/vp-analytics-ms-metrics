package com.vp.analytics.ms.metrics.domain.ports.output;

import com.vp.analytics.ms.metrics.domain.model.KpiReport;

import java.time.LocalDate;
import java.util.Optional;

/**
 * Output port for persisting and retrieving KPI Reports
 *
 * <p>
 * This pure Java interface declares what the application needs from external systems.
 * Remaining framework-free abstracts the database implementation, ensuring
 * the domain remains isolated and technology-agnostic.
 */
public interface MetricsRepositoryPort {
    KpiReport save(KpiReport report);

    Optional<KpiReport> findByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    Optional<KpiReport> findByUploadId(String uploadId);
}
