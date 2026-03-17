package com.vp.analytics.ms.metrics.domain.ports.input;

import com.vp.analytics.ms.metrics.domain.model.KpiReport;

import java.time.LocalDate;
import java.util.Optional;

public interface MetricsRepositoryPort {
    KpiReport save(KpiReport report);

    Optional<KpiReport> findByClientIdAndPeriod(String clientId, LocalDate referenceDate);

    Optional<KpiReport> findByUploadId(String uploadId);
}
